/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes.products;

import java.io.Serializable;
import java.util.ArrayList;

public enum ProductCategory implements Serializable {
    BookOrManual("Book or Manual", BookOrManualGenre.class), LearningResource("Learning Resource", LearningResourceType.class), StationeryArticle("Stationery Article", StationeryArticleCategory.class), IT("IT", ITCategory.class), OfficeEquipment("Office Equipment", OfficeEquipmentCategory.class);
    private final Class<? extends Enum<?>> enumVar;
    private final String name;

    ProductCategory(String name, Class<? extends Enum<?>> enumVar) {
        this.name = name;
        this.enumVar = enumVar;
    }

    public static ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        for (ProductCategory option : ProductCategory.values()) {
            options.add(option.toString());
        }
        return options;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public ArrayList<String> getSubOptions() {
        ArrayList<String> options = new ArrayList<>();
        for (Enum<?> option : enumVar.getEnumConstants()) {
            options.add(option.toString());
        }
        return options;
    }

    public Class<? extends Enum<?>> getEnum() {
        return enumVar;
    }
}
