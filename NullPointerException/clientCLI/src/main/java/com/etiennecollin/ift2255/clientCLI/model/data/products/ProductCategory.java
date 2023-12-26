/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data.products;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the category of a product. Each category may have sub-categories
 * represented by different enums.
 */
public enum ProductCategory implements Serializable {
    /**
     * Represents the category of a book or manual.
     */
    BookOrManual("Book or Manual", BookOrManualGenre.class),
    /**
     * Represents the category of a learning resource.
     */
    LearningResource("Learning Resource", LearningResourceType.class),
    /**
     * Represents the category of a stationery article.
     */
    StationeryArticle("Stationery Article", StationeryArticleCategory.class),
    /**
     * Represents the category of an IT product.
     */
    IT("IT", ITCategory.class),
    /**
     * Represents the category of office equipment.
     */
    OfficeEquipment("Office Equipment", OfficeEquipmentCategory.class);
    /**
     * The name of the product category.
     */
    private final Class<? extends Enum<?>> enumVar;
    /**
     * The class representing the enum for sub-categories.
     */
    private final String name;

    /**
     * Constructs a ProductCategory with the specified name and enum class.
     *
     * @param name    The name of the product category.
     * @param enumVar The class representing the enum for sub-categories.
     */
    ProductCategory(String name, Class<? extends Enum<?>> enumVar) {
        this.name = name;
        this.enumVar = enumVar;
    }

    /**
     * Gets the available options for product categories.
     *
     * @return An ArrayList of strings representing available options.
     */
    public static ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        for (ProductCategory option : ProductCategory.values()) {
            options.add(option.toString());
        }
        return options;
    }

    /**
     * Gets the name of the product category.
     *
     * @return The name of the product category.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Gets the available sub-options for the product category.
     *
     * @return An ArrayList of strings representing available sub-options.
     */
    public ArrayList<String> getSubOptions() {
        ArrayList<String> options = new ArrayList<>();
        for (Enum<?> option : enumVar.getEnumConstants()) {
            options.add(option.toString());
        }
        return options;
    }

    /**
     * Gets the class representing the enum for sub-categories.
     *
     * @return The class representing the enum for sub-categories.
     */
    public Class<? extends Enum<?>> getEnum() {
        return enumVar;
    }
}
