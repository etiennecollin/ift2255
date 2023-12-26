/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.UniShop;
import com.etiennecollin.ift2255.clientCLI.model.ProfileModel;
import com.etiennecollin.ift2255.clientCLI.model.Session;
import com.etiennecollin.ift2255.clientCLI.model.SocialModel;
import com.etiennecollin.ift2255.clientCLI.model.data.*;
import com.etiennecollin.ift2255.clientCLI.views.*;

import java.util.List;
import java.util.UUID;

public class ProfileController {
    private final ViewRenderer renderer;
    private final ProfileModel profileModel;
    private final SocialModel socialModel;

    public ProfileController(ViewRenderer renderer, ProfileModel profileModel, SocialModel socialModel) {
        this.renderer = renderer;
        this.profileModel = profileModel;
        this.socialModel = socialModel;
    }

    public void displayUserFinder() {
        renderer.addNextView(new UserFinder(this), true);
    }

    public void displayBuyerProfile() {
        renderer.addNextView(new BuyerProfile(this, UniShop.getInstance().getAuthController()), false);
    }

    public void displayBuyers(List<Buyer> buyers) {
        renderer.addNextView(new BuyersDisplay(this, buyers), true);
    }

    public Buyer getBuyer() {
        return profileModel.getBuyer(Session.getInstance().getUserId());
    }

    public Buyer getBuyer(UUID buyerId) {
        return profileModel.getBuyer(buyerId);
    }

    public List<Buyer> searchBuyerName(String name) {
        return profileModel.searchBuyers((buyer) ->
                buyer.getFirstName().contains(name) ||
                buyer.getLastName().contains(name) ||
                buyer.getUsername().contains(name)
        );
    }

    public List<Buyer> searchBuyerPhone(String phone) {
        return profileModel.searchBuyers((buyer) -> buyer.getPhoneNumber().trim().contains(phone.trim()));
    }

    public List<Buyer> searchBuyerEmail(String email) {
        return profileModel.searchBuyers((buyer) -> buyer.getEmail().contains(email.trim()));
    }

    public OperationResult updateBuyer(String firstName, String lastName, String password, String email, String phone, String address) {
        return profileModel.updateBuyer(Session.getInstance().getUserId(), firstName, lastName, password, email, phone, address);
    }

    public void displaySellerProfile() {
        renderer.addNextView(new SellerProfile(this, UniShop.getInstance().getAuthController()), false);
    }

    public void displaySellers(List<Seller> sellers) {
        renderer.addNextView(new SellersDisplay(this, UniShop.getInstance().getShopController(), sellers), true);
    }

    public Seller getSeller() {
        return profileModel.getSeller(Session.getInstance().getUserId());
    }

    public Seller getSeller(UUID sellerId) {
        return profileModel.getSeller(sellerId);
    }

    public List<Seller> searchSellerName(String name) {
        return profileModel.searchSellers((seller) -> seller.getName().contains(name));
    }

    public List<Seller> searchSellerAddress(String address) {
        return profileModel.searchSellers((seller) -> seller.getAddress().contains(address));
    }

    public List<Seller> searchSellerPhone(String phone) {
        return profileModel.searchSellers((seller) -> seller.getPhoneNumber().contains(phone));
    }

    public List<Seller> searchSellerEmail(String email) {
        return profileModel.searchSellers((seller) -> seller.getEmail().contains(email));
    }

    public OperationResult updateSeller(String name, String password, String email, String phone, String address) {
        return profileModel.updateSeller(Session.getInstance().getUserId(), name, password, email, phone, address);
    }

    public OperationResult toggleLikeSeller(UUID userId) {
        return socialModel.toggleLikeSeller(userId);
    }

    public OperationResult toggleFollowBuyer(UUID userId) {
        return socialModel.toggleFollowBuyer(userId);
    }

    public boolean isLiked(UUID likedUserId) {
        return socialModel.isLiked(likedUserId, Session.getInstance().getUserId());
    }

    public boolean isLiked(UUID likedUserId, UUID likedByUserId) {
        return socialModel.isLiked(likedUserId, likedByUserId);
    }

    public List<Like> getProductLikesByBuyer(UUID buyerId) {
        return socialModel.getLikes(null, buyerId, LikeType.Product);
    }

    public List<Like> getReviewLikesByBuyer(UUID buyerId) {
        return socialModel.getLikes(null, buyerId, LikeType.Review);
    }

    public List<Review> getReviewsByAuthor(UUID authorId) {
        return socialModel.getReviewsByAuthor(authorId);
    }

    public void logout() {
        profileModel.logout();
        renderer.clearViewHistory();
        renderer.addNextView(new MainMenu(UniShop.getInstance().getAuthController()), true);
    }
}
