/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.UniShop;
import com.etiennecollin.ift2255.clientCLI.models.ProfileModel;
import com.etiennecollin.ift2255.clientCLI.models.Session;
import com.etiennecollin.ift2255.clientCLI.models.SocialModel;
import com.etiennecollin.ift2255.clientCLI.models.data.*;
import com.etiennecollin.ift2255.clientCLI.views.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The ProfileController class is responsible for managing user profiles, interactions, and social activities.
 * It provides methods for displaying user-related views, updating user profiles, and handling social interactions
 * such as likes and follows. The class collaborates with the ViewRenderer, ProfileModel, and SocialModel to achieve
 * these functionalities.
 */
public class ProfileController {
    /**
     * The ViewRenderer responsible for rendering views.
     */
    private final ViewRenderer renderer;
    /**
     * The ProfileModel providing access to profile-related functionality.
     */
    private final ProfileModel profileModel;
    /**
     * The SocialModel providing access to social-related functionality.
     */
    private final SocialModel socialModel;

    /**
     * Constructs a new ProfileController with the specified dependencies.
     *
     * @param renderer     The ViewRenderer responsible for rendering views.
     * @param profileModel The ProfileModel providing access to profile-related functionality.
     * @param socialModel  The SocialModel providing access to social-related functionality.
     */
    public ProfileController(ViewRenderer renderer, ProfileModel profileModel, SocialModel socialModel) {
        this.renderer = renderer;
        this.profileModel = profileModel;
        this.socialModel = socialModel;
    }

    /**
     * Displays the user finder view, allowing users to search for other users.
     */
    public void displayUserFinder() {
        renderer.addNextView(new UserFinder(this, UniShop.getInstance().getShopController()), true);
    }

    /**
     * Displays the buyer profile.
     */
    public void displayBuyerProfile() {
        renderer.addNextView(new BuyerProfile(this, UniShop.getInstance().getAuthController()), false);
    }

    /**
     * Displays a list of buyers.
     *
     * @param buyers The list of buyers to display.
     */
    public void displayBuyers(List<Buyer> buyers) {
        buyers = buyers.stream().filter(buyer -> !buyer.getId().equals(Session.getInstance().getUserId())).collect(Collectors.toCollection(ArrayList::new));
        renderer.addNextView(new BuyersDisplay(this, buyers), true);
    }

    /**
     * Retrieves the current buyer's profile.
     *
     * @return The current buyer's profile.
     */
    public Buyer getBuyer() {
        return profileModel.getBuyer(Session.getInstance().getUserId());
    }

    /**
     * Retrieves a specific buyer's profile.
     *
     * @param buyerId The UUID of the buyer.
     *
     * @return The buyer's profile.
     */
    public Buyer getBuyer(UUID buyerId) {
        return profileModel.getBuyer(buyerId);
    }

    /**
     * Retrieves a list of notifications for the currently authenticated user.
     *
     * @return A list of notifications for the currently authenticated user.
     */
    public List<Notification> getNotifications() {
        return profileModel.getNotifications(Session.getInstance().getUserId());
    }

    /**
     * Removes a notification with the specified identifier.
     *
     * @param notificationId The unique identifier of the notification to be removed.
     */
    public void removeNotification(UUID notificationId) {
        profileModel.removeNotification(notificationId);
    }

    /**
     * Retrieves a list of notifications for the user with the specified identifier.
     *
     * @param userId The unique identifier of the user for whom notifications are retrieved.
     *
     * @return A list of notifications for the specified user.
     */
    public List<Notification> getNotifications(UUID userId) {
        return profileModel.getNotifications(userId);
    }

    /**
     * Retrieves a list of all buyers in the system.
     *
     * @return A list of {@link Buyer} objects representing all buyers.
     */
    public List<Buyer> getBuyers() {
        return profileModel.searchBuyers(buyer -> true);
    }

    /**
     * Checks if the current user is identified as a buyer.
     *
     * @return {@code true} if the current user is a buyer; otherwise, {@code false}.
     */
    public boolean isUserABuyer() {
        return Session.getInstance().getUserType() == UserType.Buyer;
    }

    /**
     * Searches for buyers based on their name.
     *
     * @param name The name to search for.
     *
     * @return A list of buyers matching the search criteria.
     */
    public List<Buyer> searchBuyerName(String name) {
        return profileModel.searchBuyers((buyer) -> buyer.getFirstName().contains(name) || buyer.getLastName().contains(name) || buyer.getUsername().contains(name));
    }

    /**
     * Searches for buyers based on their phone number.
     *
     * @param phone The phone number to search for.
     *
     * @return A list of buyers matching the search criteria.
     */
    public List<Buyer> searchBuyerPhone(String phone) {
        return profileModel.searchBuyers((buyer) -> buyer.getPhoneNumber().trim().contains(phone.trim()));
    }

    /**
     * Searches for buyers based on their email address.
     *
     * @param email The email address to search for.
     *
     * @return A list of buyers matching the search criteria.
     */
    public List<Buyer> searchBuyerEmail(String email) {
        return profileModel.searchBuyers((buyer) -> buyer.getEmail().contains(email.trim()));
    }

    /**
     * Updates the current buyer's profile information.
     *
     * @param firstName The new first name.
     * @param lastName  The new last name.
     * @param password  The new password.
     * @param email     The new email address.
     * @param phone     The new phone number.
     * @param address   The new address.
     *
     * @return An operation result indicating the success or failure of the update.
     */
    public OperationResult updateBuyer(String firstName, String lastName, String password, String email, String phone, String address) {
        return profileModel.updateBuyer(Session.getInstance().getUserId(), firstName, lastName, password, email, phone, address);
    }

    /**
     * Displays the seller profile.
     */
    public void displaySellerProfile() {
        renderer.addNextView(new SellerProfile(this, UniShop.getInstance().getAuthController()), false);
    }

    /**
     * Displays a list of sellers.
     *
     * @param sellers The list of sellers to display.
     */
    public void displaySellers(List<Seller> sellers) {
        renderer.addNextView(new SellersDisplay(this, UniShop.getInstance().getShopController(), sellers), true);
    }

    /**
     * Retrieves the current seller's profile.
     *
     * @return The current seller's profile.
     */
    public Seller getSeller() {
        return profileModel.getSeller(Session.getInstance().getUserId());
    }

    /**
     * Retrieves a specific seller's profile.
     *
     * @param sellerId The UUID of the seller.
     *
     * @return The seller's profile.
     */
    public Seller getSeller(UUID sellerId) {
        return profileModel.getSeller(sellerId);
    }

    /**
     * Retrieves a list of all sellers in the system.
     *
     * @return A list of {@link Seller} objects representing all sellers.
     */
    public List<Seller> getSellers() {
        return profileModel.searchSellers(seller -> true);
    }

    /**
     * Checks if the current user is identified as a seller.
     *
     * @return {@code true} if the current user is a seller; otherwise, {@code false}.
     */
    public boolean isUserASeller() {
        return Session.getInstance().getUserType() == UserType.Seller;
    }

    /**
     * Searches for sellers based on their name.
     *
     * @param name The name to search for.
     *
     * @return A list of sellers matching the search criteria.
     */
    public List<Seller> searchSellerName(String name) {
        return profileModel.searchSellers((seller) -> seller.getName().contains(name));
    }

    /**
     * Searches for sellers based on their address.
     *
     * @param address The address to search for.
     *
     * @return A list of sellers matching the search criteria.
     */
    public List<Seller> searchSellerAddress(String address) {
        return profileModel.searchSellers((seller) -> seller.getAddress().contains(address));
    }

    /**
     * Searches for sellers based on their phone number.
     *
     * @param phone The phone number to search for.
     *
     * @return A list of sellers matching the search criteria.
     */
    public List<Seller> searchSellerPhone(String phone) {
        return profileModel.searchSellers((seller) -> seller.getPhoneNumber().contains(phone));
    }

    /**
     * Searches for sellers based on their email address.
     *
     * @param email The email address to search for.
     *
     * @return A list of sellers matching the search criteria.
     */
    public List<Seller> searchSellerEmail(String email) {
        return profileModel.searchSellers((seller) -> seller.getEmail().contains(email));
    }

    /**
     * Updates the current seller's profile information.
     *
     * @param name     The new name.
     * @param password The new password.
     * @param email    The new email address.
     * @param phone    The new phone number.
     * @param address  The new address.
     *
     * @return An operation result indicating the success or failure of the update.
     */
    public OperationResult updateSeller(String name, String password, String email, String phone, String address) {
        return profileModel.updateSeller(Session.getInstance().getUserId(), name, password, email, phone, address);
    }

    /**
     * Toggles the "like" status for a seller.
     *
     * @param sellerId The UUID of the seller.
     *
     * @return An operation result indicating the success or failure of the toggle.
     */
    public OperationResult toggleLikeSeller(UUID sellerId) {
        return socialModel.toggleLikeSeller(sellerId, Session.getInstance().getUserId());
    }

    /**
     * Toggles the "follow" status for a buyer.
     *
     * @param buyerId The UUID of the buyer.
     *
     * @return An operation result indicating the success or failure of the toggle.
     */
    public OperationResult toggleFollowBuyer(UUID buyerId) {
        return socialModel.toggleFollowBuyer(buyerId, Session.getInstance().getUserId());
    }

    /**
     * Checks if the current user has liked a specific user.
     *
     * @param likedUserId The UUID of the user being checked for likes.
     *
     * @return {@code true} if the current user has liked the specified user, {@code false} otherwise.
     */
    public boolean isLiked(UUID likedUserId) {
        return socialModel.isLiked(likedUserId, Session.getInstance().getUserId());
    }

    /**
     * Checks if a specific user has been liked by another user.
     *
     * @param likedUserId   The UUID of the user being checked for likes.
     * @param likedByUserId The UUID of the user who may have liked the specified user.
     *
     * @return {@code true} if the specified user has been liked by the given user, {@code false} otherwise.
     */
    public boolean isLiked(UUID likedUserId, UUID likedByUserId) {
        return socialModel.isLiked(likedUserId, likedByUserId);
    }

    /**
     * Retrieves the likes for products by a specific buyer.
     *
     * @param buyerId The UUID of the buyer.
     *
     * @return A list of likes for products by the specified buyer.
     */
    public List<Like> getProductLikesByBuyer(UUID buyerId) {
        return socialModel.getLikes(null, buyerId, LikeType.Product);
    }

    /**
     * Retrieves the likes for reviews by a specific buyer.
     *
     * @param buyerId The UUID of the buyer.
     *
     * @return A list of likes for reviews by the specified buyer.
     */
    public List<Like> getReviewLikesByBuyer(UUID buyerId) {
        return socialModel.getLikes(null, buyerId, LikeType.Review);
    }

    /**
     * Retrieves the reviews written by a specific author.
     *
     * @param authorId The UUID of the author.
     *
     * @return A list of reviews written by the specified author.
     */
    public List<Review> getReviewsByAuthor(UUID authorId) {
        return socialModel.getReviewsByAuthor(authorId);
    }

    /**
     * Logs out the current user, clearing the view history and displaying the main menu.
     */
    public void logout() {
        profileModel.logout();
        renderer.clearViewHistory();
        renderer.addNextView(new MainMenu(UniShop.getInstance().getAuthController()), true);
    }

    /**
     * Displays the seller activities view for the currently logged-in seller.
     * The method adds the {@link SellerActivities} view to the renderer, allowing sellers to view and manage their recent and overall performance metrics,
     * including revenue, products sold, and product ratings.
     */
    public void displaySellerActivities() {
        renderer.addNextView(new SellerActivities(this, UniShop.getInstance().getShopController()), false);
    }

    /**
     * Displays the buyer activities view for the currently logged-in buyer.
     * The method adds the {@link BuyerActivities} view to the renderer, allowing buyers to view and manage their recent and overall performance metrics,
     * including orders, products bought, likes, and reviews.
     */
    public void displayBuyerActivities() {
        renderer.addNextView(new BuyerActivities(this, UniShop.getInstance().getShopController()), false);
    }

    /**
     * Displays the user notifications view, allowing users to view and manage their notifications in the CLI application.
     * It adds a {@link UserNotifications} view to the rendering queue for user interaction.
     * Notifications are presented in a paginated manner, and users can choose to delete specific notifications.
     */
    public void displayNotifications() {
        renderer.addNextView(new UserNotifications(this), false);
    }
}
