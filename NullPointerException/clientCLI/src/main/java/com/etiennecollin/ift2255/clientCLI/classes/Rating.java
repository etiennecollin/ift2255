/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import java.util.NoSuchElementException;

public class Rating {
    private int rating; // Encoded as a percentage with no decimal places
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

    public void addRating(int newRating) {
        if (ratingNumber == 0) {
            this.rating = newRating;
        } else {
            this.rating = (rating * ratingNumber + newRating) / (ratingNumber + 1);
        }
        ratingNumber++;
    }
}
