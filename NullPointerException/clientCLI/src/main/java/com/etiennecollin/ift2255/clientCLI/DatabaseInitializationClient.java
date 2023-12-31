/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import com.etiennecollin.ift2255.clientCLI.models.*;
import com.etiennecollin.ift2255.clientCLI.models.data.*;
import com.etiennecollin.ift2255.clientCLI.models.data.products.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The `DatabaseInitializationClient` class serves as the main entry point for pre-populating the database.
 */
public class DatabaseInitializationClient {

    /**
     * The main method that initializes the UniShop instance and renders views.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        System.out.println("Initializing database");
        UniShop uniShop = UniShop.getInstance();
        AuthenticationModel auth = uniShop.getAuth();
        ProfileModel profile = uniShop.getProfile();
        ShopModel shop = uniShop.getShop();
        SocialModel social = uniShop.getSocial();
        TicketingModel ticketing = uniShop.getTicketing();

        // Seller 1
        String seller1Name = "revendeur1";
        auth.registerNewSeller(seller1Name, "password1", "revendeur1@fakemail.com", "514-555-1111", "123 Rue des revendeurs, Montréal, CA");
        UUID seller1Id = profile.getSeller(seller1Name).getId();

        shop.createNewBookOrManual(seller1Id, 1999, 30, "Actionman", "A thrilling super hero comic.", 50, "1234567890", "Bob Authorson", "Super Comics", BookOrManualGenre.Comic, LocalDate.of(2020, 3, 5), 1, 0);
        UUID seller1Product1 = shop.getProducts(ProductCategory.BookOrManual, BookOrManualGenre.Comic, seller1Id).get(0).getId();
        shop.startProductPromotion(seller1Product1, 5, 0, LocalDate.of(2024, 1, 26));
        shop.createNewBookOrManual(seller1Id, 2500, 8, "The real story of a person", "The incredible autobiography of this person", 150, "1122334455", "Pen Name", "Publishing Press", BookOrManualGenre.Documentary, LocalDate.of(1985, 7, 20), 2, 0);
        UUID seller1Product2 = shop.getProducts(ProductCategory.BookOrManual, BookOrManualGenre.Documentary, seller1Id).get(0).getId();
        shop.createNewBookOrManual(seller1Id, 1299, 25, "Space Attack", "1999's best sci-fi thriller.", 30, "1223334444", "Stacy Fakename", "Publishing Press", BookOrManualGenre.Novel, LocalDate.of(1999, 8, 19), 1, 0);
        UUID seller1Product3 = shop.getProducts(ProductCategory.BookOrManual, BookOrManualGenre.Novel, seller1Id).get(0).getId();
        shop.createNewBookOrManual(seller1Id, 15000, 180, "Polymorphism: First Steps", "A thrilling super hero comic.", 500, "1011001011", "Alice Bob", "University Press", BookOrManualGenre.Textbook, LocalDate.of(2022, 9, 1), 5, 1);
        UUID seller1Product4 = shop.getProducts(ProductCategory.BookOrManual, BookOrManualGenre.Textbook, seller1Id).get(0).getId();
        shop.createNewBookOrManual(seller1Id, 1800, 46, "University Recipes", "More than 100 easy meals.", 0, "9988765432", "Anonymous", "University Press", BookOrManualGenre.Other, LocalDate.of(2023, 1, 8), 0, 0);
        UUID seller1Product5 = shop.getProducts(ProductCategory.BookOrManual, BookOrManualGenre.Other, seller1Id).get(0).getId();

        // Seller 2
        String seller2Name = "revendeur2";
        auth.registerNewSeller(seller2Name, "password2", "revendeur2@fakemail.com", "514-555-2222", "1 Rue industrielle, Montréal, CA");
        UUID seller2Id = profile.getSeller(seller2Name).getId();

        shop.createNewOfficeEquipment(seller2Id, 8499, 5, "Steel Chair", "The most comfortable cushion-less chair.", 100, "Idea", "SC1", OfficeEquipmentCategory.Chair);
        UUID seller2Product1 = shop.getProducts(ProductCategory.OfficeEquipment, OfficeEquipmentCategory.Chair, seller2Id).get(0).getId();
        shop.createNewOfficeEquipment(seller2Id, 1999, 19, "Articulated desk lamp", "The most perfect reading lamp.", 0, "Idea", "ADL", OfficeEquipmentCategory.Lamp);
        UUID seller2Product2 = shop.getProducts(ProductCategory.OfficeEquipment, OfficeEquipmentCategory.Lamp, seller2Id).get(0).getId();
        shop.createNewOfficeEquipment(seller2Id, 6005, 2, "5'x28\" Desk", "It's a desk.", 50, "Idea", "D6028", OfficeEquipmentCategory.Table);
        UUID seller2Product3 = shop.getProducts(ProductCategory.OfficeEquipment, OfficeEquipmentCategory.Table, seller2Id).get(0).getId();
        shop.createNewOfficeEquipment(seller2Id, 3499, 24, "Whiteboard and marker set", "Black, red, and green markers included.", 5, "Idea", "WB", OfficeEquipmentCategory.Other);
        UUID seller2Product4 = shop.getProducts(ProductCategory.OfficeEquipment, OfficeEquipmentCategory.Other, seller2Id).get(0).getId();

        // Seller 3
        String seller3Name = "revendeur3";
        auth.registerNewSeller(seller3Name, "password3", "revendeur3@fakemail.com", "514-555-3333", "5 Rue Saint Denis, Montréal, CA");
        UUID seller3Id = profile.getSeller(seller3Name).getId();

        shop.createNewITProduct(seller3Id, 151099, 10, "Lapbook Pro", "The latest in computing.", 0, "The Lapbook Company", "Lapbook Pro 2023", LocalDate.of(2023, 4, 17), ITCategory.Computer);
        UUID seller3Product1 = shop.getProducts(ProductCategory.IT, ITCategory.Computer, seller3Id).get(0).getId();
        shop.createNewITProduct(seller3Id, 8599, 14, "Oceangate 4TB SDD", "High-quality data backup.", 120, "Oceangate", "SDD4", LocalDate.of(2022, 5, 10), ITCategory.ExternalHardDrive);
        UUID seller3Product2 = shop.getProducts(ProductCategory.IT, ITCategory.ExternalHardDrive, seller3Id).get(0).getId();
        shop.createNewITProduct(seller3Id, 2000, 2, "Comfort Mouse", "A simple USB mouse.", 10, "Ergo Company", "CM1000", LocalDate.of(2014, 10, 1), ITCategory.Mouse);
        UUID seller3Product3 = shop.getProducts(ProductCategory.IT, ITCategory.Mouse, seller3Id).get(0).getId();
        shop.createNewITProduct(seller3Id, 5685, 1, "Ergonomic Dvorak Keyboard", "Now with RGB backlighting.", 200, "Ergo Company", "KD", LocalDate.of(2021, 2, 1), ITCategory.Keyboard);
        UUID seller3Product4 = shop.getProducts(ProductCategory.IT, ITCategory.Keyboard, seller3Id).get(0).getId();
        shop.startProductPromotion(seller3Product4, 0, 300, LocalDate.of(2024, 4, 26));
        shop.createNewITProduct(seller3Id, 6000, 1, "USB Floppy disk reader", "Pretend it's the past, in the present.", 200, "Legacy Co", "R1592250", LocalDate.of(2018, 6, 3), ITCategory.Other);
        UUID seller3Product5 = shop.getProducts(ProductCategory.IT, ITCategory.Other, seller3Id).get(0).getId();

        // Seller 4
        String seller4Name = "revendeur4";
        auth.registerNewSeller(seller4Name, "password4", "revendeur4@fakemail.com", "514-555-4444", "20 Boulevard des Commerces, Montréal, CA");
        UUID seller4Id = profile.getSeller(seller4Name).getId();

        shop.createNewStationeryArticle(seller4Id, 800, 500, "Red 3-ring binder", "As bulky as you'd expect.", 40, "Stationary Stationery", "3-ring binder - red", StationeryArticleCategory.Notebook);
        UUID seller4Product1 = shop.getProducts(ProductCategory.StationeryArticle, StationeryArticleCategory.Notebook, seller4Id).get(0).getId();
        shop.createNewStationeryArticle(seller4Id, 699, 100, "No 2 pencil, 10-pack", "Valid for all scantron tests", 25, "Stationary Stationery", "P2-10", StationeryArticleCategory.Pencil);
        UUID seller4Product2 = shop.getProducts(ProductCategory.StationeryArticle, StationeryArticleCategory.Pencil, seller4Id).get(0).getId();
        shop.createNewStationeryArticle(seller4Id, 149, 80, "Yellow highlight", "1-pack", 0, "Paper & Things", "highlight-y", StationeryArticleCategory.Highlighter);
        UUID seller4Product3 = shop.getProducts(ProductCategory.StationeryArticle, StationeryArticleCategory.Highlighter, seller4Id).get(0).getId();
        shop.createNewStationeryArticle(seller4Id, 79, 6, "Pencil sharpener", "The small metal kind.", 0, "Stationary Stationery", "shp-sm", StationeryArticleCategory.Other);
        UUID seller4Product4 = shop.getProducts(ProductCategory.StationeryArticle, StationeryArticleCategory.Other, seller4Id).get(0).getId();

        // Seller 5
        String seller5Name = "revendeur5";
        auth.registerNewSeller(seller5Name, "password5", "revendeur5@fakemail.com", "514-555-5555", "15 Rue Saint Laurent, Montréal, CA");
        UUID seller5Id = profile.getSeller(seller5Name).getId();

        shop.createNewLearningResource(seller5Id, 9999, 1000, "German Language 101", "Learn German online!", 300, "Language Soft Co", "ger1e", LocalDate.of(2023, 1, 1), LearningResourceType.Electronic, 1);
        UUID seller5Product1 = shop.getProducts(ProductCategory.LearningResource, LearningResourceType.Electronic, seller5Id).get(0).getId();
        shop.createNewLearningResource(seller5Id, 3599, 20, "French-English Dictionary", "Pocket-sized and convenient", 120, "Little Rob", "lr-dict-fe", LocalDate.of(2023, 1, 1), LearningResourceType.Printed, 1);
        UUID seller5Product2 = shop.getProducts(ProductCategory.LearningResource, LearningResourceType.Printed, seller5Id).get(0).getId();


        // Buyer 1
        String buyer1Name = "acheteur1";
        auth.registerNewBuyer(buyer1Name, "password1", "Alice", "Anderson", "aa@fakemail.com", "514-555-6666", "1 Rue Principale");
        Buyer buyer1 = profile.getBuyer(buyer1Name);

        // Buyer 2
        String buyer2Name = "acheteur2";
        auth.registerNewBuyer(buyer2Name, "password2", ".", ".", ".", "514-555-7777", "2 Rue Principale");
        Buyer buyer2 = profile.getBuyer(buyer2Name);

        // Buyer 3
        String buyer3Name = "acheteur3";
        auth.registerNewBuyer(buyer3Name, "password3", ".", ".", ".", "514-555-8888", "3 Rue Principale");
        Buyer buyer3 = profile.getBuyer(buyer3Name);

        // Buyer 4
        String buyer4Name = "acheteur4";
        auth.registerNewBuyer(buyer4Name, "password4", ".", ".", ".", "514-555-9999", "4 Rue Principale");
        Buyer buyer4 = profile.getBuyer(buyer4Name);

        // Buyer 5
        String buyer5Name = "acheteur5";
        auth.registerNewBuyer(buyer5Name, "password5", ".", ".", ".", "514-555-0000", "5 Rue Principale");
        Buyer buyer5 = profile.getBuyer(buyer5Name);

        // Buyer 6
        String buyer6Name = "acheteur6";
        auth.registerNewBuyer(buyer6Name, "password6", ".", ".", ".", "514-555-0111", "6 Rue Principale");
        Buyer buyer6 = profile.getBuyer(buyer6Name);

        // Buyer 7
        String buyer7Name = "acheteur7";
        auth.registerNewBuyer(buyer7Name, "password7", ".", ".", ".", "514-555-0222", "7 Rue Principale");
        Buyer buyer7 = profile.getBuyer(buyer7Name);

        // Buyer 8
        String buyer8Name = "acheteur8";
        auth.registerNewBuyer(buyer8Name, "password8", ".", ".", ".", "514-555-0333", "8 Rue Principale");
        Buyer buyer8 = profile.getBuyer(buyer8Name);

        // Buyer 9
        String buyer9Name = "acheteur9";
        auth.registerNewBuyer(buyer9Name, "password9", ".", ".", ".", "514-555-0444", "9 Rue Principale");
        Buyer buyer9 = profile.getBuyer(buyer9Name);

        // Buyer 10
        String buyer10Name = "acheteur10";
        auth.registerNewBuyer(buyer10Name, "password10", ".", ".", ".", "514-555-0555", "10 Rue Principale");
        Buyer buyerX = profile.getBuyer(buyer10Name);

        // Orders 1-5
        shop.addToCart(buyer1.getId(), seller1Product1, 1, null);
        shop.addToCart(buyer1.getId(), seller1Product5, 1, null);
        shop.addToCart(buyer1.getId(), seller2Product2, 1, null);
        shop.addToCart(buyer1.getId(), seller3Product2, 2, null);
        shop.addToCart(buyer1.getId(), seller4Product1, 1, null);
        shop.addToCart(buyer1.getId(), seller5Product2, 1, null);
        String order1CVC = "111";
        Seller test = profile.getSeller(seller4Id);
        Product test2 = shop.getProduct(Product.class, seller4Product1);
        shop.createOrders(buyer1.getId(), buyer1.getEmail(), buyer1.getPhoneNumber(), buyer1.getAddress(), buyer1.getAddress(), buyer1.getFirstName() + " " + buyer1.getLastName(), "4111 1111 1111 1111", YearMonth.of(2025, 1), order1CVC, 0);
        List<Order> orders = shop.getOrders(buyer1.getId(), null);
        Order order11 = shop.getOrders(o -> o.getSellerId().equals(seller1Id) && o.getCreditCardSecretDigits().equalsIgnoreCase(order1CVC)).get(0);
        Order order12 = shop.getOrders(o -> o.getSellerId().equals(seller2Id) && o.getCreditCardSecretDigits().equalsIgnoreCase(order1CVC)).get(0);
        Order order13 = shop.getOrders(o -> o.getSellerId().equals(seller3Id) && o.getCreditCardSecretDigits().equalsIgnoreCase(order1CVC)).get(0);
        Order order14 = shop.getOrders(o -> o.getSellerId().equals(seller4Id) && o.getCreditCardSecretDigits().equalsIgnoreCase(order1CVC)).get(0);
        Order order15 = shop.getOrders(o -> o.getSellerId().equals(seller5Id) && o.getCreditCardSecretDigits().equalsIgnoreCase(order1CVC)).get(0);

        // order11 Delivered
        shop.shipOrder(order11.getId(), "Shipping Ltd.", "S09810938230", LocalDate.of(2024, 1, 20));
        shop.confirmDelivery(order11.getId());
        // order12 Cancelled
        shop.cancelOrder(order12.getId());
        // order13 Delivered and has a ticket open
        shop.shipOrder(order13.getId(), "Provincial Express", "L366J6623HF5", LocalDate.of(2024, 1, 4));
        shop.confirmDelivery(order13.getId());
        // order14 left In Transit
        shop.shipOrder(order14.getId(), "Provincial Express", "L366J662A53B", LocalDate.of(2024, 1, 6));
        // order15 left In Production

        // order13 ticket. Creates a replacement order in the PendingTicket state.
        ticketing.createManualTicket(order13.getId(), order13.getProducts(), "Not my order.", TicketCause.WrongProductReceived);
        Ticket ticket1 = ticketing.getTickets(t -> t.getOrderId().equals(order13.getId())).get(0);
        ticketing.changeTicketToReplacement(ticket1.getId(), "Oops. We'll replace those items if you ship the package you received to us.", true, "L37JH7433P63", "Provincial Express");


        shop.addToCart(buyer2.getId(), seller1Product3, 1, null);
        shop.addToCart(buyer2.getId(), seller2Product1, 1, null);
        shop.addToCart(buyer2.getId(), seller2Product2, 1, null);
        shop.addToCart(buyer2.getId(), seller2Product4, 1, null);
        shop.addToCart(buyer2.getId(), seller3Product3, 2, null);
        shop.addToCart(buyer2.getId(), seller4Product2, 1, null);
        shop.addToCart(buyer2.getId(), seller5Product1, 1, null);
        String order2CVC = "222";
        shop.createOrders(buyer2.getId(), buyer2.getEmail(), buyer2.getPhoneNumber(), buyer2.getAddress(), buyer2.getAddress(), buyer2.getFirstName() + " " + buyer2.getLastName(), "4111 1111 2222 1111", YearMonth.of(2024, 8), order2CVC, 0);
        Order order21 = shop.getOrders(o -> o.getSellerId().equals(seller1Id) && o.getCreditCardSecretDigits().equalsIgnoreCase(order2CVC)).get(0);
        Order order22 = shop.getOrders(o -> o.getSellerId().equals(seller2Id) && o.getCreditCardSecretDigits().equalsIgnoreCase(order2CVC)).get(0);
        Order order23 = shop.getOrders(o -> o.getSellerId().equals(seller3Id) && o.getCreditCardSecretDigits().equalsIgnoreCase(order2CVC)).get(0);
        Order order24 = shop.getOrders(o -> o.getSellerId().equals(seller4Id) && o.getCreditCardSecretDigits().equalsIgnoreCase(order2CVC)).get(0);
        Order order25 = shop.getOrders(o -> o.getSellerId().equals(seller5Id) && o.getCreditCardSecretDigits().equalsIgnoreCase(order2CVC)).get(0);

        // order21 Delivered order returned, ticket closed
        shop.shipOrder(order21.getId(), "Shipping Ltd.", "S062060928602", LocalDate.of(2024, 1, 1));
        shop.confirmDelivery(order21.getId());
        // order22 Delivered order returned, replacement in transit
        shop.shipOrder(order22.getId(), "Shipping Ltd.", "S092750917010", LocalDate.of(2024, 1, 3));
        shop.confirmDelivery(order22.getId());
        // order23 Delivered
        shop.shipOrder(order23.getId(), "Shipping Ltd.", "S005019051003", LocalDate.of(2024, 1, 3));
        shop.confirmDelivery(order23.getId());
        // order24 In Transit
        shop.shipOrder(order24.getId(), "Shipping Ltd.", "S059107691731", LocalDate.of(2024, 1, 4));
        // order25 Delivered
        shop.shipOrder(order25.getId(), "Shipping Ltd.", "S057813797191", LocalDate.of(2024, 1, 2));
        shop.confirmDelivery(order25.getId());


        ticketing.createAutoTicket(order21.getId(), order21.getProducts(), TicketCause.DefectiveProduct, null);
        Ticket ticket2 = ticketing.getTickets(t -> t.getOrderId().equals(order21.getId())).get(0);
        ticketing.confirmReceptionOfReturn(ticket2.getId());

        Ticket ticket3 = new Ticket("", order22.getId(), order22.getProducts(), TicketCause.DefectiveProduct, TicketState.OpenAuto, order22.getBuyerId(), order22.getSellerId());
        Session.createSession(buyer2.getId(), UserType.Buyer);
        Session.getInstance().setInExchangeProcess(true);
        List<CartProduct> cartProductList3 = new ArrayList<>();
        cartProductList3.add(new CartProduct(buyer2.getId(), seller2Product2, 1));
        cartProductList3.add(new CartProduct(buyer2.getId(), seller1Product3, 1));
        ticketing.activateExchangeTicket(ticket3, order22, cartProductList3);
        ticketing.confirmReceptionOfReturn(ticket3.getId());
        ticketing.createReplacementShipment(ticket3.getId(), "Here are you new items.", "S058359275927", LocalDate.of(2024, 1, 14), "Shipping Ltd.");
        Session.clearSession();

        // Reviews
        social.addReview(seller1Product1, buyer1.getId(), "Best. Graphic. Novel. Ever.", 100);
        Review review1 = social.getReview(seller1Product1, buyer1.getId());
        social.addReview(seller1Product5, buyer1.getId(), "Only 99 meals...", 45);

        // Likes
        social.toggleLikeProduct(seller1Product1, buyer1.getId());
        social.toggleLikeProduct(seller4Product1, buyer1.getId());
        social.toggleLikeProduct(seller1Product4, buyer2.getId());
        social.toggleLikeProduct(seller3Product3, buyer3.getId());
        social.toggleLikeProduct(seller1Product1, buyer4.getId());
        social.toggleLikeProduct(seller1Product1, buyer5.getId());
        social.toggleLikeProduct(seller1Product1, buyer6.getId());
        social.toggleLikeProduct(seller1Product1, buyer7.getId());
        social.toggleLikeProduct(seller1Product1, buyer8.getId());


        social.toggleLikeSeller(seller1Id, buyer1.getId());
        social.toggleLikeSeller(seller3Id, buyer2.getId());

        social.toggleLikeReview(review1.getId(), buyer2.getId());

        social.toggleFollowBuyer(buyer3.getId(), buyer1.getId());

        shop.startProductPromotion(seller2Product3, 500, 200, LocalDate.of(2024, 2, 14));

        System.out.println("Done!");
    }
}
