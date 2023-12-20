/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model.data;

import com.etiennecollin.ift2255.clientCLI.classes.*;
import com.etiennecollin.ift2255.clientCLI.model.data.products.Product;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.UUID;

public class Order extends DatabaseObject {
    private final LocalDate orderDate;
    private final UUID buyerId;
    private final UUID sellerId;
    private int totalCost;
    private int numberOfProducts;
    private int fidelityPointsEarned;
    private String email;
    private String phone;
    private String address;
    private String billingAddress;
    private String creditCardName;
    private String creditCardNumber;
    private YearMonth creditCardExp;
    private String creditCardSecretDigits;
    private OrderState state;
    private Shipment shipment;
    private PayementMethod payementMethod;
    private ArrayList<Product> products;

    public Order(int totalCost, int fidelityPointsEarned, PayementMethod payementMethod, String email, String phone, String address, String billingAddress, String creditCardName, String creditCardNumber, YearMonth creditCardExp, String creditCardSecretDigits, UUID buyerId, UUID sellerId) {
        this.totalCost = totalCost;
        this.fidelityPointsEarned = fidelityPointsEarned;
        this.payementMethod = payementMethod;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.billingAddress = billingAddress;
        this.creditCardName = creditCardName;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExp = creditCardExp;
        this.creditCardSecretDigits = creditCardSecretDigits;
        this.buyerId = buyerId;
        this.sellerId = sellerId;

        this.state = OrderState.InProduction;
        this.orderDate = LocalDate.now();
    }
}
