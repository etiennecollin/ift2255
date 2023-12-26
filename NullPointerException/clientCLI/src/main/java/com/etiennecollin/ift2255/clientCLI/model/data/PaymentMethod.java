/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import java.io.Serializable;

/**
 * Represents a payment method in the system, implementing Serializable.
 */
public class PaymentMethod implements Serializable {
    /**
     * The amount of money used in the payment method.
     */
    private int moneyUsed;
    /**
     * The amount of fidelity points used in the payment method.
     */
    private int fidelityPointsUsed;
    /**
     * The amount of return money used in the payment method.
     */
    private int returnMoneyUsed;

    /**
     * Constructs a PaymentMethod object with the given parameters.
     *
     * @param moneyUsed          The amount of money used in the payment method.
     * @param fidelityPointsUsed The amount of fidelity points used in the payment method.
     * @param returnMoneyUsed    The amount of return money used in the payment method.
     */
    public PaymentMethod(int moneyUsed, int fidelityPointsUsed, int returnMoneyUsed) {
        this.moneyUsed = moneyUsed;
        this.fidelityPointsUsed = fidelityPointsUsed;
        this.returnMoneyUsed = returnMoneyUsed;
    }

    /**
     * Gets the amount of return money used in the payment method.
     *
     * @return The amount of return money used.
     */
    public int getReturnMoneyUsed() {
        return returnMoneyUsed;
    }

    /**
     * Sets the amount of return money used in the payment method.
     *
     * @param returnMoneyUsed The new amount of return money used to set.
     */
    public void setReturnMoneyUsed(int returnMoneyUsed) {
        this.returnMoneyUsed = returnMoneyUsed;
    }

    /**
     * Gets the amount of money used in the payment method.
     *
     * @return The amount of money used.
     */
    public int getMoneyUsed() {
        return moneyUsed;
    }

    /**
     * Sets the amount of money used in the payment method.
     *
     * @param moneyUsed The new amount of money used to set.
     */
    public void setMoneyUsed(int moneyUsed) {
        this.moneyUsed = moneyUsed;
    }

    /**
     * Gets the amount of fidelity points used in the payment method.
     *
     * @return The amount of fidelity points used.
     */
    public int getFidelityPointsUsed() {
        return fidelityPointsUsed;
    }

    /**
     * Sets the amount of fidelity points used in the payment method.
     *
     * @param fidelityPointsUsed The new amount of fidelity points used to set.
     */
    public void setFidelityPointsUsed(int fidelityPointsUsed) {
        this.fidelityPointsUsed = fidelityPointsUsed;
    }
}
