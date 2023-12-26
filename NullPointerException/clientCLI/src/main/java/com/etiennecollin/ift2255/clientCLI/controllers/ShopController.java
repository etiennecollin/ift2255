/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Tuple;
import com.etiennecollin.ift2255.clientCLI.UniShop;
import com.etiennecollin.ift2255.clientCLI.model.ProfileModel;
import com.etiennecollin.ift2255.clientCLI.model.Session;
import com.etiennecollin.ift2255.clientCLI.model.ShopModel;
import com.etiennecollin.ift2255.clientCLI.model.SocialModel;
import com.etiennecollin.ift2255.clientCLI.model.data.*;
import com.etiennecollin.ift2255.clientCLI.model.data.products.*;
import com.etiennecollin.ift2255.clientCLI.views.*;
import com.etiennecollin.ift2255.clientCLI.views.productDisplay.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public class ShopController {
    private final ViewRenderer renderer;
    private final ShopModel shopModel;
    private final ProfileModel profileModel;
    private final SocialModel socialModel;

    public ShopController(ViewRenderer renderer, ShopModel shopModel, ProfileModel profileModel, SocialModel socialModel) {
        this.renderer = renderer;
        this.shopModel = shopModel;
        this.profileModel = profileModel;
        this.socialModel = socialModel;
    }

    public <T extends Product> T getProduct(Class<T> productClass, UUID productId) {
        return shopModel.getProduct(productClass, productId);
    }

    public List<Product> getProducts(ProductCategory category, Enum<?> subCategory, UUID sellerId) {
        return shopModel.getProducts(category, subCategory, sellerId);
    }

    public List<Product> searchProductsTitleDescription(String searchString) {
        return shopModel.searchProducts((product) -> product.getTitle().toLowerCase().contains(searchString) || product.getDescription().toLowerCase().contains(searchString));
    }

    public List<Product> searchProductsBySeller(UUID sellerId) {
        return shopModel.searchProducts((product) -> product.getSellerId() == sellerId);
    }

    public Seller getSeller(UUID sellerId) {
        return profileModel.getSeller(sellerId);
    }

    public void displayProduct(Product product) {
        if (product instanceof BookOrManual) {
            renderer.addNextView(new BookOrManualDisplay(product.getId(), this), true);
        }
        else if (product instanceof IT) {
            renderer.addNextView(new ITProductDisplay(product.getId(), this), true);
        }
        else if (product instanceof LearningResource) {
            renderer.addNextView(new LearningResourceDisplay(product.getId(), this), true);
        }
        else if (product instanceof OfficeEquipment) {
            renderer.addNextView(new OfficeEquipmentDisplay(product.getId(), this), true);
        }
        else if (product instanceof StationeryArticle) {
            renderer.addNextView(new StationeryArticleDisplay(product.getId(), this), true);
        }
    }

    public void displayProducts(UUID sellerId) {
        renderer.addNextView(new ProductsMenu(sellerId, this), false);
    }

    public void displayProductSearch() {
        renderer.addNextView(new ProductSearch(this), false);
    }

    public void displayOfferProduct() {
        renderer.addNextView(new OfferProduct(this), false);
    }

    public void displayCart() {
        renderer.addNextView(new Cart(this), true);
    }

    public void displayOrderPlacement() {
        renderer.addNextView(new OrderPlacement(this, UniShop.getInstance().getProfileController()), false);
    }

    public void displayReviews(UUID productId) {
        renderer.addNextView(new ProductReviews(productId, this, UniShop.getInstance().getProfileController()), true);
    }

    public void displayProductReview(UUID productId) {
        renderer.addNextView(new ProductReview(productId, this), false);
    }

    public void displayBuyerOrdersMenu() {
        renderer.addNextView(new BuyerOrdersMenu(this, UniShop.getInstance().getProfileController()), true);
    }

    public void displayPendingSellerOrders() {
        renderer.addNextView(new PendingSellerOrders(this, UniShop.getInstance().getProfileController()), true);
    }

    public List<Review> getProductReviews(UUID productId) {
        return socialModel.getReviewsByProduct(productId);
    }

    public Review getProductReviewByUser(UUID productId) {
        return socialModel.getReview(productId, Session.getInstance().getUserId());
    }

    public OperationResult addReview(UUID productId, String content, int rating) {
        return socialModel.addReview(productId, Session.getInstance().getUserId(), content, rating);
    }

    public List<Tuple<CartProduct, Product>> getCart() {
        return shopModel.getCart(Session.getInstance().getUserId());
    }


    public OperationResult emptyCart() {
        return shopModel.emptyCart(Session.getInstance().getUserId());
    }

    public OperationResult toggleLike(UUID productId) {
        return socialModel.toggleLikeProduct(productId);
    }

    public OperationResult addToCart(UUID productId, int quantity) {
        return shopModel.addToCart(Session.getInstance().getUserId(), productId, quantity);
    }

    public OperationResult removeFromCart(UUID cartProductId, int quantity) {
        return shopModel.removeFromCart(cartProductId, quantity);
    }

    public Tuple<Integer, Integer> calculateNewCost(int cost, int fidelityPoints) {
        return shopModel.costAfterFidelityPoints(cost, fidelityPoints);
    }

    public OperationResult createOrder(String email, String phone, String shippingAddress, String billingAddress, String creditCardName, String creditCardNumber, YearMonth creditCardExpiration, String creditCardCVC, int fidelityPointsUsed) {
        return shopModel.createOrders(Session.getInstance().getUserId(), email, phone, shippingAddress, billingAddress, creditCardName, creditCardNumber, creditCardExpiration, creditCardCVC, fidelityPointsUsed);
    }

    public List<Order> getBuyerOrders() {
        return shopModel.getOrders(Session.getInstance().getUserId(), null);
    }

    public List<Order> getSellerOrders(UUID sellerId) {
        return shopModel.getOrders(null, sellerId);
    }

    public OperationResult shipOrder(UUID orderId, String shippingCompany, String trackingNumber, LocalDate expectedDeliveryDate) {
        return shopModel.shipOrder(orderId, shippingCompany, trackingNumber, expectedDeliveryDate);
    }

    public OperationResult confirmDelivery(UUID orderId) {
        return shopModel.confirmDelivery(orderId);
    }

    public OperationResult cancelOrder(UUID orderId) {
        return shopModel.cancelOrder(orderId);
    }

    public List<Order> getPendingSellerOrders() {
        UUID sellerId = Session.getInstance().getUserId();
        return shopModel.getOrders((order) -> order.getSellerId() == sellerId && order.getState() == OrderState.InProduction);
    }

    public OperationResult submitNewBookOrManual(int price, int quantity, String title, String description, int fidelityPoints, String isbn, String author, String editor, BookOrManualGenre genre, LocalDate releaseDate, int editionNumber, int volumeNumber) {
        OperationResult result = shopModel.createNewBookOrManual(Session.getInstance().getUserId(), price, quantity, title, description, fidelityPoints, isbn, author, editor, genre, releaseDate, editionNumber, volumeNumber);

        if (result.isValid()) {
            result = new OperationResult(true, "Product " + title + " added!");
        }

        return result;
    }

    public OperationResult submitNewITProduct(int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, LocalDate releaseDate, ITCategory subCategory) {
        OperationResult result = shopModel.createNewITProduct(Session.getInstance().getUserId(), price, quantity, title, description, fidelityPoints, brand, model, releaseDate, subCategory);

        if (result.isValid()) {
            result = new OperationResult(true, "Product " + title + " added!");
        }

        return result;
    }

    public OperationResult submitNewLearningResource(int price, int quantity, String title, String description, int fidelityPoints, String isbn, String organisation, LocalDate releaseDate, LearningResourceType type, int editionNumber) {
        OperationResult result = shopModel.createNewLearningResource(Session.getInstance().getUserId(), price, quantity, title, description, fidelityPoints, isbn, organisation, releaseDate, type, editionNumber);

        if (result.isValid()) {
            result = new OperationResult(true, "Product " + title + " added!");
        }

        return result;
    }

    public OperationResult submitNewOfficeEquipment(int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, OfficeEquipmentCategory subCategory) {
        OperationResult result = shopModel.createNewOfficeEquipment(Session.getInstance().getUserId(), price, quantity, title, description, fidelityPoints, brand, model, subCategory);

        if (result.isValid()) {
            result = new OperationResult(true, "Product " + title + " added!");
        }

        return result;
    }

    public OperationResult submitNewStationeryArticle(int price, int quantity, String title, String description, int fidelityPoints, String brand, String model, StationeryArticleCategory subCategory) {
        OperationResult result = shopModel.createNewStationeryArticle(Session.getInstance().getUserId(), price, quantity, title, description, fidelityPoints, brand, model, subCategory);

        if (result.isValid()) {
            result = new OperationResult(true, "Product " + title + " added!");
        }

        return result;
    }

    public OperationResult validateQuantity(UUID productId, int quantity) {
        return shopModel.validateQuantity(productId, quantity);
    }
}
