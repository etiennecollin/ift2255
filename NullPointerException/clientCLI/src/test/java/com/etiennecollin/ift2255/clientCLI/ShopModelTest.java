/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import com.etiennecollin.ift2255.clientCLI.models.ShopModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopModelTest {

    /**
     * Tests the costAfterFidelityPoints method of ShopModel
     */
    @Test
    public void testCostAfterFidelityPoints() {
        ShopModel shopModel = new ShopModel(new MockDatabase());

        assertEquals(new Tuple<>(0, 0), shopModel.costAfterFidelityPoints(0, 0), "Zero cost with zero fidelity points");
        assertEquals(new Tuple<>(100, 0), shopModel.costAfterFidelityPoints(100, 0), "Cost with no fidelity points");
        assertEquals(new Tuple<>(60, 0), shopModel.costAfterFidelityPoints(100, 20), "Fidelity points don't cover cost");
        assertEquals(new Tuple<>(0, 0), shopModel.costAfterFidelityPoints(100, 50), "Fidelity points exactly cover cost");
        assertEquals(new Tuple<>(0, 50), shopModel.costAfterFidelityPoints(100, 100), "Fidelity points more than cover cost");
    }
}
