/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.models.data.products;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a book or manual product with specific attributes such as ISBN, author, editor, release date, edition number, and volume number.
 * <p>
 * This class extends the general Product class and is categorized under the "BookOrManual" product category.
 */
public class BookOrManual extends Product {
    /**
     * The ISBN (International Standard Book Number) of the book or manual.
     */
    private String isbn;
    /**
     * The author of the book or manual.
     */
    private String author;
    /**
     * The editor of the book or manual.
     */
    private String editor;
    /**
     * The release date of the book or manual.
     */
    private LocalDate releaseDate;
    /**
     * The edition number of the book or manual.
     */
    private int editionNumber;
    /**
     * The volume number of the book or manual.
     */
    private int volumeNumber;

    /**
     * Constructs a BookOrManual with specified attributes.
     *
     * @param price          The price of the book or manual.
     * @param quantity       The quantity available for sale.
     * @param title          The title of the book or manual.
     * @param description    The description of the book or manual.
     * @param sellerId       The UUID of the seller offering the product.
     * @param fidelityPoints The fidelity points associated with the purchase of the product.
     * @param isbn           The ISBN of the book or manual.
     * @param author         The author of the book or manual.
     * @param editor         The editor of the book or manual.
     * @param genre          The genre of the book or manual.
     * @param releaseDate    The release date of the book or manual.
     * @param editionNumber  The edition number of the book or manual.
     * @param volumeNumber   The volume number of the book or manual.
     *
     * @throws IllegalArgumentException If the edition number or volume number is negative.
     */
    public BookOrManual(int price, int quantity, String title, String description, UUID sellerId, int fidelityPoints, String isbn, String author, String editor, BookOrManualGenre genre, LocalDate releaseDate, int editionNumber, int volumeNumber) throws IllegalArgumentException {
        super(price, quantity, title, description, ProductCategory.BookOrManual, genre, sellerId, fidelityPoints);
        this.isbn = isbn;
        this.author = author;
        this.editor = editor;
        this.releaseDate = releaseDate;
        this.editionNumber = editionNumber;
        this.volumeNumber = volumeNumber;
    }

    /**
     * Gets the ISBN (International Standard Book Number) of the book or manual.
     *
     * @return The ISBN of the book or manual.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN (International Standard Book Number) of the book or manual.
     *
     * @param isbn The ISBN to set for the book or manual.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the author of the book or manual.
     *
     * @return The author of the book or manual.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book or manual.
     *
     * @param author The author to set for the book or manual.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the editor of the book or manual.
     *
     * @return The editor of the book or manual.
     */
    public String getEditor() {
        return editor;
    }

    /**
     * Sets the editor of the book or manual.
     *
     * @param editor The editor to set for the book or manual.
     */
    public void setEditor(String editor) {
        this.editor = editor;
    }

    /**
     * Gets the release date of the book or manual.
     *
     * @return The release date of the book or manual.
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the book or manual.
     *
     * @param releaseDate The release date to set for the book or manual.
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets the edition number of the book or manual.
     *
     * @return The edition number of the book or manual.
     */
    public int getEditionNumber() {
        return editionNumber;
    }

    /**
     * Sets the edition number of the book or manual.
     *
     * @param editionNumber The edition number to set for the book or manual.
     *
     * @throws IllegalArgumentException If the edition number is negative.
     */
    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    /**
     * Gets the volume number of the book or manual.
     *
     * @return The volume number of the book or manual.
     */
    public int getVolumeNumber() {
        return volumeNumber;
    }

    /**
     * Sets the volume number of the book or manual.
     *
     * @param volumeNumber The volume number to set for the book or manual.
     *
     * @throws IllegalArgumentException If the volume number is negative.
     */
    public void setVolumeNumber(int volumeNumber) {
        this.volumeNumber = volumeNumber;
    }
}
