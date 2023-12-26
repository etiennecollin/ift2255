/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.model.data.JavaSerializedDatabase;
import com.etiennecollin.ift2255.clientCLI.model.data.products.BookOrManualGenre;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ShopModelTest {

    @Test
    void createNewBookOrManual() {
        JavaSerializedDatabase db = new JavaSerializedDatabase();
        ShopModel sm = new ShopModel(db);

        assertEquals(new OperationResult(true, "Product added."), sm.createNewBookOrManual(UUID.randomUUID(), 15, 3, "title", "description", 10, "IBSN", "Author", "editor", BookOrManualGenre.Comic, LocalDate.now(), 3, 2), "Product with valid input must be accepted");
        assertEquals(new OperationResult(true, "Product added."), sm.createNewBookOrManual(UUID.randomUUID(), 15, 3, "", "description", 10, "IBSN", "Author", "editor", BookOrManualGenre.Comic, LocalDate.now(), 3, 2), "Product without name must be accepted");
        assertEquals(new OperationResult(true, "Product added."), sm.createNewBookOrManual(UUID.randomUUID(), -15, 3, "title", "description", 10, "IBSN", "Author", "editor", BookOrManualGenre.Comic, LocalDate.now(), 3, 2), "Product with negative price must be accepted");
        assertEquals(new OperationResult(true, "Product added."), sm.createNewBookOrManual(UUID.randomUUID(), 15, 3, "title", "description", -10, "IBSN", "Author", "editor", BookOrManualGenre.Comic, LocalDate.now(), 3, 2), "Product with negative fidelity points must be accepted");
        assertEquals(new OperationResult(true, "Product added."), sm.createNewBookOrManual(UUID.randomUUID(), 15, -3, "title", "description", 10, "IBSN", "Author", "editor", BookOrManualGenre.Comic, LocalDate.now(), 3, 2), "Product with a negative amount must be accepted");
    }

}