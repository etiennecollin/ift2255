/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import java.io.Serializable;

public record BuyerMetrics(int numberRecentOrders, int numberTotalOrders, int numberRecentProductsBought,
                           int numberTotalProductsBought, int numberFollowers, int averageRecentReviews,
                           int averageTotalReviews, int numberRecentReviews,
                           int numberTotalReviews) implements Serializable {}