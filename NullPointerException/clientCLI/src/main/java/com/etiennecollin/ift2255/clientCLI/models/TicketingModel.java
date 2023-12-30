/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.models.data.*;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * The {@code TicketingModel} class represents the models for handling ticket-related operations in the CLI application.
 * It interacts with the underlying database to perform operations such as retrieving tickets, creating return shipments,
 * and creating replacement shipments.
 */
public class TicketingModel {
    /**
     * Maximum number of days since received product for return.
     */
    private static final int MAX_RETURN_ELIGIBILITY_DAYS = 30;
    /**
     * Maximum number of days allowed for issues to be raised.
     */
    private static final int MAX_ISSUE_DELAY_DAYS = 365;
    /**
     * Maximum number of days allowed between requesting a return and it being received.
     */
    private static final int MAX_RETURN_DELAY_DAYS = 30;
    /**
     * The underlying database used by the models.
     */
    private final Database db;

    /**
     * Constructs a new {@code TicketingModel} with the specified database.
     *
     * @param db The database used by the models to store ticket-related data.
     */
    public TicketingModel(Database db) {
        this.db = db;
    }

    /**
     * Retrieves a list of tickets that satisfy the given predicate.
     *
     * @param predicate The predicate used to filter tickets.
     *
     * @return A list of tickets that satisfy the given predicate.
     */
    public List<Ticket> getTickets(Predicate<Ticket> predicate) {
        updateTickets();
        return db.get(DataMap.TICKETS, predicate);
    }

    /**
     * Updates the state of tickets based on specific criteria, such as canceling tickets that are overdue for return shipments.
     * This method is responsible for maintaining the consistency of ticket states in the system.
     */
    public void updateTickets() {
        db.<Ticket>update(DataMap.TICKETS, t -> t.setState(TicketState.Cancelled), t -> t.getState() == TicketState.ReturnInTransit && LocalDate.now().isAfter(t.getReturnShipment().getCreationDate().plusDays(MAX_RETURN_DELAY_DAYS)));
    }

    /**
     * Creates a manual ticket for the specified order, products, description, and cause.
     *
     * @param orderId     The unique identifier of the order.
     * @param products    The list of products and quantities associated with the ticket.
     * @param description The description of the ticket.
     * @param cause       The cause or reason for creating the manual ticket.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult createManualTicket(UUID orderId, ArrayList<Tuple<Product, Integer>> products, String description, TicketCause cause) {
        if (db.<Ticket>get(DataMap.TICKETS, (ticket) -> ticket.getOrderId().equals(orderId)).size() != 0) {
            return new OperationResult(false, "A ticket has already been created for this order.");
        }

        Order order = db.get(DataMap.ORDERS, orderId);
        if (order == null) {
            return new OperationResult(false, "Order does not exist.");
        } else if (order.getState() == OrderState.Cancelled) {
            return new OperationResult(false, "Cannot create a ticket for a cancelled order.");
        } else if (!LocalDate.now().isBefore(order.getOrderDate().plusDays(MAX_ISSUE_DELAY_DAYS))) {
            return new OperationResult(false, "The ticketing window for this order has passed.");
        }

        boolean result = db.add(DataMap.TICKETS, new Ticket(description, orderId, products, cause, TicketState.OpenManual, order.getBuyerId(), order.getSellerId()));
        if (result) {
            return new OperationResult(true, "Ticket successfully opened.");
        } else {
            return new OperationResult(false, "Ticket could not be created.");
        }
    }

    /**
     * Creates an automatic ticket for the specified order, products, cause, and replacement order ID.
     *
     * @param orderId            The unique identifier of the order.
     * @param products           The list of products and quantities associated with the ticket.
     * @param cause              The cause or reason for creating the automatic ticket.
     * @param replacementOrderId The unique identifier of the replacement order, if applicable.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult createAutoTicket(UUID orderId, ArrayList<Tuple<Product, Integer>> products, TicketCause cause, UUID replacementOrderId) {
        if (db.<Ticket>get(DataMap.TICKETS, (ticket) -> ticket.getOrderId().equals(orderId)).size() != 0) {
            return new OperationResult(false, "A ticket has already been created for this order.");
        }

        Order order = db.get(DataMap.ORDERS, orderId);
        if (order == null) {
            return new OperationResult(false, "Order does not exist.");
        } else if (order.getState() == OrderState.Cancelled) {
            return new OperationResult(false, "Cannot create a ticket for a cancelled order.");
        } else if (order.getShipment() != null && !LocalDate.now().isBefore(order.getShipment().getReceptionDate().plusDays(MAX_RETURN_ELIGIBILITY_DAYS))) {
            return new OperationResult(false, "The return window for this order has passed.");
        }

        Ticket newTicket = new Ticket("", orderId, products, cause, TicketState.ReturnInTransit, order.getBuyerId(), order.getSellerId());
        newTicket.setReturnShipment(new Shipment("", LocalDate.now(), ""));
        String solution = "Return request accepted. Please bring the package to your nearest post office.";
        newTicket.setSuggestedSolution(solution);
        newTicket.setReplacementOrderId(replacementOrderId);

        boolean result = db.add(DataMap.TICKETS, newTicket);
        if (result) {
            return new OperationResult(true, solution);
        } else {
            return new OperationResult(false, "Ticket could not be created.");
        }
    }

    /**
     * Activates an exchange ticket, updating the ticket state and creating a replacement order if applicable.
     *
     * @param ticket          The exchange ticket to be activated.
     * @param originalOrder   The original order associated with the exchange ticket.
     * @param cartProductList The list of products in the exchange cart.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult activateExchangeTicket(Ticket ticket, Order originalOrder, List<CartProduct> cartProductList) {
        Session session = Session.getInstance();

        if (session.getIsInExchangeProcess()) {
            ticket.setReturnShipment(new Shipment(null, LocalDate.now(), null));
            ticket.setState(TicketState.ReturnInTransit);
            String solution = "Return request accepted. Please bring the package to your nearest post office.";
            ticket.setSuggestedSolution(solution);

            int totalReturnValue = 0;
            for (Tuple<Product, Integer> productTuple : ticket.getProducts()) {
                totalReturnValue += (productTuple.first.getPrice() - productTuple.first.getPromoDiscount()) * productTuple.second;
            }

            ArrayList<Tuple<Product, Integer>> productTupleList = new ArrayList<>();
            int totalReplacementCost = 0;
            int totalFidelityPointsEarned = 0;
            for (CartProduct cartProduct : cartProductList) {
                Product prod = db.get(DataMap.PRODUCTS, cartProduct.getProductId());
                productTupleList.add(new Tuple<>(prod, cartProduct.getQuantity()));

                int price = (prod.getPrice() - prod.getPromoDiscount()) * cartProduct.getQuantity();
                totalReplacementCost += price;
                totalFidelityPointsEarned += (price / 100 + prod.getBonusFidelityPoints()) * cartProduct.getQuantity();
            }

            PaymentMethod paymentMethod = new PaymentMethod(Math.max(totalReplacementCost - totalReturnValue, 0), 0, Math.min(totalReturnValue, totalReplacementCost));

            Order replacementOrder = new Order(productTupleList, totalReplacementCost, totalFidelityPointsEarned, paymentMethod, originalOrder.getEmail(), originalOrder.getPhone(), originalOrder.getAddress(), originalOrder.getBillingAddress(), originalOrder.getCreditCardName(), originalOrder.getCreditCardNumber(), originalOrder.getCreditCardExp(), originalOrder.getCreditCardSecretDigits(), originalOrder.getBuyerId(), originalOrder.getSellerId());
            replacementOrder.setState(OrderState.PendingTicket);
            ticket.setReplacementOrderId(replacementOrder.getId());

            db.add(DataMap.ORDERS, replacementOrder);
            db.add(DataMap.TICKETS, ticket);

            return new OperationResult(true, solution);
        }

        return new OperationResult(false, "Ticket could not be modified.");
    }

    /**
     * Creates a replacement shipment for the specified ticket with the given information.
     *
     * @param ticketId        The unique identifier of the ticket.
     * @param description     The description of the replacement product.
     * @param trackingNumber  The tracking number of the replacement shipment.
     * @param deliveryDate    The expected delivery date of the replacement shipment.
     * @param shippingCompany The shipping company responsible for the replacement shipment.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult createReplacementShipment(UUID ticketId, String description, String trackingNumber, LocalDate deliveryDate, String shippingCompany) {
        Ticket ticket = getTicket(ticketId);
        if (ticket == null) {
            return new OperationResult(false, "Ticket does not exist.");
        } else if (ticket.getState() == TicketState.Cancelled || ticket.getState() == TicketState.Closed) {
            return new OperationResult(false, "Ticket cannot be updated.");
        }

        UUID replacementOrderId = ticket.getReplacementOrderId();
        Order replacementOrder = db.get(DataMap.ORDERS, replacementOrderId);
        if (replacementOrder == null) {
            return new OperationResult(false, "Ticket's replacement order not found.");
        } else if (replacementOrder.getShipment() != null) {
            return new OperationResult(false, "Ticket already has a replacement shipment.");
        }

        boolean result1 = db.<Order>update(DataMap.ORDERS, o -> {
            o.setShipment(new Shipment(trackingNumber, deliveryDate, shippingCompany));
            o.setState(OrderState.InTransit);
        }, replacementOrderId);
        if (result1) {
            boolean result2 = db.<Ticket>update(DataMap.TICKETS, t -> {
                t.setState(TicketState.ReplacementInTransit);
                t.setReplacementProductDescription(description);
            }, ticketId);
            if (result2) {
                return new OperationResult(true, "Replacement shipment information added.");
            }
        }

        return new OperationResult(false, "Unable to update ticket.");
    }

    /**
     * Retrieves the ticket with the specified ID from the database.
     *
     * @param ticketId The unique identifier of the ticket.
     *
     * @return The ticket with the specified ID, or {@code null} if the ticket does not exist.
     */
    public Ticket getTicket(UUID ticketId) {
        return db.get(DataMap.TICKETS, ticketId);
    }

    //    /**
    //     * Creates a return shipment for the specified ticket with the given information.
    //     *
    //     * @param ticketId        The unique identifier of the ticket.
    //     * @param trackingNumber  The tracking number of the return shipment.
    //     * @param deliveryDate    The expected delivery date of the return shipment.
    //     * @param shippingCompany The shipping company responsible for the return shipment.
    //     * @return An {@code OperationResult} indicating the success or failure of the operation.
    //     */
    //    public OperationResult createReturnShipment(UUID ticketId, String solution, String trackingNumber, LocalDate deliveryDate, String shippingCompany) {
    //        Ticket ticket = getTicket(ticketId);
    //        if (ticket == null) {
    //            return new OperationResult(false, "Ticket does not exist.");
    //        }
    //
    //        if (ticket.getReturnShipment() != null) {
    //            return new OperationResult(false, "Ticket already has a return shipment.");
    //        }
    //
    //        boolean result = db.<Ticket>update(DataMap.TICKETS, (t) -> {
    //            t.setState(TicketState.ReturnInTransit);
    //            t.setSuggestedSolution(solution);
    //            t.setReturnShipment(new Shipment(trackingNumber, deliveryDate, shippingCompany));
    //        }, ticketId);
    //        if (result) {
    //            return new OperationResult(true, "Return shipment information added.");
    //        } else {
    //            return new OperationResult(false, "Unable to update ticket.");
    //        }
    //    }

    /**
     * Changes a ticket to indicate replacement with or without return.
     *
     * @param ticketId        The UUID of the ticket to modify.
     * @param solution        The proposed solution for the ticket.
     * @param requireReturn   Whether the replacement requires a return shipment.
     * @param trackingNumber  The tracking number of the return shipment.
     * @param shippingCompany The shipping company responsible for the return shipment.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult changeTicketToReplacement(UUID ticketId, String solution, boolean requireReturn, String trackingNumber, String shippingCompany) {
        Ticket ticket = db.get(DataMap.TICKETS, ticketId);
        if (ticket != null && ticket.getState() == TicketState.OpenManual) {

            int totalCost = 0;
            int fidelityPointsEarned = 0;
            for (Tuple<Product, Integer> productTuple : ticket.getProducts()) {
                int productPrice = productTuple.first.getPrice() - productTuple.first.getPromoDiscount();
                int quantity = productTuple.second;
                totalCost += productPrice * quantity;
                fidelityPointsEarned += (productPrice / 100 + productTuple.first.getBonusFidelityPoints()) * quantity;
            }

            Order originalOrder = db.get(DataMap.ORDERS, ticket.getOrderId());

            Order replacementOrder = new Order(ticket.getProducts(), totalCost, fidelityPointsEarned, new PaymentMethod(0, 0, totalCost), originalOrder.getEmail(), originalOrder.getPhone(), originalOrder.getAddress(), originalOrder.getBillingAddress(), originalOrder.getCreditCardName(), originalOrder.getCreditCardNumber(), originalOrder.getCreditCardExp(), originalOrder.getCreditCardSecretDigits(), originalOrder.getBuyerId(), originalOrder.getSellerId());
            if (requireReturn) {
                replacementOrder.setState(OrderState.PendingTicket);
            }
            db.add(DataMap.ORDERS, replacementOrder);

            db.<Ticket>update(DataMap.TICKETS, t -> {
                t.setSuggestedSolution(solution);
                if (requireReturn) {
                    t.setReturnShipment(new Shipment(trackingNumber, null, shippingCompany));
                    t.setState(TicketState.ReturnInTransit);
                } else {
                    t.setState(TicketState.ReturnReceived);
                    refundOrChargeBuyer(ticketId);
                }
            }, ticketId);

            return new OperationResult(true, "Ticket closed.");
        }
        return new OperationResult(false, "Ticket could not be modified.");
    }

    /**
     * Refunds or charges the buyer based on the ticket information.
     *
     * @param ticketId The UUID of the ticket for which the refund or charge operation is performed.
     */
    private void refundOrChargeBuyer(UUID ticketId) {
        Ticket ticket = db.get(DataMap.TICKETS, ticketId);
        if (ticket == null) {
            return;
        }

        Order originalOrder = db.get(DataMap.ORDERS, ticket.getOrderId());
        if (originalOrder == null || originalOrder.getPaymentMethod() == null) {
            return;
        }
        PaymentMethod originalPayment = originalOrder.getPaymentMethod();

        Order replacementOrder = db.get(DataMap.ORDERS, ticket.getReplacementOrderId());
        PaymentMethod replacementPayment = null;
        int replacementPointsEarned;
        if (replacementOrder != null) {
            replacementPayment = replacementOrder.getPaymentMethod();
            replacementPointsEarned = replacementOrder.getFidelityPointsEarned();
        } else {
            replacementPointsEarned = 0;
        }

        int returnValue = 0;
        int earnedPointsToRemove = 0;
        for (Tuple<Product, Integer> productTuple : ticket.getProducts()) {
            int productPrice = productTuple.first.getPrice() - productTuple.first.getPromoDiscount();
            int quantity = productTuple.second;
            returnValue += productPrice * quantity;
            earnedPointsToRemove += productPrice / 100 + productTuple.first.getBonusFidelityPoints();
        }
        int finalEarnedPointsToRemove = earnedPointsToRemove;

        int returnMoneyUsed = 0;
        if (replacementPayment != null) {
            int fidelityPointsUsed = replacementPayment.getFidelityPointsUsed();
            if (replacementPayment.getMoneyUsed() > 0 || fidelityPointsUsed > 0) {
                // charge credit card
                db.<Buyer>update(DataMap.BUYERS, b -> b.setFidelityPoints(b.getFidelityPoints() - fidelityPointsUsed - finalEarnedPointsToRemove + replacementPointsEarned), ticket.getBuyerId());
                return;
            }

            returnMoneyUsed = replacementPayment.getReturnMoneyUsed();
        }

        int refundAmount = returnValue - returnMoneyUsed;

        int fidelityPointsToRefund = Math.min(originalPayment.getFidelityPointsUsed(), refundAmount);
        db.<Buyer>update(DataMap.BUYERS, b -> b.setFidelityPoints(b.getFidelityPoints() + fidelityPointsToRefund - finalEarnedPointsToRemove), ticket.getBuyerId());

        refundAmount -= fidelityPointsToRefund;
        // refund remaining to credit card
    }

    /**
     * Changes a ticket to indicate no replacement, with or without return.
     *
     * @param ticketId        The UUID of the ticket to modify.
     * @param solution        The proposed solution for the ticket.
     * @param requireReturn   Whether the replacement requires a return shipment.
     * @param trackingNumber  The tracking number of the return shipment.
     * @param shippingCompany The shipping company responsible for the return shipment.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult changeTicketToNoReplacement(UUID ticketId, String solution, boolean requireReturn, String trackingNumber, String shippingCompany) {
        Ticket ticket = db.get(DataMap.TICKETS, ticketId);
        if (ticket != null && ticket.getState() == TicketState.OpenManual) {
            db.<Ticket>update(DataMap.TICKETS, t -> {
                t.setSuggestedSolution(solution);
                if (requireReturn) {
                    t.setReturnShipment(new Shipment(trackingNumber, null, shippingCompany));
                    t.setState(TicketState.ReturnInTransit);
                } else {
                    t.setState(TicketState.Closed);
                    refundOrChargeBuyer(ticketId);
                }
            }, ticketId);

            return new OperationResult(true, "Ticket closed.");
        }
        return new OperationResult(false, "Ticket could not be modified.");
    }

    /**
     * Confirms the reception of a replacement for the specified ticket.
     *
     * @param ticketId The UUID of the ticket for which the replacement reception is confirmed.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult confirmReceptionOfReplacement(UUID ticketId) {
        Ticket ticket = db.get(DataMap.TICKETS, ticketId);
        boolean result = db.<Ticket>update(DataMap.TICKETS, t -> t.setState(TicketState.Closed), t -> t.getId().equals(ticketId) && t.getState() == TicketState.ReplacementInTransit);
        db.<Order>update(DataMap.ORDERS, o -> o.setState(OrderState.Delivered), ticket.getReplacementOrderId());
        if (result) {
            return new OperationResult(true, "Replacement reception confirmed. Ticket closed.");
        } else {
            return new OperationResult(false, "Unable to confirm replacement reception.");
        }
    }

    /**
     * Confirms the reception of a return for the specified ticket.
     *
     * @param ticketId The UUID of the ticket for which the return reception is confirmed.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult confirmReceptionOfReturn(UUID ticketId) {
        Ticket ticket = db.get(DataMap.TICKETS, ticketId);

        if (ticket.getState() == TicketState.ReturnInTransit && ticket.getReturnShipment() != null) {
            if (ticket.getReplacementOrderId() != null) {
                db.<Ticket>update(DataMap.TICKETS, t -> {
                    t.setState(TicketState.ReturnReceived);
                    t.getReturnShipment().setReceptionDate(LocalDate.now());
                }, ticketId);
                db.<Order>update(DataMap.ORDERS, o -> o.setState(OrderState.InProduction), ticket.getReplacementOrderId());
            } else {
                db.<Ticket>update(DataMap.TICKETS, t -> {
                    t.setState(TicketState.Closed);
                    t.getReturnShipment().setReceptionDate(LocalDate.now());
                }, ticketId);
            }

            if (ticket.getCause() != TicketCause.DefectiveProduct) {
                for (Tuple<Product, Integer> productTuple : ticket.getProducts()) {
                    int quantity = productTuple.second;
                    db.<Product>update(DataMap.PRODUCTS, p -> p.setQuantity(p.getQuantity() + quantity), productTuple.first.getId());
                }
            }

            refundOrChargeBuyer(ticketId);
        }

        return new OperationResult(true, "Return reception confirmed.");
    }
}
