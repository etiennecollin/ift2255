/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

public record SellerMetrics(int recentRevenue, int totalRevenue, int numberRecentProductsSold,
                            int numberTotalProductsSold, int numberProductsOffered, int averageRecentProductRating,
                            int averageTotalProductRating) {}
