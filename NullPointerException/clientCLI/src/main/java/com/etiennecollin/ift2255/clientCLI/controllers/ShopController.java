/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.UniShop;
import com.etiennecollin.ift2255.clientCLI.models.ProfileModel;
import com.etiennecollin.ift2255.clientCLI.models.Session;
import com.etiennecollin.ift2255.clientCLI.models.ShopModel;
import com.etiennecollin.ift2255.clientCLI.models.SocialModel;
import com.etiennecollin.ift2255.clientCLI.models.data.*;
import com.etiennecollin.ift2255.clientCLI.models.data.products.*;
import com.etiennecollin.ift2255.clientCLI.views.*;
import com.etiennecollin.ift2255.clientCLI.views.productDisplay.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

/**
 * Controller class responsible for handling operations related to the online shop.
 */
public class ShopController {
    /**
     * The view renderer to display views.
     */
    private final ViewRenderer renderer;
    /**
     * The shop models to interact with shop data.
     */
    private final ShopModel shopModel;
    /**
     * The profile models to interact with user profiles.
     */
    private final ProfileModel profileModel;
    /**
     * The social models to interact with social features.
     */
    private final SocialModel socialModel;

    /**
     * Constructs a new ShopController.
     *
     * @param renderer     The view renderer to display views.
     * @param shopModel    The shop models to interact with shop data.
     * @param profileModel The profile models to interact with user profiles.
     * @param socialModel  The social models to interact with social features.
     */
    public ShopController(ViewRenderer renderer, ShopModel shopModel, ProfileModel profileModel, SocialModel socialModel) {
        this.renderer = renderer;
        this.shopModel = shopModel;
        this.profileModel = profileModel;
        this.socialModel = socialModel;
    }

    /**
     * Retrieves a specific product by its UUID and class type.
     *
     * @param productClass The class type of the product.
     * @param productId    The UUID of the product to retrieve.
     * @param <T>          The type of the product.
     *
     * @return The product with the specified UUID and type.
     */
    public <T extends Product> T getProduct(Class<T> productClass, UUID productId) {
        return shopModel.getProduct(productClass, productId);
    }

    /**
     * Retrieves a list of products based on category, subcategory, and seller ID.
     *
     * @param category    The main category of the products.
     * @param subCategory The subcategory of the products.
     * @param sellerId    The UUID of the seller.
     *
     * @return A list of products based on the specified criteria.
     */
    public List<Product> getProducts(ProductCategory category, Enum<?> subCategory, UUID sellerId) {
        return shopModel.getProducts(category, subCategory, sellerId);
    }

    /**
     * Searches for products based on title or description containing the specified search string.
     *
     * @param searchString The search string to match against product titles and descriptions.
     *
     * @return A list of products matching the search criteria.
     */
    public List<Product> searchProductsTitleDescription(String searchString) {
        return shopModel.searchProducts((product) -> product.getTitle().toLowerCase().contains(searchString) || product.getDescription().toLowerCase().contains(searchString));
    }

    /**
     * Searches for products associated with a specific seller.
     *
     * @param sellerId The UUID of the seller.
     *
     * @return A list of products associated with the specified seller.
     */
    public List<Product> searchProductsBySeller(UUID sellerId) {
        return shopModel.searchProducts((product) -> product.getSellerId().equals(sellerId));
    }

    /**
     * Retrieves information about a specific seller.
     *
     * @param sellerId The UUID of the seller.
     *
     * @return The seller information.
     */
    public Seller getSeller(UUID sellerId) {
        return profileModel.getSeller(sellerId);
    }

    /**
     * Displays detailed information about a specific product.
     *
     * @param product The product to display.
     */
    public void displayProduct(Product product) {
        if (product instanceof BookOrManual) {
            renderer.addNextView(new BookOrManualDisplay(product.getId(), this, UniShop.getInstance().getProfileController()), true);
        } else if (product instanceof IT) {
            renderer.addNextView(new ITProductDisplay(product.getId(), this, UniShop.getInstance().getProfileController()), true);
        } else if (product instanceof LearningResource) {
            renderer.addNextView(new LearningResourceDisplay(product.getId(), this, UniShop.getInstance().getProfileController()), true);
        } else if (product instanceof OfficeEquipment) {
            renderer.addNextView(new OfficeEquipmentDisplay(product.getId(), this, UniShop.getInstance().getProfileController()), true);
        } else if (product instanceof StationeryArticle) {
            renderer.addNextView(new StationeryArticleDisplay(product.getId(), this, UniShop.getInstance().getProfileController()), true);
        }
    }

    /**
     * Displays a menu with a list of products associated with a specific seller.
     * This method is intended for sellers to view and manage their products.
     * The menu allows the seller to perform actions such as editing, removing, and viewing details of their products.
     * Accessible only for users with the role of Seller.
     */
    public void displaySellerProducts() {
        if (Session.getInstance().getUserType() == UserType.Seller) {
            displayProducts(Session.getInstance().getUserId());
        }
    }

    /**
     * Displays a menu with a list of products associated with a specific seller.
     *
     * @param sellerId The UUID of the seller.
     */
    public void displayProducts(UUID sellerId) {
        renderer.addNextView(new ProductsMenu(sellerId, this), true);
    }

    /**
     * Displays a menu for searching products.
     */
    public void displayProductSearch() {
        renderer.addNextView(new ProductSearch(this), false);
    }

    /**
     * Displays a menu for offering a new product.
     */
    public void displayOfferProduct() {
        renderer.addNextView(new OfferProduct(this), false);
    }

    /**
     * Displays the shopping cart.
     */
    public void displayCart() {
        renderer.addNextView(new Cart(this), true);
    }

    /**
     * Displays the order placement menu.
     */
    public void displayOrderPlacement() {
        renderer.addNextView(new OrderPlacement(this, UniShop.getInstance().getProfileController()), false);
    }

    /**
     * Displays reviews for a specific product.
     *
     * @param productId The UUID of the product.
     */
    public void displayReviews(UUID productId) {
        renderer.addNextView(new ProductReviews(productId, this, UniShop.getInstance().getProfileController()), true);
    }

    /**
     * Displays the menu for adding or updating a product review.
     *
     * @param productId The UUID of the product.
     */
    public void displayProductReview(UUID productId) {
        renderer.addNextView(new ProductReview(productId, this), true);
    }

    /**
     * Displays the menu for buyer's orders.
     */
    public void displayBuyerOrdersMenu() {
        renderer.addNextView(new BuyerOrdersMenu(null, this, UniShop.getInstance().getProfileController(), UniShop.getInstance().getTicketController()), true);
    }

    /**
     * Displays the menu for buyer's orders.
     *
     * @param orders The specific orders to display.
     */
    public void displayBuyerOrdersMenu(List<Order> orders) {
        renderer.addNextView(new BuyerOrdersMenu(orders, this, UniShop.getInstance().getProfileController(), UniShop.getInstance().getTicketController()), true);
    }

    /**
     * Displays pending orders for the seller.
     */
    public void displayPendingSellerOrders() {
        renderer.addNextView(new PendingSellerOrders(null, this, UniShop.getInstance().getProfileController()), true);
    }

    /**
     * Displays pending orders for the seller.
     *
     * @param orders The specific orders to display.
     */
    public void displayPendingSellerOrders(List<Order> orders) {
        renderer.addNextView(new PendingSellerOrders(orders, this, UniShop.getInstance().getProfileController()), true);
    }

    /**
     * Retrieves reviews for a specific product.
     *
     * @param productId The UUID of the product.
     *
     * @return A list of reviews for the specified product.
     */
    public List<Review> getProductReviews(UUID productId) {
        return socialModel.getReviewsByProduct(productId);
    }

    /**
     * Retrieves a specific review associated with a product and author.
     *
     * @param productId The unique identifier of the product.
     * @param authorId  The unique identifier of the author (buyer) of the review.
     *
     * @return A {@link Review} object representing the review associated with the specified product and author.
     */
    public Review getProductReview(UUID productId, UUID authorId) {
        return socialModel.getReview(productId, authorId);
    }

    /**
     * Retrieves a specific review based on its unique identifier.
     *
     * @param reviewId The unique identifier of the review.
     *
     * @return A {@link Review} object representing the review with the specified unique identifier.
     */
    public Review getProductReview(UUID reviewId) {
        return socialModel.getReview(reviewId);
    }

    /**
     * Retrieves a review for a specific product written by the currently logged-in user.
     *
     * @param productId The UUID of the product.
     *
     * @return The review written by the currently logged-in user for the specified product.
     */
    public Review getProductReviewByUser(UUID productId) {
        return socialModel.getReview(productId, Session.getInstance().getUserId());
    }

    /**
     * Initiates a promotion for a specific product, providing a discount, promotional points, and an end date.
     * Sellers can use this method to start promotions for their products.
     *
     * @param productId   The UUID of the product to be promoted.
     * @param discount    The discount percentage to apply during the promotion.
     * @param promoPoints The promotional points awarded during the promotion.
     * @param endDate     The end date of the promotion.
     *
     * @return The result of the operation, indicating whether the promotion was successfully started or not.
     */
    public OperationResult startProductPromotion(UUID productId, int discount, int promoPoints, LocalDate endDate) {
        return shopModel.startProductPromotion(productId, discount, promoPoints, endDate);
    }

    /**
     * Adds a new review for a specific product.
     *
     * @param productId The UUID of the product.
     * @param content   The content of the review.
     * @param rating    The rating given in the review.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult addReview(UUID productId, String content, int rating) {
        return socialModel.addReview(productId, Session.getInstance().getUserId(), content, rating);
    }

    /**
     * Retrieves the contents of the shopping cart.
     *
     * @return A list of tuples representing the contents of the shopping cart.
     */
    public List<Tuple<CartProduct, Product>> getCart() {
        return shopModel.getCart(Session.getInstance().getUserId());
    }

    /**
     * Empties the contents of the shopping cart.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult emptyCart() {
        return shopModel.emptyCart(Session.getInstance().getUserId());
    }

    /**
     * Toggles the like status of a product.
     *
     * @param productId The UUID of the product.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult toggleLike(UUID productId) {
        return socialModel.toggleLikeProduct(productId, Session.getInstance().getUserId());
    }

    /**
     * Retrieves a list of likes associated with a specified entity (e.g., review).
     *
     * @param entity The unique identifier of the entity.
     *
     * @return A list of {@link Like} objects representing likes associated with the specified entity.
     */
    public List<Like> getLikes(UUID entity) {
        return socialModel.getLikes(entity, null, LikeType.Review);
    }

    /**
     * Toggles the like status for a review.
     *
     * @param reviewId The unique identifier of the review.
     *
     * @return An {@link OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult toggleLikeReview(UUID reviewId) {
        return socialModel.toggleLikeReview(reviewId, Session.getInstance().getUserId());
    }

    /**
     * Marks a review as inappropriate.
     *
     * @param reviewId The unique identifier of the review.
     *
     * @return An {@link OperationResult} indicating the success or failure of marking the review.
     */
    public OperationResult markReviewAsInappropriate(UUID reviewId) {
        return socialModel.markReviewAsInappropriate(reviewId);
    }

    /**
     * Adds a product to the shopping cart with a specified quantity.
     *
     * @param productId The UUID of the product.
     * @param quantity  The quantity to add to the shopping cart.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult addToCart(UUID productId, int quantity) {
        Session session = Session.getInstance();
        UUID userId = session.getUserId();

        if (session.getIsInExchangeProcess() && session.getExchangeCart() != null) {
            return shopModel.addToCart(userId, productId, quantity, session.getExchangeCart());
        } else {
            return shopModel.addToCart(userId, productId, quantity, null);
        }
    }

    /**
     * Removes a specified quantity of a product from the shopping cart.
     *
     * @param cartProductId The UUID of the product in the shopping cart.
     * @param quantity      The quantity to remove from the shopping cart.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult removeFromCart(UUID cartProductId, int quantity) {
        Session session = Session.getInstance();

        if (session.getIsInExchangeProcess() && session.getExchangeCart() != null) {
            return shopModel.removeFromCart(cartProductId, quantity, session.getExchangeCart());
        } else {
            return shopModel.removeFromCart(cartProductId, quantity, null);
        }
    }

    /**
     * Calculates the new cost of a product after applying fidelity points.
     *
     * @param cost           The original cost of the product.
     * @param fidelityPoints The number of fidelity points to apply.
     *
     * @return A tuple containing the new cost and remaining fidelity points.
     */
    public Tuple<Integer, Integer> calculateNewCost(int cost, int fidelityPoints) {
        return shopModel.costAfterFidelityPoints(cost, fidelityPoints);
    }

    /**
     * Creates a new order with the specified details.
     *
     * @param email                The email address for order communication.
     * @param phone                The phone number for order communication.
     * @param shippingAddress      The shipping address for the order.
     * @param billingAddress       The billing address for the order.
     * @param creditCardName       The name on the credit card used for payment.
     * @param creditCardNumber     The credit card number used for payment.
     * @param creditCardExpiration The expiration date of the credit card.
     * @param creditCardCVC        The CVC code of the credit card.
     * @param fidelityPointsUsed   The number of fidelity points used in the order.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult createOrder(String email, String phone, String shippingAddress, String billingAddress, String creditCardName, String creditCardNumber, YearMonth creditCardExpiration, String creditCardCVC, int fidelityPointsUsed) {
        return shopModel.createOrders(Session.getInstance().getUserId(), email, phone, shippingAddress, billingAddress, creditCardName, creditCardNumber, creditCardExpiration, creditCardCVC, fidelityPointsUsed);
    }

    /**
     * Retrieves a list of orders placed by the buyer.
     *
     * @return A list of orders placed by the buyer.
     */
    public List<Order> getBuyerOrders() {
        return shopModel.getOrders(Session.getInstance().getUserId(), null);
    }

    /**
     * Retrieves a list of orders associated with a specific seller.
     *
     * @param sellerId The UUID of the seller.
     *
     * @return A list of orders associated with the specified seller.
     */
    public List<Order> getSellerOrders(UUID sellerId) {
        return shopModel.getOrders(null, sellerId);
    }

    /**
     * Retrieves the order associated with the order ID.
     *
     * @param orderId The UUID of the order.
     *
     * @return The order associated with the order ID.
     */
    public Order getOrder(UUID orderId) {
        return shopModel.getOrder(orderId);
    }

    /**
     * Ships an order with the specified details.
     *
     * @param orderId              The UUID of the order to ship.
     * @param shippingCompany      The shipping company used for the shipment.
     * @param trackingNumber       The tracking number of the shipment.
     * @param expectedDeliveryDate The expected delivery date of the shipment.
     *
     * @return The result of the operation (success or failure).
     */
    public OperationResult shipOrder(UUID orderId, String shippingCompany, String trackingNumber, LocalDate expectedDeliveryDate) {
        return shopModel.shipOrder(orderId, shippingCompany, trackingNumber, expectedDeliveryDate);
    }

    /**
     * Confirms the delivery of an order.
     *
     * @param orderId The UUID of the order to confirm delivery for.
     *
     * @return An operation result indicating the success or failure of the operation.
     */
    public OperationResult confirmDelivery(UUID orderId) {
        return shopModel.confirmDelivery(orderId);
    }

    /**
     * Cancels a specific order.
     *
     * @param orderId The UUID of the order to be canceled.
     *
     * @return An operation result indicating the success or failure of the operation.
     */
    public OperationResult cancelOrder(UUID orderId) {
        return shopModel.cancelOrder(orderId);
    }

    /**
     * Retrieves a list of pending orders for a seller in the "In Production" state.
     *
     * @return A list of pending seller orders.
     */
    public List<Order> getPendingSellerOrders() {
        UUID sellerId = Session.getInstance().getUserId();
        return shopModel.getOrders((order) -> order.getSellerId().equals(sellerId) && order.getState() == OrderState.InProduction);
    }

    /**
     * Submits a new book or manual product to the shop.
     *
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
     * @return An operation result indicating the success or failure of the operation.
     */
    public OperationResult submitNewBookOrManual(int price, int quantity, String title, String description, int fidelityPoints, String isbn, String author, String editor, BookOrManualGenre genre, LocalDate releaseDate, int editionNumber, int volumeNumber) {
        OperationResult result = shopModel.createNewBookOrManual(Session.getInstance().getUserId(), price, quantity, title, description, fidelityPoints, isbn, author, editor, genre, releaseDate, editionNumber, volumeNumber);

        if (result.isValid()) {
            result = new OperationResult(true, "Product " + title + " added!");
        }

        return result;
    }

    /**
     * Submits a new IT product to the shop.
     *
     * @param price          The price of the product.
     * @param quantity       The quantity of the product.
     * @param title          The title of the product.
     * @param description    The description of the product.
     * @param fidelityPoints The fidelity points associated with the product.
     * @param brand          The brand of the IT product.
     * @param model          The models of the IT product.
     * @param releaseDate    The release date of the IT product.
     * @param subCategory    The sub-category of the IT product.
     *
     * @return An operation result indicating the success or failure of the operation.
     */
    public OperationResult submitNewITProduct(int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, LocalDate releaseDate, ITCategory subCategory) {
        OperationResult result = shopModel.createNewITProduct(Session.getInstance().getUserId(), price, quantity, title, description, fidelityPoints, brand, model, releaseDate, subCategory);

        if (result.isValid()) {
            result = new OperationResult(true, "Product " + title + " added!");
        }

        return result;
    }

    /**
     * Submits a new learning resource product to the shop.
     *
     * @param price          The price of the product.
     * @param quantity       The quantity of the product.
     * @param title          The title of the product.
     * @param description    The description of the product.
     * @param fidelityPoints The fidelity points associated with the product.
     * @param isbn           The ISBN of the learning resource.
     * @param organisation   The organization related to the learning resource.
     * @param releaseDate    The release date of the learning resource.
     * @param type           The type of the learning resource.
     * @param editionNumber  The edition number of the learning resource.
     *
     * @return An operation result indicating the success or failure of the operation.
     */
    public OperationResult submitNewLearningResource(int price, int quantity, String title, String description, int fidelityPoints, String isbn, String organisation, LocalDate releaseDate, LearningResourceType type, int editionNumber) {
        OperationResult result = shopModel.createNewLearningResource(Session.getInstance().getUserId(), price, quantity, title, description, fidelityPoints, isbn, organisation, releaseDate, type, editionNumber);

        if (result.isValid()) {
            result = new OperationResult(true, "Product " + title + " added!");
        }

        return result;
    }

    /**
     * Submits a new office equipment product to the shop.
     *
     * @param price          The price of the product.
     * @param quantity       The quantity of the product.
     * @param title          The title of the product.
     * @param description    The description of the product.
     * @param fidelityPoints The fidelity points associated with the product.
     * @param brand          The brand of the office equipment.
     * @param model          The models of the office equipment.
     * @param subCategory    The sub-category of the office equipment.
     *
     * @return An operation result indicating the success or failure of the operation.
     */
    public OperationResult submitNewOfficeEquipment(int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, OfficeEquipmentCategory subCategory) {
        OperationResult result = shopModel.createNewOfficeEquipment(Session.getInstance().getUserId(), price, quantity, title, description, fidelityPoints, brand, model, subCategory);

        if (result.isValid()) {
            result = new OperationResult(true, "Product " + title + " added!");
        }

        return result;
    }

    /**
     * Submits a new stationery article product to the shop.
     *
     * @param price          The price of the product.
     * @param quantity       The quantity of the product.
     * @param title          The title of the product.
     * @param description    The description of the product.
     * @param fidelityPoints The fidelity points associated with the product.
     * @param brand          The brand of the stationery article.
     * @param model          The models of the stationery article.
     * @param subCategory    The sub-category of the stationery article.
     *
     * @return An operation result indicating the success or failure of the operation.
     */
    public OperationResult submitNewStationeryArticle(int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, StationeryArticleCategory subCategory) {
        OperationResult result = shopModel.createNewStationeryArticle(Session.getInstance().getUserId(), price, quantity, title, description, fidelityPoints, brand, model, subCategory);

        if (result.isValid()) {
            result = new OperationResult(true, "Product " + title + " added!");
        }

        return result;
    }

    /**
     * Validates the quantity of a product before adding it to the cart.
     *
     * @param productId The UUID of the product.
     * @param quantity  The quantity to validate.
     *
     * @return An operation result indicating the success or failure of the validation.
     */
    public OperationResult validateQuantity(UUID productId, int quantity) {
        return shopModel.validateQuantity(productId, quantity);
    }
}
