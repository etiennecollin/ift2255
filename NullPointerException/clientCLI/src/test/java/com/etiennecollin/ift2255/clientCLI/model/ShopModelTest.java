/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.MockDatabase;
import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.models.ShopModel;
import com.etiennecollin.ift2255.clientCLI.models.data.products.BookOrManualGenre;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The ShopModelTest class contains unit tests for the ShopModel class, focusing on the createNewBookOrManual method.
 * It ensures the proper functionality of adding a new Book or Manual product to the shop.
 */
class ShopModelTest {
    /**
     * Tests the createNewBookOrManual method in the ShopModel class.
     */
    @Test
    void createNewBookOrManual() {
        ShopModel sm = new ShopModel(new MockDatabase());

        assertEquals(new OperationResult(true, "Product added."), sm.createNewBookOrManual(UUID.randomUUID(), 15, 3, "title", "description", 10, "IBSN", "Author", "editor", BookOrManualGenre.Comic, LocalDate.now(), 3, 2), "Product with valid input must be accepted");
        assertEquals(new OperationResult(true, "Product added."), sm.createNewBookOrManual(UUID.randomUUID(), 15, 3, "", "description", 10, "IBSN", "Author", "editor", BookOrManualGenre.Comic, LocalDate.now(), 3, 2), "Product without name must be accepted");
        assertEquals(new OperationResult(true, "Product added."), sm.createNewBookOrManual(UUID.randomUUID(), -15, 3, "title", "description", 10, "IBSN", "Author", "editor", BookOrManualGenre.Comic, LocalDate.now(), 3, 2), "Product with negative price must be accepted");
        assertEquals(new OperationResult(true, "Product added."), sm.createNewBookOrManual(UUID.randomUUID(), 15, 3, "title", "description", -10, "IBSN", "Author", "editor", BookOrManualGenre.Comic, LocalDate.now(), 3, 2), "Product with negative fidelity points must be accepted");
        assertEquals(new OperationResult(true, "Product added."), sm.createNewBookOrManual(UUID.randomUUID(), 15, -3, "title", "description", 10, "IBSN", "Author", "editor", BookOrManualGenre.Comic, LocalDate.now(), 3, 2), "Product with a negative amount must be accepted");
    }
}