/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.model.data.*;
import com.etiennecollin.ift2255.clientCLI.model.data.products.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Predicate;

/**
 * The {@code ShopModel} class represents the model for managing shopping-related operations in the CLI application.
 * It includes methods for retrieving products, adding products to the cart, processing orders, and handling order-related tasks.
 */
public class ShopModel {
    /**
     * The underlying database used by the model.
     */
    private final Database db;

    /**
     * Constructs a new {@code ShopModel} with the specified database.
     *
     * @param database The database used by the model to store shopping-related data.
     */
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

    /**
     * Retrieves a list of all products in the database.
     *
     * @return A list containing all products.
     */
    public List<Product> getProducts() {
        return db.get(DataMap.PRODUCTS, (product) -> true);
    }

    /**
     * Retrieves a list of products based on specified criteria.
     *
     * @param category    The product category (can be {@code null}).
     * @param subCategory The product sub-category (can be {@code null}).
     * @param sellerId    The unique identifier of the seller (can be {@code null}).
     *
     * @return A list of products that match the specified criteria.
     */
    public List<Product> getProducts(ProductCategory category, Enum<?> subCategory, UUID sellerId) {
        return db.get(DataMap.PRODUCTS, (product) -> (category == null || product.getCategory() == category) && (subCategory == null || product.getSubCategory() == subCategory) && (sellerId == null || product.getSellerId() == sellerId));
    }

    /**
     * Searches for products based on a custom predicate.
     *
     * @param predicate The predicate used to filter products.
     *
     * @return A list of products that match the specified predicate.
     */
    public List<Product> searchProducts(Predicate<Product> predicate) {
        return db.get(DataMap.PRODUCTS, predicate);
    }

    /**
     * Creates a new book or manual product and adds it to the database.
     *
     * @param sellerId       The unique identifier of the seller.
     * @param price          The price of the product.
     * @param quantity       The quantity of the product.
     * @param title          The title of the product.
     * @param description    The description of the product.
     * @param fidelityPoints The fidelity points associated with the product.
     * @param isbn           The ISBN of the book or manual.
     * @param author         The author of the book or manual.
     * @param editor         The editor of the book or manual.
     * @param genre          The genre of the book or manual.
     * @param releaseDate    The release date of the book or manual.
     * @param editionNumber  The edition number of the book or manual.
     * @param volumeNumber   The volume number of the book or manual.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult createNewBookOrManual(UUID sellerId, int price, int quantity, String title, String description, int fidelityPoints, String isbn, String author, String editor, BookOrManualGenre genre, LocalDate releaseDate, int editionNumber, int volumeNumber) {
        BookOrManual book = new BookOrManual(price, quantity, title, description, sellerId, fidelityPoints, isbn, author, editor, genre, releaseDate, editionNumber, volumeNumber);
        boolean result = db.add(DataMap.PRODUCTS, book);

        if (result) {
            return new OperationResult(true, "Product added.");
        } else {
            return new OperationResult(false, "An error occurred when saving the product.");
        }
    }

    /**
     * Creates a new IT product and adds it to the database.
     *
     * @param sellerId       The unique identifier of the seller.
     * @param price          The price of the product.
     * @param quantity       The quantity of the product.
     * @param title          The title of the product.
     * @param description    The description of the product.
     * @param fidelityPoints The fidelity points associated with the product.
     * @param brand          The brand of the IT product.
     * @param model          The model of the IT product.
     * @param releaseDate    The release date of the IT product.
     * @param subCategory    The sub-category of the IT product.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult createNewITProduct(UUID sellerId, int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, LocalDate releaseDate, ITCategory subCategory) {
        IT it = new IT(price, quantity, title, description, sellerId, fidelityPoints, brand, model, releaseDate, subCategory);
        boolean result = db.add(DataMap.PRODUCTS, it);

        if (result) {
            return new OperationResult(true, "Product added.");
        } else {
            return new OperationResult(false, "An error occurred when saving the product.");
        }
    }

    /**
     * Creates a new learning resource product and adds it to the database.
     *
     * @param sellerId       The unique identifier of the seller.
     * @param price          The price of the product.
     * @param quantity       The quantity of the product.
     * @param title          The title of the product.
     * @param description    The description of the product.
     * @param fidelityPoints The fidelity points associated with the product.
     * @param isbn           The ISBN of the learning resource.
     * @param organisation   The organization associated with the learning resource.
     * @param releaseDate    The release date of the learning resource.
     * @param type           The type of the learning resource.
     * @param editionNumber  The edition number of the learning resource.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult createNewLearningResource(UUID sellerId, int price, int quantity, String title, String description, int fidelityPoints, String isbn, String organisation, LocalDate releaseDate, LearningResourceType type, int editionNumber) {
        LearningResource learningResource = new LearningResource(price, quantity, title, description, sellerId, fidelityPoints, isbn, organisation, releaseDate, type, editionNumber);
        boolean result = db.add(DataMap.PRODUCTS, learningResource);

        if (result) {
            return new OperationResult(true, "Product added.");
        } else {
            return new OperationResult(false, "An error occurred when saving the product.");
        }
    }

    /**
     * Creates a new office equipment product and adds it to the database.
     *
     * @param sellerId       The unique identifier of the seller.
     * @param price          The price of the product.
     * @param quantity       The quantity of the product.
     * @param title          The title of the product.
     * @param description    The description of the product.
     * @param fidelityPoints The fidelity points associated with the product.
     * @param brand          The brand of the office equipment.
     * @param model          The model of the office equipment.
     * @param subCategory    The sub-category of the office equipment.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult createNewOfficeEquipment(UUID sellerId, int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, OfficeEquipmentCategory subCategory) {
        OfficeEquipment officeEquipment = new OfficeEquipment(price, quantity, title, description, sellerId, fidelityPoints, brand, model, subCategory);
        boolean result = db.add(DataMap.PRODUCTS, officeEquipment);

        if (result) {
            return new OperationResult(true, "Product added.");
        } else {
            return new OperationResult(false, "An error occurred when saving the product.");
        }
    }

    /**
     * Creates a new stationery article product and adds it to the database.
     *
     * @param sellerId       The unique identifier of the seller.
     * @param price          The price of the product.
     * @param quantity       The quantity of the product.
     * @param title          The title of the product.
     * @param description    The description of the product.
     * @param fidelityPoints The fidelity points associated with the product.
     * @param brand          The brand of the stationery article.
     * @param model          The model of the stationery article.
     * @param subCategory    The sub-category of the stationery article.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult createNewStationeryArticle(UUID sellerId, int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, StationeryArticleCategory subCategory) {
        StationeryArticle stationeryArticle = new StationeryArticle(price, quantity, title, description, sellerId, fidelityPoints, brand, model, subCategory);
        boolean result = db.add(DataMap.PRODUCTS, stationeryArticle);

        if (result) {
            return new OperationResult(true, "Product added.");
        } else {
            return new OperationResult(false, "An error occurred when saving the product.");
        }
    }

    /**
     * Adds a specified quantity of a product to the user's cart.
     *
     * @param buyerId   The unique identifier of the buyer.
     * @param productId The unique identifier of the product.
     * @param quantity  The quantity to add to the cart.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult addToCart(UUID buyerId, UUID productId, int quantity) {
        Product product = db.get(DataMap.PRODUCTS, productId);
        if (product == null) {
            return new OperationResult(false, "Product discontinued.");
        }

        boolean result;

        List<CartProduct> existingEntries = db.get(DataMap.CARTS, (cartProd) -> cartProd.getBuyerId() == buyerId && cartProd.getProductId() == productId);
        if (!existingEntries.isEmpty()) {
            CartProduct existingEntry = existingEntries.get(0);
            int newQuantity = existingEntry.getQuantity() + quantity;

            if (!validateQuantity(product, newQuantity).isValid()) {
                return new OperationResult(false, "Insufficient inventory.");
            }

            result = db.<CartProduct>update(DataMap.CARTS, (cartProd) -> cartProd.setQuantity(newQuantity), productId);
        } else {
            if (!validateQuantity(product, quantity).isValid()) {
                return new OperationResult(false, "Insufficient inventory.");
            }

            result = db.add(DataMap.CARTS, new CartProduct(buyerId, productId, quantity));
        }

        if (result) {
            return new OperationResult(true, "Product successfully added to cart.");
        } else {
            return new OperationResult(false, "Product could not be added to cart.");
        }
    }

    /**
     * Validates the specified quantity for a product, checking if it is greater than 0
     * and does not exceed the available stock.
     *
     * @param product  The product to validate the quantity for.
     * @param quantity The quantity to validate.
     *
     * @return An {@code OperationResult} indicating the validation result.
     *         If the quantity is valid, the result is marked as successful; otherwise, it contains an error message.
     */
    private OperationResult validateQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            return new OperationResult(false, "Quantity must be greater than 0.");
        }

        if (product != null) {
            if (quantity <= product.getQuantity()) {
                return new OperationResult(true, "");
            } else {
                return new OperationResult(false, "Only " + product.getQuantity() + " of this item are left in stock.");
            }
        }

        return new OperationResult(false, "");
    }

    /**
     * Removes a specified quantity of a product from the user's cart.
     *
     * @param cartProductId The unique identifier of the cart product.
     * @param quantity      The quantity to remove from the cart.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
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
            } else {
                return new OperationResult(false, "Failed to update item quantity.");
            }
        } else {
            boolean result = db.remove(DataMap.CARTS, cartProductId);
            if (result) {
                return new OperationResult(true, "Item removed from cart.");
            } else {
                return new OperationResult(false, "Failed to remove item.");
            }
        }
    }

    /**
     * Calculates the remaining cost left to pay and the unused fidelity points.
     *
     * @param cost           The amount to pay.
     * @param fidelityPoints The amount of fidelity points available.
     *
     * @return A tuple containing the remaining cost and the remaining fidelity points.
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

    /**
     * Processes the creation of orders based on the items in the user's cart.
     *
     * @param buyerId              The unique identifier of the buyer.
     * @param email                The email address for order communication.
     * @param phone                The phone number for order communication.
     * @param shippingAddress      The shipping address for the order.
     * @param billingAddress       The billing address for the order.
     * @param creditCardName       The name on the credit card used for payment.
     * @param creditCardNumber     The credit card number used for payment.
     * @param creditCardExpiration The expiration date of the credit card.
     * @param creditCardCVC        The CVC code of the credit card.
     * @param fidelityPointsUsed   The amount of fidelity points used in the order.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
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

            PaymentMethod paymentMethod = new PaymentMethod(subTotalCost, fidelityPointsUsed, 0);

            // Create the sub-order
            Order order = new Order(tuples, subTotalCost, subTotalFidelityPoints, paymentMethod, email, phone, shippingAddress, billingAddress, creditCardName, creditCardNumber, creditCardExpiration, creditCardCVC, buyerId, sellerId);
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

    /**
     * Retrieves the products in the user's cart along with associated details.
     *
     * @param buyerId The unique identifier of the buyer.
     *
     * @return A list of tuples containing cart product details and associated product information.
     */
    public List<Tuple<CartProduct, Product>> getCart(UUID buyerId) {
        List<CartProduct> cartProductList = db.get(DataMap.CARTS, (cartProduct -> cartProduct.getBuyerId() == buyerId));
        return cartProductList.stream().map((cartProd) -> new Tuple<CartProduct, Product>(cartProd, db.get(DataMap.PRODUCTS, cartProd.getProductId()))).toList();
    }

    /**
     * Empties the user's cart.
     *
     * @param buyerId The unique identifier of the buyer.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult emptyCart(UUID buyerId) {
        boolean result = db.<CartProduct>remove(DataMap.CARTS, (cartProduct) -> cartProduct.getBuyerId() == buyerId);
        if (result) {
            return new OperationResult(true, "Cart emptied.");
        } else {
            return new OperationResult(false, "The cart could not be emptied.");
        }
    }

    /**
     * Retrieves orders based on specified criteria.
     *
     * @param buyerId  The unique identifier of the buyer (can be {@code null}).
     * @param sellerId The unique identifier of the seller (can be {@code null}).
     *
     * @return A list of orders that match the specified criteria.
     */
    public List<Order> getOrders(UUID buyerId, UUID sellerId) {
        return db.get(DataMap.ORDERS, (order) -> {
            if (order.getBuyerId() == buyerId && sellerId == null) {
                return true;
            } else if (order.getSellerId() == sellerId && buyerId == null) {
                return true;
            } else {
                return order.getBuyerId() == buyerId && order.getSellerId() == sellerId;
            }
        });
    }

    /**
     * Retrieves orders based on a custom predicate.
     *
     * @param predicate The predicate used to filter orders.
     *
     * @return A list of orders that match the specified predicate.
     */
    public List<Order> getOrders(Predicate<Order> predicate) {
        return db.get(DataMap.ORDERS, predicate);
    }

    /**
     * Updates the shipment information for a specific order, marking it as in transit.
     *
     * @param orderId              The unique identifier of the order.
     * @param shippingCompany      The shipping company used for the shipment.
     * @param trackingNumber       The tracking number associated with the shipment.
     * @param expectedDeliveryDate The expected delivery date of the shipment.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult shipOrder(UUID orderId, String shippingCompany, String trackingNumber, LocalDate expectedDeliveryDate) {
        Order order = db.get(DataMap.ORDERS, orderId);
        if (order == null) {
            return new OperationResult(false, "Order does not exists.");
        }

        if (order.getState() == OrderState.InProduction && order.getShipment() == null) {
            db.<Order>update(DataMap.ORDERS, (o) -> {
                o.setShipment(new Shipment(trackingNumber, expectedDeliveryDate, shippingCompany));
                o.setState(OrderState.InTransit);
            }, orderId);
            return new OperationResult(true, "Order status updated");
        } else {
            return new OperationResult(false, "Order shipment information cannot be changed.");
        }
    }

    /**
     * Marks a specific order as delivered.
     *
     * @param orderId The unique identifier of the order.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult confirmDelivery(UUID orderId) {
        Order order = db.get(DataMap.ORDERS, orderId);
        if (order == null) {
            return new OperationResult(false, "Order does not exists.");
        }

        if (order.getState() == OrderState.InTransit) {
            db.<Order>update(DataMap.ORDERS, o -> o.setState(OrderState.Delivered), orderId);
            return new OperationResult(true, "Order successfully marked as delivered");
        } else {
            return new OperationResult(false, "Order cannot be marked as delivered.");
        }
    }

    /**
     * Cancels a specific order that is still in production.
     *
     * @param orderId The unique identifier of the order.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult cancelOrder(UUID orderId) {
        Order order = db.get(DataMap.ORDERS, orderId);
        if (order == null) {
            return new OperationResult(false, "Order does not exists.");
        }

        if (order.getState() == OrderState.InProduction) {
            db.<Order>update(DataMap.ORDERS, o -> o.setState(OrderState.Cancelled), orderId);
            return new OperationResult(true, "Order cancelled.");
        } else {
            return new OperationResult(false, "Order cannot be marked as cancelled.");
        }
    }

    /**
     * Validates the quantity for a specified product.
     *
     * @param productId The unique identifier of the product.
     * @param quantity  The quantity to validate.
     *
     * @return An {@code OperationResult} indicating the validity of the quantity.
     */
    public OperationResult validateQuantity(UUID productId, int quantity) {
        Product product = db.get(DataMap.PRODUCTS, productId);
        return validateQuantity(product, quantity);
    }
}
