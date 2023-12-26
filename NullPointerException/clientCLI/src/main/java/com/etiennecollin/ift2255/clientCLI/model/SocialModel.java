/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.model.data.*;
import com.etiennecollin.ift2255.clientCLI.model.data.products.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SocialModel {
    private final Database db;

    public SocialModel(Database database) {
        this.db = database;
    }

    public OperationResult toggleLikeProduct(UUID productId) {
        UUID userId = Session.getInstance().getUserId();
        Product product = db.get(DataMap.PRODUCTS, productId);
        if (product != null) {
            if (isLiked(productId, userId)) {
                db.<Like>remove(DataMap.CARTS, (entry) -> entry.getLikedEntityId() == productId && entry.getUserId() == userId);
                int likes = product.getLikes() - 1;
                db.<Product>update(DataMap.PRODUCTS, (prod) -> prod.setLikes(likes), productId);
                return new OperationResult(true, "Like removed.");
            } else {
                db.add(DataMap.LIKES, new Like(productId, userId, LikeType.Product));
                int likes = product.getLikes() + 1;
                db.<Product>update(DataMap.PRODUCTS, (prod) -> prod.setLikes(likes), productId);
                return new OperationResult(true, "Like added.");
            }
        }

        return new OperationResult(false, "Product does not exist.");
    }

    public boolean isLiked(UUID likedEntity, UUID likedByUser) {
        return db.<Like>get(DataMap.CARTS, (entry) -> entry.getLikedEntityId() == likedEntity && entry.getUserId() == likedByUser).size() > 0;
    }

    public OperationResult toggleLikeReview(UUID reviewId) {
        UUID userId = Session.getInstance().getUserId();
        Review review = db.get(DataMap.REVIEWS, reviewId);

        if (review != null) {
            List<Like> likes = db.get(DataMap.LIKES, (like) -> like.getLikedEntityId() == reviewId);
            Optional<Like> likedByUser = likes.stream().filter((like) -> like.getUserId() == userId).findFirst();

            if (likedByUser.isEmpty()) {
                if (likes.size() == 0 && !review.getIsReported()) {
                    db.<Buyer>update(DataMap.BUYERS, (buyer) -> buyer.setFidelityPoints(buyer.getFidelityPoints() + 10), review.getAuthorId());
                }
                db.add(DataMap.LIKES, new Like(reviewId, userId, LikeType.Review));
                return new OperationResult(true, "Like removed.");
            } else {
                if (likes.size() == 1 && !review.getIsReported()) {
                    db.<Buyer>update(DataMap.BUYERS, (buyer) -> buyer.setFidelityPoints(buyer.getFidelityPoints() - 10), review.getAuthorId());
                }
                db.remove(DataMap.LIKES, likedByUser.get().getId());
                return new OperationResult(true, "Like added.");
            }
        }

        return new OperationResult(false, "Seller no longer exists.");
    }

    public OperationResult toggleLikeSeller(UUID sellerId) {
        UUID userId = Session.getInstance().getUserId();
        Seller seller = db.get(DataMap.SELLERS, sellerId);

        if (seller != null) {
            List<Like> likes = db.get(DataMap.LIKES, (like) -> like.getLikedEntityId() == sellerId);
            Optional<Like> likedByUser = likes.stream().filter((like) -> like.getUserId() == userId).findFirst();

            if (likedByUser.isEmpty()) {
                db.add(DataMap.LIKES, new Like(sellerId, userId, LikeType.Seller));
                return new OperationResult(true, "Like removed.");
            } else {
                db.remove(DataMap.LIKES, likedByUser.get().getId());
                return new OperationResult(true, "Like added.");
            }
        }

        return new OperationResult(false, "Seller no longer exists.");
    }

    public OperationResult toggleFollowBuyer(UUID buyerId) {
        UUID userId = Session.getInstance().getUserId();
        Buyer buyer = db.get(DataMap.BUYERS, buyerId);

        if (buyer != null) {
            List<Like> likes = db.get(DataMap.LIKES, (like) -> like.getLikedEntityId() == buyerId);
            Optional<Like> likedByUser = likes.stream().filter((like) -> like.getUserId() == userId).findFirst();

            if (likedByUser.isEmpty()) {
                db.add(DataMap.LIKES, new Like(buyerId, userId, LikeType.Buyer));
                db.<Buyer>update(DataMap.BUYERS, (b) -> b.setFidelityPoints(buyer.getFidelityPoints() + 5), (b) -> b.getId() == userId || b.getId() == buyerId);
                return new OperationResult(true, "Unfollowed. -5 fidelity points.");
            } else {
                db.remove(DataMap.LIKES, likedByUser.get().getId());
                db.<Buyer>update(DataMap.BUYERS, (b) -> b.setFidelityPoints(buyer.getFidelityPoints() - 5), (b) -> b.getId() == userId || b.getId() == buyerId);
                return new OperationResult(true, "Following this user. +5 fidelity points.");
            }
        }

        return new OperationResult(false, "Seller no longer exists.");
    }

    public List<Like> getLikes(UUID likee, UUID liker, LikeType type) {
        return db.get(DataMap.LIKES, (like) -> (likee == null || like.getLikedEntityId() == likee) && (liker == null || like.getUserId() == liker) && (type == null || like.getLikeType() == type));
    }

    public List<Review> getReviewsByProduct(UUID productId) {
        return db.get(DataMap.REVIEWS, (review) -> review.getProductId() == productId);
    }

    public List<Review> getReviewsByAuthor(UUID authorId) {
        return db.get(DataMap.REVIEWS, (review) -> review.getAuthorId() == authorId);
    }

    public OperationResult addReview(UUID productId, UUID authorId, String content, int rating) {
        if (getReview(productId, authorId) != null) {
            return new OperationResult(false, "You have already rated this product.");
        }

        db.add(DataMap.REVIEWS, new Review(authorId, productId, content, rating, LocalDate.now()));
        updateProductRating(productId);

        return new OperationResult(true, "Rating added.");
    }

    public Review getReview(UUID productId, UUID authorId) {
        List<Review> reviews = db.get(DataMap.REVIEWS, (r) -> r.getAuthorId() == authorId && r.getProductId() == productId);
        if (reviews.size() == 0) {
            return null;
        } else {
            return reviews.get(0);
        }
    }

    private void updateProductRating(UUID productId) {
        List<Review> reviews = db.get(DataMap.REVIEWS, (review) -> review.getProductId() == productId);
        int totalRating = reviews.stream().map(Review::getRating).reduce(0, Integer::sum);
        int ratingAverage = totalRating / reviews.size();
        db.<Product>update(DataMap.PRODUCTS, prod -> prod.setRating(ratingAverage), productId);
    }
}
