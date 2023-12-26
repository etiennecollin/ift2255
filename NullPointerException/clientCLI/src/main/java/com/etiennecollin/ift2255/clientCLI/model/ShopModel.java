/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.model.data.PayementMethod;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.model.data.*;
import com.etiennecollin.ift2255.clientCLI.model.data.products.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Predicate;

public class ShopModel {
    private final Database db;

    public ShopModel(Database database) {
        this.db = database;
    }

    public <T extends Product> T getProduct(Class<T> productClass, UUID productId) {
        Product product = db.get(DataMap.PRODUCTS, productId);
        if (product.getClass().equals(productClass)) {
            return (T) product;
        }

        return null;
    }

    public List<Product> getProducts() {
        return db.get(DataMap.PRODUCTS, (product) -> true);
    }

    public List<Product> getProducts(ProductCategory category, Enum<?> subCategory, UUID sellerId) {
        return db.get(DataMap.PRODUCTS, (product) ->
                (category == null || product.getCategory() == category) &&
                (subCategory == null || product.getSubCategory() == subCategory) &&
                (sellerId == null || product.getSellerId() == sellerId)
        );
    }

    public List<Product> searchProducts(Predicate<Product> predicate) {
        return db.get(DataMap.PRODUCTS, predicate);
    }

    public OperationResult createNewBookOrManual(UUID sellerId, int price, int quantity, String title, String description, int fidelityPoints, String isbn, String author, String editor, BookOrManualGenre genre, LocalDate releaseDate, int editionNumber, int volumeNumber) {
        BookOrManual book = new BookOrManual(price, quantity, title, description, sellerId, fidelityPoints, isbn, author, editor, genre, releaseDate, editionNumber, volumeNumber);
        boolean result = db.add(DataMap.PRODUCTS, book);

        if (result) {
            return new OperationResult(true, "Product added.");
        }
        else {
            return new OperationResult(false, "An error occurred when saving the product.");
        }
    }

    public OperationResult createNewITProduct(UUID sellerId, int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, LocalDate releaseDate, ITCategory subCategory) {
        IT it = new IT(price, quantity, title, description, sellerId, fidelityPoints, brand, model, releaseDate, subCategory);
        boolean result = db.add(DataMap.PRODUCTS, it);

        if (result) {
            return new OperationResult(true, "Product added.");
        }
        else {
            return new OperationResult(false, "An error occurred when saving the product.");
        }
    }

    public OperationResult createNewLearningResource(UUID sellerId, int price, int quantity, String title, String description, int fidelityPoints, String isbn, String organisation, LocalDate releaseDate, LearningResourceType type, int editionNumber) {
        LearningResource learningResource = new LearningResource(price, quantity, title, description, sellerId, fidelityPoints, isbn, organisation, releaseDate, type, editionNumber);
        boolean result = db.add(DataMap.PRODUCTS, learningResource);

        if (result) {
            return new OperationResult(true, "Product added.");
        }
        else {
            return new OperationResult(false, "An error occurred when saving the product.");
        }
    }

    public OperationResult createNewOfficeEquipment(UUID sellerId, int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, OfficeEquipmentCategory subCategory) {
        OfficeEquipment officeEquipment = new OfficeEquipment(price, quantity, title, description, sellerId, fidelityPoints, brand, model, subCategory);
        boolean result = db.add(DataMap.PRODUCTS, officeEquipment);

        if (result) {
            return new OperationResult(true, "Product added.");
        }
        else {
            return new OperationResult(false, "An error occurred when saving the product.");
        }
    }

    public OperationResult createNewStationeryArticle(UUID sellerId, int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, StationeryArticleCategory subCategory) {
        StationeryArticle stationeryArticle = new StationeryArticle(price, quantity, title, description, sellerId, fidelityPoints, brand, model, subCategory);
        boolean result = db.add(DataMap.PRODUCTS, stationeryArticle);

        if (result) {
            return new OperationResult(true, "Product added.");
        }
        else {
            return new OperationResult(false, "An error occurred when saving the product.");
        }
    }

    public List<Tuple<CartProduct, Product>> getCart(UUID buyerId) {
        List<CartProduct> cartProductList = db.get(DataMap.CARTS, (cartProduct -> cartProduct.getBuyerId() == buyerId));
        return cartProductList.stream().map((cartProd) -> new Tuple<CartProduct, Product>(cartProd, db.get(DataMap.PRODUCTS, cartProd.getProductId()))).toList();
    }

    public OperationResult addToCart(UUID buyerId, UUID productId, int quantity) {
        Product product = db.get(DataMap.PRODUCTS, productId);
        if (product == null) {
            return new OperationResult(false, "Product discontinued.");
        }

        boolean result;

        List<CartProduct> existingEntries = db.get(DataMap.CARTS, (cartProd) -> cartProd.getBuyerId() == buyerId && cartProd.getProductId() == productId);
        if (existingEntries.size() > 0) {
            CartProduct existingEntry = existingEntries.get(0);
            int newQuantity = existingEntry.getQuantity() + quantity;

            if (!validateQuantity(product, newQuantity).isValid()) {
                return new OperationResult(false, "Insufficient inventory.");
            }

            result = db.<CartProduct>update(DataMap.CARTS, (cartProd) -> cartProd.setQuantity(newQuantity), productId);
        }
        else {
            if (!validateQuantity(product, quantity).isValid()) {
                return new OperationResult(false, "Insufficient inventory.");
            }

            result = db.add(DataMap.CARTS, new CartProduct(buyerId, productId, quantity));
        }

        if (result) {
            return new OperationResult(true, "Product successfully added to cart.");
        }
        else {
            return new OperationResult(false, "Product could not be added to cart.");
        }

    }

    public OperationResult removeFromCart(UUID cartProductId, int quantity) {
        CartProduct cartProduct = db.get(DataMap.CARTS, cartProductId);

        if (quantity < 0) {
            return new OperationResult(false, "Quantity to remove cannot be less than 0.");
        }

        if (cartProduct.getQuantity() > quantity) {
            int newQuantity = cartProduct.getQuantity() - quantity;
            boolean result = db.<CartProduct>update(DataMap.CARTS, (cartProd) -> cartProd.setQuantity(newQuantity), cartProductId);
            if (result) {
                return new OperationResult(true, "Item quantity updated.");
            }
            else {
                return new OperationResult(false, "Failed to update item quantity.");
            }
        }
        else {
            boolean result = db.remove(DataMap.CARTS, cartProductId);
            if (result) {
                return new OperationResult(true, "Item removed from cart.");
            }
            else {
                return new OperationResult(false, "Failed to remove item.");
            }
        }
    }

    public OperationResult emptyCart(UUID buyerId) {
        boolean result = db.<CartProduct>remove(DataMap.CARTS, (cartProduct) -> cartProduct.getBuyerId() == buyerId);
        if (result) {
            return new OperationResult(true, "Cart emptied.");
        }
        else {
            return new OperationResult(false, "The cart could not be emptied.");
        }
    }

    /**
     * Calculates the remaining cost left to pay and the unused fidelity points.
     * @param cost - The amount to pay.
     * @param fidelityPoints - The amount of fidelity points available.
     * @return Tuple.first = remaining cost, Tuple.second = remaining fidelity points.
     */
    public Tuple<Integer, Integer> costAfterFidelityPoints(int cost, int fidelityPoints) {
        int fidelityPointsUsed = 0;

        int pointsInCents = fidelityPoints * 2; // In cents
        if (pointsInCents > cost) {
            fidelityPointsUsed = cost / 2;
        } else {
            fidelityPointsUsed = fidelityPoints;
        }

        int remainingFidelityPoints = fidelityPoints - fidelityPointsUsed;

        int remainingCost = cost - fidelityPointsUsed * 2;

        return new Tuple<>(remainingCost, remainingFidelityPoints);
    }


    public OperationResult createOrders(UUID buyerId, String email, String phone, String shippingAddress, String billingAddress, String creditCardName, String creditCardNumber, YearMonth creditCardExpiration, String creditCardCVC, int fidelityPointsUsed) {
        int paidWithFidelityPoints = fidelityPointsUsed * 2; // This is in cents

        List<Tuple<CartProduct, Product>> cartProductTupleList = getCart(buyerId);

        // "Sort" products by seller
        HashMap<UUID, ArrayList<Tuple<Product, Integer>>> hashmap = new HashMap<>();
        for (Tuple<CartProduct, Product> cartProductTuple : cartProductTupleList) {
            UUID productSellerId = cartProductTuple.second.getSellerId();
            int purchaseQuantity = cartProductTuple.first.getQuantity();
            int inventoryQuantity = cartProductTuple.second.getQuantity();

            ArrayList<Tuple<Product, Integer>> sellerTupleList = hashmap.getOrDefault(productSellerId, new ArrayList<>());
            sellerTupleList.add(new Tuple<>(cartProductTuple.second, purchaseQuantity));
            hashmap.put(productSellerId, sellerTupleList);

            if (purchaseQuantity > inventoryQuantity) {
                return new OperationResult(false, "Unable to complete order. Only " + inventoryQuantity + " of " + cartProductTuple.second.getTitle() + " remaining.");
            }

            db.<Product>update(DataMap.PRODUCTS, (prod) -> prod.setQuantity(inventoryQuantity - purchaseQuantity), cartProductTuple.first.getProductId());
        }

        // For each seller and tuples of Product/Quantity in the hashmap
        for (Map.Entry<UUID, ArrayList<Tuple<Product, Integer>>> entry : hashmap.entrySet()) {
            UUID sellerId = entry.getKey();
            ArrayList<Tuple<Product, Integer>> tuples = entry.getValue();

            int subTotalCost = 0;
            int subTotalFidelityPoints = 0;

            // Compute the cost and fidelity points of this sub-order
            for (Tuple<Product, Integer> tuple : tuples) {
                Product product = tuple.first;
                int quantity = tuple.second;
                subTotalCost += (product.getPrice() - product.getPromoDiscount()) * quantity;

                // Subtract rebate from fidelity points
                if (paidWithFidelityPoints > 0) {
                    if (subTotalCost < paidWithFidelityPoints) {
                        paidWithFidelityPoints -= subTotalCost;
                        subTotalCost = 0;
                    } else {
                        subTotalCost -= paidWithFidelityPoints;
                        paidWithFidelityPoints = 0;
                    }
                }
                subTotalFidelityPoints += (subTotalCost / 100 + product.getBonusFidelityPoints()) * quantity;
            }

            PayementMethod payementMethod = new PayementMethod(subTotalCost, fidelityPointsUsed, 0);

            // Create the sub-order
            Order order = new Order(tuples, subTotalCost, subTotalFidelityPoints, payementMethod, email, phone, shippingAddress, billingAddress, creditCardName, creditCardNumber, creditCardExpiration, creditCardCVC, buyerId, sellerId);
            db.add(DataMap.ORDERS, order);
            // TODO add order products

            // Send notification
            Buyer buyer = db.get(DataMap.BUYERS, buyerId);
            String content = "Order " + order.getId() + " by " + buyer.getFirstName() + " " + buyer.getLastName() + " was received on " + order.getOrderDate() + ".";
            Notification notification = new Notification(sellerId, "Order received", content);
            db.add(DataMap.NOTIFICATIONS, notification);
        }

        db.<Buyer>update(DataMap.BUYERS, (buyer) -> buyer.setFidelityPoints(buyer.getFidelityPoints() - fidelityPointsUsed), buyerId);

        emptyCart(buyerId);
        return new OperationResult(true, "Your order has been placed successfully");
    }

    public List<Order> getOrders(UUID buyerId, UUID sellerId) {
        return db.get(DataMap.ORDERS, (order) -> {
            if (order.getBuyerId() == buyerId && sellerId == null) {
                return true;
            }
            else if (order.getSellerId() == sellerId && buyerId == null) {
                return true;
            }
            else {
                return order.getBuyerId() == buyerId && order.getSellerId() == sellerId;
            }
        });
    }

    public List<Order> getOrders(Predicate<Order> predicate) {
        return db.get(DataMap.ORDERS, predicate);
    }

    public OperationResult shipOrder(UUID orderId, String shippingCompany, String trackingNumber, LocalDate expectedDeliveryDate) {
        Order order = db.get(DataMap.ORDERS, orderId);
        if (order == null) {
            return new OperationResult(false, "Order does not exists.");
        }

        if (order.getState() == OrderState.InProduction && order.getShipment() == null) {
            db.<Order>update(DataMap.ORDERS,(o) -> {
                o.setShipment(new Shipment(trackingNumber, expectedDeliveryDate, shippingCompany));
                o.setState(OrderState.InTransit);
            }, orderId);
            return new OperationResult(true, "Order status updated");
        }
        else {
            return new OperationResult(false, "Order shipment information cannot be changed.");
        }
    }

    public OperationResult confirmDelivery(UUID orderId) {
        Order order = db.get(DataMap.ORDERS, orderId);
        if (order == null) {
            return new OperationResult(false, "Order does not exists.");
        }

        if (order.getState() == OrderState.InTransit) {
            db.<Order>update(DataMap.ORDERS, o -> o.setState(OrderState.Delivered), orderId);
            return new OperationResult(true, "Order successfully marked as delivered");
        }
        else {
            return new OperationResult(false, "Order cannot be marked as delivered.");
        }
    }

    public OperationResult cancelOrder(UUID orderId) {
        Order order = db.get(DataMap.ORDERS, orderId);
        if (order == null) {
            return new OperationResult(false, "Order does not exists.");
        }

        if (order.getState() == OrderState.InProduction) {
            db.<Order>update(DataMap.ORDERS, o -> o.setState(OrderState.Cancelled), orderId);
            return new OperationResult(true, "Order cancelled.");
        }
        else {
            return new OperationResult(false, "Order cannot be marked as cancelled.");
        }
    }

    public OperationResult validateQuantity(UUID productId, int quantity) {
        Product product = db.get(DataMap.PRODUCTS, productId);
        return validateQuantity(product, quantity);
    }

    private OperationResult validateQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            return new OperationResult(false, "Quantity must be greater than 0.");
        }

        if (product != null) {
            if (quantity <= product.getQuantity()) {
                return new OperationResult(true, "");
            }
            else {
                return new OperationResult(false, "Only " + product.getQuantity() + " of this item are left in stock.");
            }
        }

        return new OperationResult(false, "");
    }
}
