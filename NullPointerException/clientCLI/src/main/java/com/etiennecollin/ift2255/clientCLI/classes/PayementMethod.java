/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

public class PayementMethod {
    private int moneyUsed;
    private int fidelityPointsUsed;

    public PayementMethod(int moneyUsed, int fidelityPointsUsed) {
        this.moneyUsed = moneyUsed;
        this.fidelityPointsUsed = fidelityPointsUsed;
    }

    public int getMoneyUsed() {
        return moneyUsed;
    }

    public void setMoneyUsed(int moneyUsed) {
        this.moneyUsed = moneyUsed;
    }

    public int getFidelityPointsUsed() {
        return fidelityPointsUsed;
    }

    public void setFidelityPointsUsed(int fidelityPointsUsed) {
        this.fidelityPointsUsed = fidelityPointsUsed;
    }
}