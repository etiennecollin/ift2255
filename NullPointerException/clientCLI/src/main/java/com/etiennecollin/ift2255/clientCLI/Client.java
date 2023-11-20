/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import com.etiennecollin.ift2255.clientCLI.classes.*;
import com.etiennecollin.ift2255.clientCLI.classes.products.*;

import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The `Client` class serves as the main entry point for the client command-line interface (CLI).
 */
public class Client {
    public static final String savePath;
    public static final UniShop unishop = new UniShop();
    public static boolean inReturnProcess = false;

    static {
        try {
            // Inspired by https://stackoverflow.com/a/3627527
            savePath = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile() + "/unishop_save.txt";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The main method of the `Client` class, which is the entry point for the CLI application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        unishop.loadUserList(savePath);

        while (true) {
            String[] loginMenu = {"Login", "Register", "Quit"};
            int answer = prettyMenu("Welcome to UniShop", loginMenu);

            if (answer == 0) {
                loginForm();
            } else if (answer == 1) {
                createAccount();
            } else if (answer == 2) {
                quit(unishop);
                break;
            }

            User user = unishop.getCurrentUser();
            if (user instanceof Buyer) {
                buyerMenu();
            } else if (user instanceof Seller) {
                sellerMenu();
            }
        }
    }

    private static void loginForm() {
        while (true) {
            clearConsole();
            System.out.println(prettify("Login menu"));
            String[] options = {"Buyer", "Seller"};
            int answer = prettyMenu("Login as", options);

            try {
                if (answer == 0) {
                    String username = prettyPrompt("Username");
                    String password = prettyPrompt("Password");
                    unishop.loginBuyer(username, password);
                } else if (answer == 1) {
                    String username = prettyPrompt("Name");
                    String password = prettyPrompt("Password");
                    unishop.loginSeller(username, password);
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(prettify(e.getMessage()));
                boolean tryAgain = prettyPromptBool("Try again?");
                if (!tryAgain) break;
            }
        }
        clearConsole();
    }

    private static void createAccount() {
        while (true) {
            clearConsole();
            System.out.println(prettify("Account creation menu"));
            String[] options = {"Buyer", "Seller"};
            int answer = prettyMenu("What type of account would you like to create?", options);
            User user = null;

            try {
                if (answer == 0) {
                    user = buyerCreationForm();
                    if (user == null) break;
                    unishop.addUser((Buyer) user);
                } else if (answer == 1) {
                    user = sellerCreationForm();
                    if (user == null) break;
                    unishop.addUser((Seller) user);
                }

                unishop.setCurrentUser(user);
                System.out.println(prettify("Successfully created account"));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(prettify(e.getMessage()));
                boolean tryAgain = prettyPromptBool("Try again?");
                if (!tryAgain) break;
            }
        }
        clearConsole();
    }

    // TODO complete
    public static void buyerMenu() {
        String[] buyerMenu = {"Catalog", "Search a product", "My cart", "Display notifications", "Display activities", "Find user", "My orders", "Update account information", "Log out"};

        loop:
        while (true) {
            int buyerAnswer = prettyMenu("Main menu", buyerMenu);
            switch (buyerAnswer) {
                case 0 -> displayProducts(unishop.getCatalog());
                case 1 -> searchProduct();
                case 2 -> displayCart();
                case 3 -> displayNotifications();
                case 4 -> displayActivities();
                case 5 -> findUser();
                case 6 -> displayOrders();
                case 7 -> updateBuyerInfo();
                case 8 -> {
                    logout(unishop);
                    break loop;
                }
            }
        }
    }

    // TODO complete
    public static void sellerMenu() {
        String[] sellerMenu = {"Offer product", "Modify order status", "Manage issues", "Update account information", "Display notifications", "Display activities", "Log out"};

        loop:
        while (true) {
            int answer = prettyMenu("Main menu", sellerMenu);
            switch (answer) {
                case 0 -> addProduct();
                case 1 -> changeOrderStatus();
                case 2 -> displayTickets();
                case 3 -> updateSellerInfo();
                case 4 -> displayNotifications();
                case 5 -> displayActivities();
                case 6 -> {
                    logout(unishop);
                    break loop;
                }
            }
        }
    }

    private static void displayNotifications() {
        ArrayList<Notification> notifications = (ArrayList<Notification>) unishop.getCurrentUser().getNotifications().clone();
        if (notifications.isEmpty()) {
            System.out.println(prettify("No notifications"));
        } else if (notifications.size() <= 3) {
            // Print notifications in batches of 3
            outerLoop:
            for (int i = 0; i < notifications.size(); i += 3) {
                clearConsole();
                System.out.println(prettify("Notifications " + i + " to " + (i + 3) + ":"));

                ArrayList<String> notificationTitles = new ArrayList<>();
                notificationTitles.add("Go back");

                // Print 3 notifs
                for (int j = i; j < i + 3; j++) {
                    if (j >= notifications.size()) break;
                    Notification notification = notifications.get(j);
                    notificationTitles.add(notification.getTitle());
                    System.out.println(prettify("--------------------"));
                    System.out.println(prettify(notification.getTitle()));
                    System.out.println(prettify(notification.getContent()));
                }

                // Setup action menu
                String[] options = {"Go back", "Delete notification", "See more"};
                innerLoop:
                while (true) {
                    int answer = prettyMenu("Select action", options);
                    switch (answer) {
                        case 0 -> {
                            // Go back by stopping print of notifs
                            break outerLoop;
                        }
                        case 1 -> {
                            // Delete notif
                            int index = prettyMenu("Delete notification", notificationTitles);
                            if (index == 0) break;
                            unishop.getCurrentUser().removeNotification(notifications.get(i + index - 1));
                            System.out.println(prettify("Notification successfully deleted"));
                        }
                        case 2 -> {
                            // Display next notifs
                            break innerLoop;
                        }
                    }
                }
            }
        }
    }

    private static Buyer buyerCreationForm() {
        clearConsole();

        while (true) {
            try {
                String firstName = prettyPrompt("First name", Utils::validateName);
                String lastName = prettyPrompt("Last name", Utils::validateName);
                String username = prettyPrompt("Username", Utils::validateNotEmpty);
                String email = prettyPrompt("Email", Utils::validateEmail);
                String password = prettyPrompt("Password", Utils::validateNotEmpty);
                String phoneNumber = prettyPrompt("Phone number", Utils::validatePhoneNumber);
                String address = prettyPrompt("Shipping address", Utils::validateNotEmpty);

                return new Buyer(firstName, lastName, username, email, phoneNumber, address, password);
            } catch (RuntimeException e) {
                System.out.println(prettify(e.getMessage()));
                boolean tryAgain = prettyPromptBool("Try again?");
                if (!tryAgain) break;
            }
        }

        return null;
    }

    private static Seller sellerCreationForm() {
        clearConsole();

        while (true) {
            try {
                String name = prettyPrompt("Name", Utils::validateName);
                String email = prettyPrompt("Email", Utils::validateEmail);
                String password = prettyPrompt("Password", Utils::validateNotEmpty);
                String phoneNumber = prettyPrompt("Phone number", Utils::validatePhoneNumber);
                String address = prettyPrompt("Shipping address", Utils::validateNotEmpty);

                return new Seller(name, email, phoneNumber, address, password);
            } catch (RuntimeException e) {
                System.out.println(prettify(e.getMessage()));
                boolean tryAgain = prettyPromptBool("Try again?");
                if (!tryAgain) break;
            }
        }

        return null;
    }

    private static void searchProduct() {
        while (true) {
            ArrayList<Product> searchResults = new ArrayList<>();
            ArrayList<String> searchResultsString = new ArrayList<>();
            searchResultsString.add("Main Menu");

            String keyWord = prettyPrompt("Search");

            for (Product p : unishop.getCatalog()) {
                if (p.getTitle().contains(keyWord)) {
                    searchResults.add(p);
                    searchResultsString.add(p.getTitle());
                }
            }

            int answer = prettyMenu("Select a product", searchResultsString);

            if (answer == 0) {
                break;
            } else {
                // Get product
                Product product = searchResults.get(answer - 1); // adjust to product array
                displayProduct(product);
                displayBuyerProductActions(product);
            }
            boolean tryAgain = prettyPromptBool("Keep browsing product?");
            if (!tryAgain) break;
        }
    }

    public static void displayCart() {
        while (true) {
            clearConsole();
            System.out.println(prettify("My cart: "));
            Cart cart = ((Buyer) unishop.getCurrentUser()).getCart();
            if (cart.getProducts().isEmpty()) {
                System.out.println(prettify("Empty cart"));
            } else {
                for (Tuple<Product, Integer> tuple : cart.getProducts()) {
                    Product product = tuple.first;
                    int quantity = tuple.second;
                    int cost = product.getCost() * quantity;

                    System.out.println(prettify(product.getTitle() + " x" + quantity + " | Cost: " + product.getFormattedCost()));
                }
            }
            System.out.println(prettify("Total: " + cart.getFormattedCost() + "$"));

            String[] options = {"Main menu", "Remove product", "Place order", "Empty cart"};
            int answer = prettyMenu("Cart menu", options);
            switch (answer) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    if (cart.getProducts().isEmpty()) {
                        System.out.println(prettify("Empty cart"));
                    } else {
                        ArrayList<Product> products = new ArrayList<>();
                        ArrayList<String> productsString = new ArrayList<>();

                        for (Tuple<Product, Integer> tuple : cart.getProducts()) {
                            Product product = tuple.first;
                            int quantity = tuple.second;
                            products.add(product);
                            productsString.add(product.getTitle() + " x" + quantity + " | Cost: " + product.getFormattedCost());
                        }

                        int productToRemove = prettyMenu("What product do you want to remove?", productsString);
                        Product product = products.get(productToRemove);

                        while (true) {
                            int removeHowMany = prettyPromptInt("How many do you want to remove? (-1 to remove all)");
                            if (removeHowMany == -1 || removeHowMany >= cart.getProducts().get(productToRemove).second) {
                                cart.removeProduct(product);
                            } else if (removeHowMany > 0) {
                                cart.subtractProduct(product, removeHowMany);
                            } else {
                                System.out.println(prettify("Invalid quantity"));

                                boolean tryAgain = prettyPromptBool("Try again?");
                                if (tryAgain) continue;
                            }
                            System.out.println(prettify("Product successfully removed"));
                            break;
                        }
                    }
                }
                case 2 -> {
                    if (cart.getProducts().isEmpty()) {
                        System.out.println(prettify("Empty cart"));
                    } else {
                        placeOrder();
                    }
                }
                case 3 -> {
                    if (cart.getProducts().isEmpty()) {
                        System.out.println(prettify("Empty cart"));
                    } else {
                        cart.emptyCart();
                        System.out.println(prettify("Cart successfully emptied"));
                    }
                }
            }
        }
    }

    // General metrics
    public static void displayActivities() {
        User currentUser = unishop.getCurrentUser();
        int nMonths = prettyPromptInt("Display activities for the last how many months");

        System.out.println(prettify("My activities:"));
        if (currentUser instanceof Buyer) {
            BuyerMetrics metrics = ((Buyer) currentUser).getMetrics(nMonths);
            System.out.println(prettify("Recent orders: " + metrics.numberRecentOrders()));
            System.out.println(prettify("Total orders: " + metrics.numberTotalOrders()));
            System.out.println(prettify("Recent product bought: " + metrics.numberRecentProductsBought()));
            System.out.println(prettify("Total product bought: " + metrics.numberTotalProductsBought()));
            System.out.println(prettify("Followers: " + metrics.numberFollowers()));
            System.out.println(prettify("Average recent reviews: " + metrics.averageRecentReviews()));
            System.out.println(prettify("Average total reviews: " + metrics.averageTotalReviews()));
            System.out.println(prettify("Recent reviews: " + metrics.averageRecentReviews()));
            System.out.println(prettify("Total reviews: " + metrics.numberTotalReviews()));
        } else if (currentUser instanceof Seller) {
            SellerMetrics metrics = ((Seller) currentUser).getMetrics(nMonths);
            System.out.println(prettify("Recent revenue: " + metrics.recentRevenue()));
            System.out.println(prettify("Total revenue: " + metrics.totalRevenue()));
            System.out.println(prettify("Recent products sold: " + metrics.numberRecentProductsSold()));
            System.out.println(prettify("Total products sold: " + metrics.numberTotalProductsSold()));
            System.out.println(prettify("Products offered " + metrics.numberProductsOffered()));
            System.out.println(prettify("Recent product rating average: " + metrics.averageRecentProductRating()));
            System.out.println(prettify("Total product rating average: " + metrics.averageTotalProductRating()));
        }
    }

    public static void findUser() {
        String[] options = {"Main menu", "Buyer", "Seller"};
        loop:
        while (true) {
            int answer = prettyMenu("Search for", options);
            switch (answer) {
                case 0 -> {
                    break loop;
                }
                case 1 -> findBuyer();
                case 2 -> findSeller();
            }
        }
    }

    public static void displayOrders() {
        Buyer currentBuyer = (Buyer) unishop.getCurrentUser();
        ArrayList<Order> orders = currentBuyer.getOrders();

        if (orders.isEmpty()) {
            System.out.println(prettify("No orders"));
        } else if (orders.size() <= 3) {
            // Print orders in batches of 3
            outerLoop:
            for (int i = 0; i < orders.size(); i += 3) {
                clearConsole();
                System.out.println(prettify("Orders " + i + " to " + (i + 3) + ":"));

                ArrayList<String> ordersDate = new ArrayList<>();
                ordersDate.add("Go back");

                // Print 3 notifs
                for (int j = i; j < i + 3; j++) {
                    if (j >= orders.size()) break;
                    Order order = orders.get(j);
                    ordersDate.add("Order of " + order.getOrderDate());

                    System.out.println(prettify("--------------------"));
                    System.out.println(prettify("Order date: " + order.getOrderDate()));
                    System.out.println(prettify("State: " + order.getState()));
                    System.out.println(prettify("Cost: " + order.getCost() / 100 + "." + order.getCost() % 100 + "$"));
                    System.out.println(prettify("Fidelity points earned: " + order.getNumberOfFidelityPoints()));
                    System.out.println(prettify("Number of products: " + order.getProducts().size()));
                }

                // Setup action menu
                String[] options = {"Go back", "Display order", "See more"};
                innerLoop:
                while (true) {
                    int answer = prettyMenu("Select action", options);
                    switch (answer) {
                        case 0 -> {
                            // Go back by stopping print of orders
                            break outerLoop;
                        }
                        case 1 -> {
                            int index = prettyMenu("Select order to display", ordersDate);
                            if (index == 0) break;
                            displayBuyerOrderActions(orders.get(i + index - 1));
                        }
                        case 3 -> {
                            // Display next orders
                            break innerLoop;
                        }
                    }
                }
            }
        }
    }

    public static void displayOrder(Order order) {
        System.out.println(prettify("Order date: " + order.getOrderDate()));
        System.out.println(prettify("State: " + order.getState()));
        System.out.println(prettify("Cost: " + order.getCost() / 100 + "." + order.getCost() % 100 + "$"));
        System.out.println(prettify("Fidelity points earned: " + order.getNumberOfFidelityPoints()));
        System.out.println(prettify("Number of products: " + order.getProducts().size()));
        System.out.println(prettify("Seller: " + order.getSeller()));
        System.out.println(prettify("Fidelity points used to pay: " + order.getPayementMethod().getFidelityPointsUsed()));
        System.out.println(prettify("Money used to pay: " + order.getPayementMethod().getMoneyUsed()));
        System.out.println(prettify("Shipping Address: " + order.getAddress()));
        if (order.getState().equals(OrderState.InTransit)) {
            System.out.println(prettify("Shipping company: " + order.getShippingInfo().getShippingCompany()));
            System.out.println(prettify("Delivery date: " + order.getShippingInfo().getDeliveryDate()));
            System.out.println(prettify("Tracking number: " + order.getShippingInfo().getTrackingNumber()));
        }
    }

    public static void displayBuyerOrderActions(Order order) {
        String[] options = {"Go back", "Confirm reception of order", "Signal issue with order"};

        loop:
        while (true) {
            clearConsole();
            displayOrder(order);

            // Setup action menu
            int answer = prettyMenu("Select action", options);
            switch (answer) {
                case 0 -> {
                    break loop;
                }
                case 1 -> {
                    boolean confirmation = prettyPromptBool("Do you really want to mark this order as delivered?");
                    if (confirmation) {
                        order.setDelivered();
                        System.out.println(prettify("Order successfully marked as delivered"));
                    } else {
                        System.out.println(prettify("Action cancelled"));
                    }
                }
                case 2 -> {
                    boolean confirmation = prettyPromptBool("Do you really want to create an issue for this order?");
                    if (confirmation) {
                        createTicket(order);
                    } else {
                        System.out.println(prettify("Action cancelled"));
                    }
                }
            }
        }
    }

    // TODO
    private static void createTicket(Order order) {
    }

    public static void updateBuyerInfo() {
        Buyer buyer = (Buyer) unishop.getCurrentUser();

        String[] options = new String[]{"Go back", "First name", "Last name", "Password", "Email", "Phone number", "Address"};
        while (true) {
            int answer = prettyMenu("Select the information you'd like to change", options);

            switch (answer) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    String newVal = prettyPrompt("Set a new first name", Utils::validateName);
                    buyer.setFirstName(newVal);
                }
                case 2 -> {
                    String newVal = prettyPrompt("Set a new last name", Utils::validateName);
                    buyer.setLastName(newVal);
                }
                case 3 -> {
                    while (true) {
                        String oldPassword = prettyPrompt("Enter old password", Utils::validateNotEmpty);
                        if (unishop.isPasswordMatching(oldPassword)) {
                            String newVal = prettyPrompt("Set a new password", Utils::validateNotEmpty);
                            buyer.setPassword(newVal);
                            break;
                        } else {
                            System.out.println(prettify("Old password invalid"));
                            boolean tryAgain = prettyPromptBool("Try again?");
                            if (!tryAgain) break;
                        }
                    }
                }
                case 4 -> {
                    String newVal = prettyPrompt("Set a new email address", Utils::validateEmail);
                    buyer.setEmail(newVal);
                }
                case 5 -> {
                    String newVal = prettyPrompt("Set a new phone number", Utils::validatePhoneNumber);
                    buyer.setPhoneNumber(newVal);
                }
                case 6 -> {
                    String newVal = prettyPrompt("Set a new address", Utils::validateNotEmpty);
                    buyer.setAddress(newVal);
                }
            }
        }
    }

    public static void addProduct() {
        Product product = null;

        try {
            String title = prettyPrompt("Title", Utils::validateNotEmpty);
            ProductCategory productCategory = prettyMenu("Category", ProductCategory.class);
            String description = prettyPrompt("Description");
            int price = prettyPromptCurrency("Price");
            int fidelityPoints = prettyPromptInt("Fidelity points", bonusPoints -> validateBonusFidelityPoints(bonusPoints, price));
            int quantity = prettyPromptInt("Quantity", amount -> validateNumberRange(amount, 0, Integer.MAX_VALUE));
            Seller seller = (Seller) unishop.getCurrentUser();

            switch (productCategory) {
                case BookOrManual -> {
                    String author = prettyPrompt("Author", Utils::validateNotEmpty);
                    String editor = prettyPrompt("Publisher", Utils::validateNotEmpty);
                    LocalDate releaseDate = prettyPromptDate("Release date");
                    BookOrManualGenre genre = prettyMenu("Genre", BookOrManualGenre.class);
                    int edition = prettyPromptInt("Edition number (enter 0 if not applicable)");
                    int volume = prettyPromptInt("Volume number (enter 0 if not applicable)");
                    String isbn = prettyPrompt("ISBN", Utils::validateISBN);
                    product = new BookOrManual(price, quantity, title, description, seller, fidelityPoints, isbn, author, editor, genre, releaseDate, edition, volume);
                }
                case IT -> {
                    String brand = prettyPrompt("Brand name", Utils::validateNotEmpty);
                    String model = prettyPrompt("Model name", Utils::validateNotEmpty);
                    LocalDate releaseDate = prettyPromptDate("Release date");
                    ITCategory itCategory = prettyMenu("Sub-category", ITCategory.class);
                    product = new IT(price, quantity, title, description, seller, fidelityPoints, brand, model, releaseDate, itCategory);
                }
                case LearningResource -> {
                    String org = prettyPrompt("Organization", Utils::validateNotEmpty);
                    LocalDate releaseDate = prettyPromptDate("Release date");
                    LearningResourceType type = prettyMenu("Sub-category", LearningResourceType.class);
                    int edition = prettyPromptInt("Edition number (enter 0 if not applicable)");
                    int isbn = prettyPromptInt("ISBN");
                    product = new LearningResource(price, quantity, title, description, seller, fidelityPoints, isbn, org, releaseDate, type, edition);
                }
                case OfficeEquipment -> {
                    String brand = prettyPrompt("Brand name", Utils::validateNotEmpty);
                    String model = prettyPrompt("Model name", Utils::validateNotEmpty);
                    OfficeEquipmentCategory oeCategory = prettyMenu("Sub-category", OfficeEquipmentCategory.class);
                    product = new OfficeEquipment(price, quantity, title, description, seller, fidelityPoints, brand, model, oeCategory);
                }
                case StationeryArticle -> {
                    String brand = prettyPrompt("Brand name", Utils::validateNotEmpty);
                    String model = prettyPrompt("Model name", Utils::validateNotEmpty);
                    StationeryArticleCategory saCategory = prettyMenu("Sub-category", StationeryArticleCategory.class);
                    product = new StationeryArticle(price, quantity, title, description, seller, fidelityPoints, brand, model, saCategory);
                }
            }

            if (prettyPromptBool("Save product?")) {
                ((Seller) unishop.getCurrentUser()).addProductOffered(product);
                unishop.updateCatalog();
                System.out.println("Product " + title + " added!");
            } else {
                System.out.println("Cancelled adding a new product.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // TODO
    public static void changeOrderStatus() {
        String[] orderMenu = new String[]{"#123 - Pending Seller", "Main menu"};
        int orderIdx = prettyMenu("Select the order to update", orderMenu);
        if (orderIdx == orderMenu.length - 1) { // last index returns to main menu
            return;
        }

        String[] orderStatusMenu = new String[]{"In transit", "Main menu"};
        int statusIdx = prettyMenu("Select new status", orderStatusMenu);
        if (statusIdx == orderStatusMenu.length - 1) { // last index returns to main menu
            return;
        }

        String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);
        String trackingId = prettyPrompt("Tracking ID", Utils::validateNotEmpty);

        System.out.println("Order status updated!");
    }

    public static void displayTickets() {
        User currentUser = unishop.getCurrentUser();
        ArrayList<Ticket> tickets = currentUser.getTickets();

        if (tickets.isEmpty()) {
            System.out.println(prettify("No tickets"));
        } else if (tickets.size() <= 3) {
            // Print tickets in batches of 3
            outerLoop:
            for (int i = 0; i < tickets.size(); i += 3) {
                clearConsole();
                System.out.println(prettify("Tickets " + i + " to " + (i + 3) + ":"));

                ArrayList<String> ticketsString = new ArrayList<>();
                ticketsString.add("Go back");

                // Print 3 tickets
                for (int j = i; j < i + 3; j++) {
                    if (j >= tickets.size()) break;
                    Ticket ticket = tickets.get(j);
                    ticketsString.add("Ticket of " + ticket.getCreationDate());

                    System.out.println(prettify("--------------------"));
                    System.out.println(prettify("Creation date: " + ticket.getCreationDate()));
                    System.out.println(prettify("State: " + ticket.getState()));
                    System.out.println(prettify("For order placed on: " + ticket.getOrder().getOrderDate()));
                    System.out.println(prettify("Buyer: " + ticket.getBuyer().getUsername()));
                    System.out.println(prettify("Seller: " + ticket.getSeller().getName()));
                    System.out.println(prettify("Number of products in ticket: " + ticket.getProducts().size()));
                }

                // Setup action menu
                String[] options = {"Go back", "Display ticket", "See more"};
                innerLoop:
                while (true) {
                    int answer = prettyMenu("Select action", options);
                    switch (answer) {
                        case 0 -> {
                            // Go back by stopping print of tickets
                            break outerLoop;
                        }
                        case 1 -> {
                            int index = prettyMenu("Select order to display", ticketsString);
                            if (index == 0) break;

                            if (currentUser instanceof Buyer) {
                                displayBuyerTicketActions(tickets.get(i + index - 1));
                            } else {
                                displaySellerTicketActions(tickets.get(i + index - 1));
                            }
                        }
                        case 3 -> {
                            // Display next tickets
                            break innerLoop;
                        }
                    }
                }
            }
        }
    }

    private static void displayTicket(Ticket ticket) {
        System.out.println(prettify("Creation date: " + ticket.getCreationDate()));
        System.out.println(prettify("State: " + ticket.getState()));
        System.out.println(prettify("For order placed on: " + ticket.getOrder().getOrderDate()));
        System.out.println(prettify("Buyer: " + ticket.getBuyer().getUsername()));
        System.out.println(prettify("Seller: " + ticket.getSeller().getName()));
        System.out.println(prettify("Number of products in ticket: " + ticket.getProducts().size()));
        System.out.println(prettify("Cause of ticket: " + ticket.getCause()));
        System.out.println(prettify("Problem description: " + ticket.getProblemDescription()));
        System.out.println(prettify("Suggested solution: " + ticket.getSuggestedSolution()));
        System.out.println(prettify("Replacement product description: " + ticket.getReplacementProductDescription()));

        if (ticket.getState().equals(TicketState.ReturnInTransit)) {
            System.out.println(prettify("Return shipment creation date: " + ticket.getReturnShipment().getCreationDate()));
            System.out.println(prettify("Return shipment tracking number: " + ticket.getReturnShipment().getTrackingNumber()));
        } else if (ticket.getState().equals(TicketState.ReplacementInTransit)) {
            System.out.println(prettify("Replacement shipment creation date: " + ticket.getReplacementShipment().getCreationDate()));
            System.out.println(prettify("Replacement shipment tracking number: " + ticket.getReplacementShipment().getTrackingNumber()));
        }
    }

    // TODO verify if complete
    private static void displaySellerTicketActions(Ticket ticket) {
        String[] options = {"Go back", "Set suggested solution", "Confirm reception of return shipment", "Set replacement product description", "Create replacement shipment"};

        loop:
        while (true) {
            clearConsole();
            displayTicket(ticket);

            // Setup action menu
            int answer = prettyMenu("Select action", options);
            switch (answer) {
                case 0 -> {
                    break loop;
                }
                case 1 -> {
                    String suggestedSolution = prettyPrompt("Suggested solution", Utils::validateNotEmpty);
                    ticket.setSuggestedSolution(suggestedSolution);
                }
                case 2 -> {
                    boolean confirmation = prettyPromptBool("Do you really want to confirm the reception of the return shipment");
                    if (confirmation) {
                        ticket.getReturnShipment().confirmDelivery();
                        ticket.updateState();
                    } else {
                        System.out.println(prettify("Action cancelled"));
                    }
                }
                case 3 -> {
                    String replacementProductDescription = prettyPrompt("Replacement product description", Utils::validateNotEmpty);
                    ticket.setReplacementProductDescription(replacementProductDescription);
                }
                case 4 -> {
                    String trackingNumber = prettyPrompt("Tracking number of replacement shipment", Utils::validateNotEmpty);
                    ticket.createReplacementShipment(trackingNumber);
                }
            }
        }
    }

    // TODO
    private static void displayBuyerTicketActions(Ticket ticket) {
        // Create returnShipment, confirm reception of replacementShipment
        String[] options = {"Go back", "..."};

        loop:
        while (true) {
            clearConsole();
            displayTicket(ticket);

            // Setup action menu
            int answer = prettyMenu("Select action", options);
            switch (answer) {
                case 0 -> {
                    break loop;
                }
                case 1 -> {
                    boolean confirmation = prettyPromptBool("Do you really want to...");
                    if (confirmation) {
                        // Do somehting
                    } else {
                        System.out.println(prettify("Action cancelled"));
                    }
                }
            }
        }
    }

    public static void updateSellerInfo() {
        Seller seller = (Seller) unishop.getCurrentUser();

        String[] options = new String[]{"Go back", "Name", "Password", "Email", "Phone number", "Address"};
        while (true) {
            int answer = prettyMenu("Select the information you'd like to change", options);

            switch (answer) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    String newVal = prettyPrompt("Set a new name", Utils::validateName);
                    seller.setName(newVal);
                }
                case 2 -> {
                    while (true) {
                        String oldPassword = prettyPrompt("Enter old password", Utils::validateNotEmpty);
                        if (unishop.isPasswordMatching(oldPassword)) {
                            String newVal = prettyPrompt("Set a new password", Utils::validateNotEmpty);
                            seller.setPassword(newVal);
                            break;
                        } else {
                            System.out.println(prettify("Old password invalid"));
                            boolean tryAgain = prettyPromptBool("Try again?");
                            if (!tryAgain) break;
                        }
                    }
                }
                case 3 -> {
                    String newVal = prettyPrompt("Set a new email address", Utils::validateEmail);
                    seller.setEmail(newVal);
                }
                case 4 -> {
                    String newVal = prettyPrompt("Set a new phone number", Utils::validatePhoneNumber);
                    seller.setPhoneNumber(newVal);
                }
                case 5 -> {
                    String newVal = prettyPrompt("Set a new address", Utils::validateNotEmpty);
                    seller.setAddress(newVal);
                }
            }
        }
    }

    public static void displayProduct(Product product) {
        System.out.println(prettify("Title: " + product.getTitle()));
        System.out.println(prettify("Description: ") + product.getDescription());
        System.out.println(prettify("Category: ") + product.getCategory());
        System.out.println(prettify("Subcategory: ") + product.getSubCategory());
        System.out.println(prettify("Price : ") + product.getCost() / 100 + "." + product.getCost() % 100 + "$");
        System.out.println(prettify("Discount: ") + product.getDiscount() + "%");
        System.out.println(prettify("Quantity: ") + product.getQuantity());
        System.out.println(prettify("Fidelity Points: ") + product.getBonusFidelityPoints());
        System.out.println(prettify("Sold by: ") + product.getSeller().getName());
        System.out.println(prettify("Likes: ") + product.getLikes());
        System.out.println(prettify("Commercialization date: ") + product.getCommercializationDate());
    }

    private static void displayBuyerProductActions(Product product) {
        String[] options = {"Go back", "Toggle like", "Display reviews", "Add to cart"};
        while (true) {
            int answer = prettyMenu("Select action", options);
            switch (answer) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    Buyer buyer = (Buyer) unishop.getCurrentUser();
                    boolean wasLiked = buyer.toggleLike(product);

                    if (wasLiked) {
                        System.out.println(prettify("Product successfully liked"));
                    } else {
                        System.out.println(prettify("Product successfully unliked"));
                    }
                }
                case 2 -> {
                    displayReviews(product);
                }
                case 3 -> {
                    int qty = prettyPromptInt("Quantity");
                    if (qty > product.getQuantity()) {
                        System.out.println(prettify("Insufficient product quantity in inventory"));
                    } else {
                        ((Buyer) unishop.getCurrentUser()).getCart().addProduct(product, qty);
                        System.out.println(prettify("Product successfully added to cart"));
                    }
                }
            }

            boolean tryAgain = prettyPromptBool("New action?");
            if (!tryAgain) break;
        }
    }

    private static void placeOrder() {
        Buyer buyer = (Buyer) unishop.getCurrentUser();
        Cart cart = buyer.getCart();

        System.out.println(prettify("Payement form"));
        String shippingAddress = prettyPrompt("Shipping address");

        int fidelityPointsUsed = 0;
        if (buyer.getFidelityPoints() > 0) {
            boolean doUsePoints = prettyPromptBool("You have " + buyer.getFidelityPoints() + " fidelity points. Do you want to use them?");
            if (doUsePoints) {
                int availablePointMoney = buyer.getFidelityPoints() * 2; // In cents
                if (availablePointMoney > cart.getCost()) {
                    fidelityPointsUsed = cart.getCost() / 2;
                } else {
                    fidelityPointsUsed = buyer.getFidelityPoints();
                }

                int toPayWithMoney = cart.getCost() - fidelityPointsUsed * 2;
                System.out.println(prettify("Remaining to pay: " + toPayWithMoney / 100 + "." + toPayWithMoney % 100 + "$"));
            }
        }

        String creditCardName = prettyPrompt("Credit card name");

        String creditCardNumber;
        while (true) {
            creditCardNumber = prettyPrompt("Credit card number");
            if (creditCardNumber.length() == 16 && creditCardNumber.matches("\\d+")) {
                break;
            } else {
                System.out.println(prettify("Invalid credit card number"));
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyy");
        YearMonth expirationDate;
        while (true) {
            try {
                expirationDate = YearMonth.parse(prettyPrompt("Expiration date MMYY"), formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println(prettify("Invalid expiration date"));
            }
        }

        String cvc;
        while (true) {
            cvc = prettyPrompt("CVC");
            if (cvc.length() == 3 && cvc.matches("\\d+")) {
                break;
            } else {
                System.out.println(prettify("Invalid CVC"));
            }
        }

        // Confirm order
        boolean doOrder = prettyPromptBool("Do you want to place the order?");

        if (!doOrder) {
            System.out.println("Cancelled order.");
            return;
        }

        buyer.getCart().createOrder(buyer.getEmail(), buyer.getPhoneNumber(), shippingAddress, buyer.getAddress(), creditCardName, creditCardNumber, expirationDate, cvc, fidelityPointsUsed);

        System.out.println(prettify("Your order has been placed successfully"));
    }

    private static void findBuyer() {
        loop:
        while (true) {
            String[] searchBy = {"Go Back", "Name", "Phone number", "email"};
            int search = prettyMenu("Search buyer by", searchBy);

            ArrayList<Buyer> matchList = new ArrayList<>();
            ArrayList<String> matchListString = new ArrayList<>();
            switch (search) {
                case 0 -> {
                    break loop;
                }
                case 1 -> {
                    String name = prettyPrompt("Name").toLowerCase();
                    for (Buyer buyer : unishop.getBuyerList().values()) {
                        String fullId = (buyer.getFirstName() + " " + buyer.getLastName() + " " + buyer.getUsername()).toLowerCase();
                        if (fullId.contains(name)) {
                            matchList.add(buyer);
                            matchListString.add(buyer.getUsername());
                        }
                    }
                }
                case 2 -> {
                    String phoneNumber = prettyPrompt("Phone number");
                    for (Buyer buyer : unishop.getBuyerList().values()) {
                        if (buyer.getPhoneNumber().contains(phoneNumber)) {
                            matchList.add(buyer);
                            matchListString.add(buyer.getUsername());
                        }
                    }
                }
                case 3 -> {
                    String email = prettyPrompt("email").toLowerCase();
                    for (Buyer buyer : unishop.getBuyerList().values()) {
                        if (buyer.getEmail().contains(email)) {
                            matchList.add(buyer);
                            matchListString.add(buyer.getUsername());
                        }
                    }
                }
            }

            matchListString.add("Go back");
            while (true) {
                int index = prettyMenu("Select buyer", matchListString);
                if (index == matchListString.size() - 1) break;

                Buyer buyer = matchList.get(index);
                displayBuyer(buyer);
            }
        }
    }

    private static void findSeller() {
        loop:
        while (true) {
            String[] searchBy = {"Go back", "Name", "Address", "Phone number", "email"};
            int search = prettyMenu("Search seller by", searchBy);

            ArrayList<Seller> matchList = new ArrayList<>();
            ArrayList<String> matchListString = new ArrayList<>();
            switch (search) {
                case 0 -> {
                    break loop;
                }
                case 1 -> {
                    String name = prettyPrompt("Name").toLowerCase();
                    for (Seller seller : unishop.getSellerList().values()) {
                        if (seller.getName().toLowerCase().contains(name)) {
                            matchList.add(seller);
                            matchListString.add(seller.getName());
                        }
                    }
                }
                case 2 -> {
                    String address = prettyPrompt("Address").toLowerCase();
                    for (Seller seller : unishop.getSellerList().values()) {
                        if (seller.getAddress().toLowerCase().contains(address)) {
                            matchList.add(seller);
                            matchListString.add(seller.getName());
                        }
                    }
                }
                case 3 -> {
                    String phoneNumber = prettyPrompt("Phone number");
                    for (Seller seller : unishop.getSellerList().values()) {
                        if (seller.getPhoneNumber().contains(phoneNumber)) {
                            matchList.add(seller);
                            matchListString.add(seller.getName());
                        }
                    }
                }
                case 4 -> {
                    String email = prettyPrompt("email").toLowerCase();
                    for (Seller seller : unishop.getSellerList().values()) {
                        if (seller.getEmail().contains(email)) {
                            matchList.add(seller);
                            matchListString.add(seller.getName());
                        }
                    }
                }
            }

            matchListString.add("Go back");
            while (true) {
                int index = prettyMenu("Select seller", matchListString);
                if (index == matchListString.size() - 1) break;

                Seller seller = matchList.get(index);
                displaySeller(seller);
            }
        }
    }

    private static void displayReviews(Product product) {
        ArrayList<Review> reviews = product.getReviews();
        if (reviews.isEmpty()) {
            System.out.println(prettify("No reviews for this product"));
        } else if (reviews.size() <= 3) {
            // Print reviews in batches of 3
            for (int i = 0; i < reviews.size(); i += 3) {
                clearConsole();
                System.out.println(prettify("Reviews " + i + " to " + (i + 3) + ":"));
                for (int j = i; j < i + 3; j++) {
                    if (j >= reviews.size()) break;
                    Review review = reviews.get(j);
                    Buyer author = review.getAuthor();
                    System.out.println(prettify("--------------------"));
                    System.out.println(prettify(author.getFirstName() + " " + author.getLastName() + " - " + review.getRating() + "/100"));
                    System.out.println(prettify("Title: " + review.getTitle()));
                    System.out.println(prettify(review.getContent()));
                }
                boolean tryAgain = prettyPromptBool("See more reviews?");
                if (!tryAgain) break;
            }
        }
    }

    public static void displaySeller(Seller seller) {
        Buyer currentBuyer = (Buyer) unishop.getCurrentUser();

        loop:
        while (true) {
            System.out.println(prettify("Name: " + seller.getName()));
            System.out.println(prettify("Email: " + seller.getEmail()));
            System.out.println(prettify("Address: " + seller.getAddress()));
            System.out.println(prettify("Phone number: " + seller.getPhoneNumber()));
            System.out.println(prettify("Number of products offered: " + seller.getProductsOffered().size()));
            System.out.println(prettify("Number of orders sold: " + seller.getOrdersSold().size()));
            System.out.println(prettify("Followed by you: " + currentBuyer.doesLike(seller)));

            String[] options = {"Go back", "Toggle follow", "Display seller's products"};
            int answer = prettyMenu("Select action", options);
            switch (answer) {
                case 0 -> {
                    break loop;
                }
                case 1 -> {
                    currentBuyer.toggleLike(seller);
                    System.out.println(prettify("Successfully toggled follow"));
                }
                case 2 -> displayProducts(seller.getProductsOffered());
            }
        }
    }

    public static void displayProducts(ArrayList<Product> source) {

        ArrayList<String> options = ProductCategory.getOptions();
        options.add("Main menu");

        while (true) {
            // Select category
            int choice = prettyMenu("Categories", options);
            if (choice == options.size() - 1) break;
            ProductCategory selectedCategory = ProductCategory.values()[choice];

            // Select subcategory
            ArrayList<String> subOptions = selectedCategory.getSubOptions();
            subOptions.add("Main menu");

            int subChoice = prettyMenu("Sub-Categories", subOptions);
            if (subChoice == subOptions.size() - 1) break;
            Enum<?> selectedSubCategory = selectedCategory.getEnum().getEnumConstants()[subChoice];

            // Get products that match category/subcategory

            ArrayList<Product> matchedProducts = new ArrayList<>();
            ArrayList<String> matchedProductsString = new ArrayList<>();
            matchedProductsString.add("Back to categories");
            matchedProductsString.add("Back to main menu");

            for (Product product : source) {
                if (product.getCategory().equals(selectedCategory) && product.getSubCategory().equals(selectedSubCategory)) {
                    matchedProducts.add(product);
                    matchedProductsString.add(product.getTitle());
                }
            }

            int answer = prettyMenu("Select a product", matchedProductsString);

            // Check if we go back
            if (answer == 0) {
                continue;
            } else if (answer == 1) {
                break;
            }

            // Get product
            Product product = matchedProducts.get(answer - 2);
            displayProduct(product);
            displayBuyerProductActions(product);

            boolean tryAgain = prettyPromptBool("Keep browsing product?");
            if (!tryAgain) break;
        }
    }

    public static void displayBuyer(Buyer buyer) {
        Buyer currentBuyer = (Buyer) unishop.getCurrentUser();

        loop:
        while (true) {
            System.out.println(prettify("Username: " + buyer.getUsername()));
            System.out.println(prettify("Full name: ") + buyer.getFirstName() + " " + buyer.getLastName());
            System.out.println(prettify("Followed by you: " + currentBuyer.doesLike(buyer)));
            System.out.println(prettify("Follows you: " + buyer.doesLike(currentBuyer)));
            System.out.println(prettify("Number of fidelity points: " + buyer.getFidelityPoints()));
            System.out.println(prettify("Number of reviews written: " + buyer.getReviewsWritten().size()));
            System.out.println(prettify("Number of reviews liked: " + buyer.getReviewsLiked().size()));
            System.out.println(prettify("Number of products liked: " + buyer.getProductsLiked().size()));
            System.out.println(prettify("Number of order bought: " + buyer.getOrders().size()));

            String[] options = {"Go back", "Toggle follow"};
            int answer = prettyMenu("Select action", options);
            switch (answer) {
                case 0 -> {
                    break loop;
                }
                case 1 -> {
                    currentBuyer.toggleLike(buyer);
                    System.out.println(prettify("Successfully toggled follow"));
                }
            }
        }
    }
}
