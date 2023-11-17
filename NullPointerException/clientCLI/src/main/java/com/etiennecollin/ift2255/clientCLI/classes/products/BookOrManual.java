/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes.products;

import java.time.LocalDate;

public class BookOrManual extends Product {
    private int isbn;
    private String author;
    private String editor;
    private LocalDate releaseDate;
    private int editionNumber;
    private int volumeNumber;

    public BookOrManual(int price, int quantity, String title, String description, int fidelityPoints, int isbn, String author, String editor, BookOrManualGenre genre, LocalDate releaseDate, int editionNumber, int volumeNumber) {
        super(price, quantity, title, description, ProductCategory.BookOrManual, genre, fidelityPoints);
        this.isbn = isbn;
        this.author = author;
        this.editor = editor;
        this.releaseDate = releaseDate;
        this.editionNumber = editionNumber;
        this.volumeNumber = volumeNumber;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    public int getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(int volumeNumber) {
        this.volumeNumber = volumeNumber;
    }
}
