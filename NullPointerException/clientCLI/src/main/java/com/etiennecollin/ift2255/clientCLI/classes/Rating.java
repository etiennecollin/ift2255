/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import java.util.NoSuchElementException;

public class Rating {
    /**
     * Rating encoded as a percentage with no decimal places.
     */
    private int rating;
    /**
     * The number of reviews that generated the rating.
     */
    private int ratingNumber;

    public Rating() {
        this.rating = 0;
        this.ratingNumber = 0;
    }

    public int getRating() throws NoSuchElementException {
        if (ratingNumber == 0) {
            throw new NoSuchElementException("There are no ratings available yet.");
        }
        return rating;
    }

    public void addRating(int newRating) throws IllegalArgumentException {
        if (newRating < 0 || newRating > 100) {
            throw new IllegalArgumentException("Rating must be between 0 and 100 inclusively");
        }
        this.rating = (rating * ratingNumber + newRating) / (ratingNumber + 1);
        ratingNumber++;
    }

    public void removeRating(int removedRating) throws IllegalArgumentException {
        switch (ratingNumber) {
            case 0:
                throw new IllegalArgumentException("There are no ratings to remove");
            case 1:
                this.rating = 0;
            default:
                this.rating = (rating * ratingNumber - removedRating) / (ratingNumber - 1);
        }
        ratingNumber--;
    }
}
