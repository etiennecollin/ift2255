/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.models.data.*;
import com.etiennecollin.ift2255.clientCLI.models.data.products.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The {@code SocialModel} class represents the models for handling social interactions and user engagement in the CLI application.
 * It includes methods for toggling likes on products, reviews, sellers, and buyers, as well as methods for retrieving likes,
 * reviews, and managing user interactions.
 */
public class SocialModel {
    /**
     * The underlying database used by the models.
     */
    private final Database db;

    /**
     * Constructs a new {@code SocialModel} with the specified database.
     *
     * @param database The database used by the models to store social interaction-related data.
     */
    public SocialModel(Database database) {
        this.db = database;
    }

    /**
     * Toggles the like status of a product by the current user.
     *
     * @param productId The unique identifier of the product.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult toggleLikeProduct(UUID productId, UUID userId) {
        Product product = db.get(DataMap.PRODUCTS, productId);
        if (product != null) {
            if (isLiked(productId, userId)) {
                db.<Like>remove(DataMap.LIKES, (entry) -> entry.getLikedEntityId().equals(productId) && entry.getUserId().equals(userId));
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

    /**
     * Checks whether a user has liked a specific entity (product, review, seller, or buyer).
     *
     * @param likedEntity The unique identifier of the liked entity.
     * @param likedByUser The unique identifier of the user who liked the entity.
     *
     * @return {@code true} if the user has liked the entity, {@code false} otherwise.
     */
    public boolean isLiked(UUID likedEntity, UUID likedByUser) {
        return db.<Like>get(DataMap.LIKES, (entry) -> entry.getLikedEntityId().equals(likedEntity) && entry.getUserId().equals(likedByUser)).size() > 0;
    }

    /**
     * Toggles the like status of a review by the current user.
     *
     * @param reviewId The unique identifier of the review.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult toggleLikeReview(UUID reviewId, UUID userId) {
        Review review = db.get(DataMap.REVIEWS, reviewId);

        if (review != null) {
            if (review.getAuthorId().equals(userId)) {
                return new OperationResult(false, "You cannot like your own review.");
            }

            List<Like> likes = db.get(DataMap.LIKES, (like) -> like.getLikedEntityId().equals(reviewId));
            Optional<Like> likedByUser = likes.stream().filter((like) -> like.getUserId().equals(userId)).findFirst();

            if (likedByUser.isEmpty()) {
                if (likes.size() == 0 && !review.getIsReported()) {
                    db.<Buyer>update(DataMap.BUYERS, (buyer) -> buyer.setFidelityPoints(buyer.getFidelityPoints() + 10), review.getAuthorId());
                }
                db.add(DataMap.LIKES, new Like(reviewId, userId, LikeType.Review));
                return new OperationResult(true, "Like added.");
            } else {
                if (likes.size() == 1 && !review.getIsReported()) {
                    db.<Buyer>update(DataMap.BUYERS, (buyer) -> buyer.setFidelityPoints(buyer.getFidelityPoints() - 10), review.getAuthorId());
                }
                db.remove(DataMap.LIKES, likedByUser.get().getId());
                return new OperationResult(true, "Like removed.");
            }
        }

        return new OperationResult(false, "Seller no longer exists.");
    }

    public OperationResult markReviewAsInappropriate(UUID reviewId) {
        Review review = db.get(DataMap.REVIEWS, reviewId);

        if (review != null) {
            if (review.getIsReported()) {
                return new OperationResult(true, "Marked as inappropriate.");
            }

            List<Like> likes = db.get(DataMap.LIKES, (like) -> like.getLikedEntityId().equals(reviewId));
            db.<Review>update(DataMap.REVIEWS, r -> r.setIsReported(true), reviewId);

            if (likes.size() > 0) {
                db.<Buyer>update(DataMap.BUYERS, (buyer) -> buyer.setFidelityPoints(buyer.getFidelityPoints() - 10), review.getAuthorId());
            }

            return new OperationResult(true, "Marked as inappropriate.");
        }

        return new OperationResult(false, "Review could not be modified.");
    }

    /**
     * Toggles the like status of a seller by the current user.
     *
     * @param sellerId The unique identifier of the seller.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult toggleLikeSeller(UUID sellerId, UUID userId) {
        Seller seller = db.get(DataMap.SELLERS, sellerId);

        if (seller != null) {
            List<Like> likes = db.get(DataMap.LIKES, (like) -> like.getLikedEntityId().equals(sellerId));
            Optional<Like> likedByUser = likes.stream().filter((like) -> like.getUserId().equals(userId)).findFirst();

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

    /**
     * Toggles the follow status of a buyer by the current user.
     *
     * @param buyerId The unique identifier of the buyer.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult toggleFollowBuyer(UUID buyerId, UUID userId) {
        Buyer buyer = db.get(DataMap.BUYERS, buyerId);

        if (buyer != null) {
            List<Like> likes = db.get(DataMap.LIKES, (like) -> like.getLikedEntityId().equals(buyerId));
            Optional<Like> likedByUser = likes.stream().filter((like) -> like.getUserId().equals(userId)).findFirst();

            if (likedByUser.isEmpty()) {
                db.add(DataMap.LIKES, new Like(buyerId, userId, LikeType.Buyer));
                db.<Buyer>update(DataMap.BUYERS, (b) -> b.setFidelityPoints(buyer.getFidelityPoints() + 5), (b) -> b.getId().equals(userId) || b.getId().equals(buyerId));
                return new OperationResult(true, "Unfollowed. -5 fidelity points.");
            } else {
                db.remove(DataMap.LIKES, likedByUser.get().getId());
                db.<Buyer>update(DataMap.BUYERS, (b) -> b.setFidelityPoints(buyer.getFidelityPoints() - 5), (b) -> b.getId().equals(userId) || b.getId().equals(buyerId));
                return new OperationResult(true, "Following this user. +5 fidelity points.");
            }
        }

        return new OperationResult(false, "Seller no longer exists.");
    }

    /**
     * Retrieves a list of likes based on specified criteria.
     *
     * @param likee The unique identifier of the entity being liked (can be {@code null}).
     * @param liker The unique identifier of the user who liked the entity (can be {@code null}).
     * @param type  The type of entity being liked (can be {@code null}).
     *
     * @return A list of likes that match the specified criteria.
     */
    public List<Like> getLikes(UUID likee, UUID liker, LikeType type) {
        return db.get(DataMap.LIKES, (like) -> (likee == null || like.getLikedEntityId().equals(likee)) && (liker == null || like.getUserId().equals(liker)) && (type == null || like.getLikeType() == type));
    }

    /**
     * Retrieves a list of reviews associated with a specific product.
     *
     * @param productId The unique identifier of the product.
     *
     * @return A list of reviews associated with the specified product.
     */
    public List<Review> getReviewsByProduct(UUID productId) {
        return db.get(DataMap.REVIEWS, (review) -> review.getProductId().equals(productId));
    }

    /**
     * Retrieves a list of reviews written by a specific author.
     *
     * @param authorId The unique identifier of the review author.
     *
     * @return A list of reviews written by the specified author.
     */
    public List<Review> getReviewsByAuthor(UUID authorId) {
        return db.get(DataMap.REVIEWS, (review) -> review.getAuthorId().equals(authorId));
    }

    public Review getReview(UUID reviewId) {
        return db.get(DataMap.REVIEWS, reviewId);
    }

    /**
     * Adds a review for a specific product by the current user.
     *
     * @param productId The unique identifier of the product.
     * @param authorId  The unique identifier of the review author.
     * @param content   The content of the review.
     * @param rating    The rating given to the product in the review.
     *
     * @return An {@code OperationResult} indicating the success or failure of the operation.
     */
    public OperationResult addReview(UUID productId, UUID authorId, String content, int rating) {
        if (getReview(productId, authorId) != null) {
            return new OperationResult(false, "You have already rated this product.");
        }

        db.add(DataMap.REVIEWS, new Review(authorId, productId, content, rating, LocalDate.now()));
        updateProductRating(productId);

        return new OperationResult(true, "Rating added.");
    }

    /**
     * Retrieves a review written by a specific author for a specific product.
     *
     * @param productId The unique identifier of the product.
     * @param authorId  The unique identifier of the review author.
     *
     * @return The review written by the specified author for the specified product, or {@code null} if not found.
     */
    public Review getReview(UUID productId, UUID authorId) {
        List<Review> reviews = db.get(DataMap.REVIEWS, (r) -> r.getAuthorId().equals(authorId) && r.getProductId().equals(productId));
        if (reviews.size() == 0) {
            return null;
        } else {
            return reviews.get(0);
        }
    }

    /**
     * Updates the rating of a product based on the average rating of its associated reviews.
     *
     * @param productId The unique identifier of the product.
     */
    private void updateProductRating(UUID productId) {
        List<Review> reviews = db.get(DataMap.REVIEWS, (review) -> review.getProductId().equals(productId));
        int totalRating = reviews.stream().map(Review::getRating).reduce(0, Integer::sum);
        int ratingAverage = totalRating / reviews.size();
        db.<Product>update(DataMap.PRODUCTS, prod -> prod.setRating(ratingAverage), productId);
    }
}
