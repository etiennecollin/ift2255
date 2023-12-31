/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

// import com.etiennecollin.ift2255.clientCLI.classes.*;


/**
 * The `Client` class serves as the main entry point for the client command-line interface (CLI).
 */
public class Client {
    //    public static final String savePath;
    //    public static final UniShop unishop = new UniShop();
    //    public static boolean inReturnProcess = false;

    //    static {
    //        try {
    //            // Inspired by https://stackoverflow.com/a/3627527
    //            savePath = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile() + "/unishop_save.txt";
    //        } catch (URISyntaxException e) {
    //            throw new RuntimeException(e);
    //        }
    //    }

    /**
     * The main method that initializes the UniShop instance and renders views.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        UniShop.getInstance().getRenderer().renderViews();
        //        unishop.loadUserList(savePath);
        //
        //        String[] loginMenu = {"Login", "Register", "Quit"};
        //        while (true) {
        //            clearConsole();
        //            int answer = prettyMenu("Welcome to UniShop", loginMenu);
        //
        //            if (answer == 0) {
        //                loginForm();
        //            } else if (answer == 1) {
        //                createAccount();
        //            } else if (answer == 2) {
        ////                quit(unishop);
        //                break;
        //            }
        //
        //            User user = unishop.getCurrentUser();
        //            if (user instanceof Buyer) {
        //                buyerMenu();
        //            } else if (user instanceof Seller) {
        //                sellerMenu();
        //            }
        //        }
    }

    //    private static void loginForm() {
    //        while (true) {
    //            clearConsole();
    //            System.out.println(prettify("Login menu"));
    //            String[] options = {"Buyer", "Seller"};
    //            int answer = prettyMenu("Login as", options);
    //
    //            ValidationResult result;
    //            if (answer == 0) {
    //                String username = prettyPrompt("Username");
    //                String password = prettyPrompt("Password");
    //                result = unishop.getAuth().authenticateBuyer(username, password);
    //            } else if (answer == 1) {
    //                String username = prettyPrompt("Name");
    //                String password = prettyPrompt("Password");
    //                result = unishop.getAuth().authenticateSeller(username, password);
    //            } else {
    //                break;
    //            }
    //
    //            if (result.isValid()) {
    //                break;
    //            }
    //            else if (!prettyPromptBool("Try again?")) {
    //                break;
    //            }
    //        }
    //        clearConsole();
    //    }
    //
    //    private static void createAccount() {
    //        while (true) {
    //            clearConsole();
    //            System.out.println(prettify("Account creation menu"));
    //            String[] options = {"Buyer", "Seller"};
    //            int answer = prettyMenu("What type of account would you like to create?", options);
    //            User user = null;
    //
    //            try {
    //                if (answer == 0) {
    //                    user = buyerCreationForm();
    //                    if (user == null) break;
    //                    unishop.addUser((Buyer) user);
    //                } else if (answer == 1) {
    //                    user = sellerCreationForm();
    //                    if (user == null) break;
    //                    unishop.addUser((Seller) user);
    //                }
    //
    //                unishop.setCurrentUser(user);
    //                System.out.println(prettify("Successfully created account"));
    //                break;
    //            } catch (IllegalArgumentException e) {
    //                System.out.println(prettify(e.getMessage()));
    //                if (!prettyPromptBool("Try again?")) break;
    //            }
    //        }
    //        clearConsole();
    //    }
    //
    //    public static void buyerMenu() {
    //        String[] buyerMenu = {"Display catalog", "Search a product", "Display cart", "Find user", "Display orders", "Display notifications", "Display tickets", "Display activities", "Update account information", "Log out"};
    //
    //        loop:
    //        while (true) {
    //            clearConsole();
    //            int buyerAnswer = prettyMenu("Main menu", buyerMenu);
    //            switch (buyerAnswer) {
    //                case 0 -> displayProducts(unishop.getCatalog());
    //                case 1 -> searchProduct();
    //                case 2 -> displayCart();
    //                case 3 -> findUser();
    //                case 4 -> displayBuyerOrders();
    //                case 5 -> displayNotifications();
    //                case 6 -> displayTickets();
    //                case 7 -> displayActivities();
    //                case 8 -> updateBuyerInfo();
    //                case 9 -> {
    //                    logout(unishop);
    //                    break loop;
    //                }
    //            }
    //        }
    //    }
    //
    //    public static void sellerMenu() {
    //        String[] sellerMenu = {"Offer product", "Change order status", "Display notifications", "Display tickets", "Display activities", "Update account information", "Log out"};
    //
    //        loop:
    //        while (true) {
    //            clearConsole();
    //            int answer = prettyMenu("Main menu", sellerMenu);
    //            switch (answer) {
    //                case 0 -> offerProduct();
    //                case 1 -> displayPendingSellerOrders();
    //                case 2 -> displayNotifications();
    //                case 3 -> displayTickets();
    //                case 4 -> displayActivities();
    //                case 5 -> updateSellerInfo();
    //                case 6 -> {
    //                    logout(unishop);
    //                    break loop;
    //                }
    //            }
    //        }
    //    }
    //
    //    private static void displayNotifications() {
    //        ArrayList<Notification> notifications = (ArrayList<Notification>) unishop.getCurrentUser().getNotifications().clone();
    //        clearConsole();
    //        if (notifications.isEmpty()) {
    //            System.out.println(prettify("No notifications"));
    //            waitForKey();
    //        } else {
    //            prettyPaginationMenu(notifications, 3, "Delete notification", notification -> {
    //                System.out.println(prettify("--------------------"));
    //                System.out.println(prettify(notification.getTitle()));
    //                System.out.println(prettify(notification.getContent()));
    //            }, Notification::getTitle, unishop.getCurrentUser()::removeNotification);
    //        }
    //    }
    //
    //    private static Buyer buyerCreationForm() {
    //        while (true) {
    //            clearConsole();
    //            try {
    //                String firstName = prettyPrompt("First name", Utils::validateName);
    //                String lastName = prettyPrompt("Last name", Utils::validateName);
    //                String username = prettyPrompt("Username", Utils::validateNotEmpty);
    //                String email = prettyPrompt("Email", Utils::validateEmail);
    //                String password = prettyPrompt("Password", Utils::validateNotEmpty);
    //                String phoneNumber = prettyPrompt("Phone number", Utils::validatePhoneNumber);
    //                String address = prettyPrompt("Shipping address", Utils::validateNotEmpty);
    //
    //                return new Buyer(firstName, lastName, username, email, phoneNumber, address, password);
    //            } catch (RuntimeException e) {
    //                System.out.println(prettify(e.getMessage()));
    //
    //                if (!prettyPromptBool("Try again?")) break;
    //            }
    //        }
    //
    //        return null;
    //    }
    //
    //    private static Seller sellerCreationForm() {
    //        while (true) {
    //            clearConsole();
    //            try {
    //                String name = prettyPrompt("Name", Utils::validateNotEmpty);
    //                String email = prettyPrompt("Email", Utils::validateEmail);
    //                String password = prettyPrompt("Password", Utils::validateNotEmpty);
    //                String phoneNumber = prettyPrompt("Phone number", Utils::validatePhoneNumber);
    //                String address = prettyPrompt("Shipping address", Utils::validateNotEmpty);
    //
    //                return new Seller(name, email, phoneNumber, address, password);
    //            } catch (RuntimeException e) {
    //                System.out.println(prettify(e.getMessage()));
    //
    //                if (!prettyPromptBool("Try again?")) break;
    //            }
    //        }
    //
    //        return null;
    //    }
    //
    //    private static void searchProduct() {
    //        while (true) {
    //            clearConsole();
    //            ArrayList<Product> searchResults = new ArrayList<>();
    //            ArrayList<String> searchResultsString = new ArrayList<>();
    //            searchResultsString.add("Main Menu");
    //
    //            String keyWord = prettyPrompt("Search").toLowerCase();
    //
    //            for (Product p : unishop.getCatalog()) {
    //                if (p.getTitle().toLowerCase().contains(keyWord) || p.getDescription().toLowerCase().contains(keyWord)) {
    //                    searchResults.add(p);
    //                    searchResultsString.add(p.getTitle());
    //                }
    //            }
    //
    //            if (searchResults.isEmpty()) {
    //                System.out.println(prettify("No match found"));
    //                if (!prettyPromptBool("Keep browsing product?")) break;
    //                continue;
    //            }
    //
    //            int answer = prettyMenu("Select a product", searchResultsString);
    //
    //            if (answer == 0) {
    //                break;
    //            } else {
    //                // Get product
    //                Product product = searchResults.get(answer - 1); // adjust to product array
    //                displayProduct(product);
    //                displayBuyerProductActions(product);
    //            }
    //
    //            if (!prettyPromptBool("Keep browsing product?")) break;
    //        }
    //    }
    //
    //    public static void displayCart() {
    //        while (true) {
    //            clearConsole();
    //            System.out.println(prettify("My cart: "));
    //            Cart cart = ((Buyer) unishop.getCurrentUser()).getCart();
    //
    //            if (cart.getProducts().isEmpty()) {
    //                System.out.println(prettify("Empty cart"));
    //                waitForKey();
    //                break;
    //            }
    //
    //            for (Tuple<Product, Integer> tuple : cart.getProducts()) {
    //                Product product = tuple.first;
    //                int quantity = tuple.second;
    //
    //                System.out.println(prettify(product.getTitle() + " x" + quantity + " | Cost: " + product.getFormattedCost(quantity)));
    //            }
    //            System.out.println(prettify("Total: " + cart.getFormattedCost() + "$"));
    //
    //            String[] options = {"Main menu", "Remove product", "Place order", "Empty cart"};
    //            int answer = prettyMenu("Cart menu", options);
    //            switch (answer) {
    //                case 0 -> {
    //                    return;
    //                }
    //                case 1 -> {
    //                    if (cart.getProducts().isEmpty()) {
    //                        System.out.println(prettify("Empty cart"));
    //                    } else {
    //                        ArrayList<Product> products = new ArrayList<>();
    //                        ArrayList<String> productsString = new ArrayList<>();
    //
    //                        for (Tuple<Product, Integer> tuple : cart.getProducts()) {
    //                            Product product = tuple.first;
    //                            int quantity = tuple.second;
    //                            products.add(product);
    //                            productsString.add(product.getTitle() + " x" + quantity + " | Cost: " + product.getFormattedCost());
    //                        }
    //
    //                        int productToRemove = prettyMenu("What product do you want to remove?", productsString);
    //                        Product product = products.get(productToRemove);
    //
    //                        while (true) {
    //                            int removeHowMany = prettyPromptInt("How many do you want to remove? (-1 to remove all)");
    //                            if (removeHowMany == -1 || removeHowMany >= cart.getProducts().get(productToRemove).second) {
    //                                cart.removeProduct(product);
    //                            } else if (removeHowMany > 0) {
    //                                cart.subtractProduct(product, removeHowMany);
    //                            } else {
    //                                System.out.println(prettify("Invalid quantity"));
    //
    //                                if (prettyPromptBool("Try again?")) continue;
    //                            }
    //                            System.out.println(prettify("Product successfully removed"));
    //                            break;
    //                        }
    //                    }
    //                }
    //                case 2 -> {
    //                    if (cart.getProducts().isEmpty()) {
    //                        System.out.println(prettify("Empty cart"));
    //                        waitForKey();
    //                    } else {
    //                        placeOrder();
    //                    }
    //                }
    //                case 3 -> {
    //                    if (cart.getProducts().isEmpty()) {
    //                        System.out.println(prettify("Empty cart"));
    //                    } else {
    //                        cart.emptyCart();
    //                        System.out.println(prettify("Cart successfully emptied"));
    //                    }
    //                    waitForKey();
    //                }
    //            }
    //        }
    //    }
    //
    //    public static void displayActivities() {
    //        clearConsole();
    //        User currentUser = unishop.getCurrentUser();
    //        int nMonths = prettyPromptInt("Display activities for the last how many months");
    //
    //        System.out.println(prettify("My activities:"));
    //        if (currentUser instanceof Buyer) {
    //            BuyerMetrics metrics = ((Buyer) currentUser).getMetrics(nMonths);
    //            System.out.println(prettify("Recent orders: " + metrics.numberRecentOrders()));
    //            System.out.println(prettify("Total orders: " + metrics.numberTotalOrders()));
    //            System.out.println(prettify("Recent product bought: " + metrics.numberRecentProductsBought()));
    //            System.out.println(prettify("Total product bought: " + metrics.numberTotalProductsBought()));
    //            System.out.println(prettify("Followers: " + metrics.numberProductsLiked()));
    //            System.out.println(prettify("Average recent reviews: " + metrics.averageRecentReviews()));
    //            System.out.println(prettify("Average total reviews: " + metrics.averageTotalReviews()));
    //            System.out.println(prettify("Recent reviews: " + metrics.averageRecentReviews()));
    //            System.out.println(prettify("Total reviews: " + metrics.numberTotalReviews()));
    //        } else if (currentUser instanceof Seller) {
    //            SellerMetrics metrics = ((Seller) currentUser).getMetrics(nMonths);
    //            System.out.println(prettify("Recent revenue: " + metrics.recentRevenue()));
    //            System.out.println(prettify("Total revenue: " + metrics.totalRevenue()));
    //            System.out.println(prettify("Recent products sold: " + metrics.numberRecentProductsSold()));
    //            System.out.println(prettify("Total products sold: " + metrics.numberTotalProductsSold()));
    //            System.out.println(prettify("Products offered: " + metrics.numberProductsOffered()));
    //            if (metrics.averageRecentProductRating() != -1 && metrics.averageTotalProductRating() != -1) {
    //                System.out.println(prettify("Recent product rating average: " + metrics.averageRecentProductRating()));
    //                System.out.println(prettify("Total product rating average: " + metrics.averageTotalProductRating()));
    //            }
    //        }
    //        waitForKey();
    //    }
    //
    //    public static void findUser() {
    //        String[] options = {"Main menu", "Buyer", "Seller"};
    //        loop:
    //        while (true) {
    //            clearConsole();
    //            int answer = prettyMenu("Search for", options);
    //            switch (answer) {
    //                case 0 -> {
    //                    break loop;
    //                }
    //                case 1 -> findBuyer();
    //                case 2 -> findSeller();
    //            }
    //        }
    //    }
    //
    //    public static void displayBuyerOrders() {
    //        clearConsole();
    //        Buyer currentBuyer = (Buyer) unishop.getCurrentUser();
    //        ArrayList<Order> orders = currentBuyer.getOrders();
    //
    //        if (orders.isEmpty()) {
    //            System.out.println(prettify("No orders"));
    //            waitForKey();
    //            return;
    //        }
    //
    //        prettyPaginationMenu(orders, 3, "Display order", order -> {
    //            System.out.println(prettify("--------------------"));
    //            System.out.println(prettify("Order date: " + order.getOrderDate()));
    //            System.out.println(prettify("State: " + order.getState()));
    //            System.out.println(prettify("Cost: " + order.getCost() / 100 + "." + order.getCost() % 100 + "$"));
    //            System.out.println(prettify("Fidelity points earned: " + order.getNumberOfFidelityPoints()));
    //            System.out.println(prettify("Number of products: " + order.getProducts().size()));
    //        }, order -> "Order of " + order.getOrderDate(), Client::displayBuyerOrderActions);
    //    }
    //
    //    public static void displayOrder(Order order) {
    //        clearConsole();
    //        System.out.println(prettify("Order date: " + order.getOrderDate()));
    //        System.out.println(prettify("State: " + order.getState()));
    //        System.out.println(prettify("Cost: " + order.getCost() / 100 + "." + order.getCost() % 100 + "$"));
    //        System.out.println(prettify("Fidelity points earned: " + order.getNumberOfFidelityPoints()));
    //        System.out.println(prettify("Number of products: " + order.getProducts().size()));
    //        System.out.println(prettify("Buyer: " + order.getBuyer().getUsername()));
    //        System.out.println(prettify("Seller: " + order.getSeller().getName()));
    //        System.out.println(prettify("Fidelity points used to pay: " + order.getPaymentMethod().getFidelityPointsUsed()));
    //        System.out.println(prettify("Money used to pay: " + order.getPaymentMethod().getMoneyUsed() / 100 + "." + order.getPaymentMethod().getMoneyUsed() % 100 + "$"));
    //        System.out.println(prettify("Shipping Address: " + order.getAddress()));
    //        if (order.getState().equals(OrderState.InTransit)) {
    //            System.out.println(prettify("Shipping company: " + order.getShipment().getShippingCompany()));
    //            System.out.println(prettify("Delivery date: " + order.getShipment().getExpectedDeliveryDate()));
    //            System.out.println(prettify("Tracking number: " + order.getShipment().getTrackingNumber()));
    //        }
    //        waitForKey();
    //    }
    //
    //    public static void displayBuyerOrderActions(Order order) {
    //        ArrayList<DynamicMenuItem> options = new ArrayList<>();
    //
    //        options.add(new DynamicMenuItem("Review a product", () -> {
    //            prettyPaginationMenu(order.getProducts(), 3, "Review product", (tuple) -> {
    //                Product product = tuple.first;
    //                int quantity = tuple.second;
    //                String totalPrice = (product.getCost() * quantity) / 100 + "." + (product.getCost() * quantity) % 100 + "$";
    //                System.out.println(prettify("--------------------"));
    //                System.out.println(prettify("Product name: " + product.getTitle()));
    //                System.out.println(prettify("Quantity: " + quantity));
    //                System.out.println(prettify("Price per product: " + product.getCost() / 100 + "." + product.getCost() % 100 + "$"));
    //                System.out.println(prettify("Total price: " + totalPrice));
    //            }, tuple -> tuple.first.getTitle(), tuple -> Client.reviewProduct(tuple.first));
    //        }, () -> order.getState().equals(OrderState.Delivered)));
    //        options.add(new DynamicMenuItem("Confirm reception of order", () -> {
    //            if (prettyPromptBool("Do you really want to mark this order as delivered?")) {
    //                order.setDelivered();
    //                System.out.println(prettify("Order successfully marked as delivered"));
    //            } else {
    //                System.out.println(prettify("Action cancelled"));
    //            }
    //        }, () -> order.getState().equals(OrderState.InTransit)));
    //        options.add(new DynamicMenuItem("Report issue with order", () -> {
    //            if (prettyPromptBool("Do you really want to open a ticket for this order?")) {
    //                createTicket(order);
    //            } else {
    //                System.out.println(prettify("Action cancelled"));
    //            }
    //        }, () -> !order.getState().equals(OrderState.Cancelled) && LocalDate.now().isBefore(order.getOrderDate().plusDays(365))));
    //        options.add(new DynamicMenuItem("Return items", () -> {
    //            if (prettyPromptBool("Do you really want to return items from this order?")) {
    //                displayReturnMenu(order);
    //            } else {
    //                System.out.println(prettify("Action cancelled"));
    //            }
    //        }, () -> !order.getState().equals(OrderState.Cancelled) && (order.getShipment() == null || LocalDate.now().isBefore(order.getShipment().getExpectedDeliveryDate().plusDays(30)))));
    //        options.add(new DynamicMenuItem("Exchange items", () -> {
    //            if (prettyPromptBool("Do you really want to exchange items from this order?")) {
    //                displayExchangeMenu(order);
    //            } else {
    //                System.out.println(prettify("Action cancelled"));
    //            }
    //        }, () -> !order.getState().equals(OrderState.Cancelled) && (order.getShipment() == null || LocalDate.now().isBefore(order.getShipment().getExpectedDeliveryDate().plusDays(30)))));
    //        options.add(new DynamicMenuItem("Cancel order", () -> {
    //            if (prettyPromptBool("Do you really want to cancel this order?")) {
    //                order.setCancelled();
    //                System.out.println(prettify("Order cancelled"));
    //            } else {
    //                System.out.println(prettify("Action cancelled"));
    //            }
    //        }, () -> order.getState().equals(OrderState.InProduction)));
    //
    //        prettyDynamicMenu("Select action", "Go back", options, () -> displayOrder(order));
    //    }
    //
    //    private static void reviewProduct(Product product) {
    //        Buyer author = (Buyer) unishop.getCurrentUser();
    //
    //        for (Review review : author.getReviewsWritten()) {
    //            if (review.getProduct().equals(product)) {
    //                System.out.println(prettify("This product has already been reviewed"));
    //                waitForKey();
    //                return;
    //            }
    //        }
    //
    //        String title = prettyPrompt("Title of your review", Utils::validateNotEmpty);
    //        String content = prettyPrompt("Content of your review", Utils::validateNotEmpty);
    //        int rating = prettyPromptInt("Rating out of 100", number -> Utils.validateNumberRange(number, 0, 100));
    //        Review review = new Review(content, title, author, product, rating);
    //        product.addReview(review);
    //
    //        System.out.println(prettify("Review successfully submitted"));
    //        waitForKey();
    //    }
    //
    //    // TODO test
    //    private static void createTicket(Order order) {
    //        TicketCause cause = prettyMenu("Select the type of issue", TicketCause.class);
    //        String description = prettyPrompt("Description of problem", Utils::validateNotEmpty);
    //
    //        if (prettyPromptBool("Do you really want to open a ticket for this order?")) {
    //            order.createTicket(description, cause, TicketState.OpenManual);
    //            System.out.println(prettify("Ticket successfully opened"));
    //        } else {
    //            System.out.println(prettify("Cancelled ticket creation"));
    //        }
    //
    //        waitForKey();
    //    }
    //
    //    // TODO test
    //    private static void displayReturnMenu(Order order) {
    //        HashSet<Tuple<Product, Integer>> returnProducts = new HashSet<>();
    //        prettyPaginationMenu(order.getProducts(), 5, "Select item with problem", productTuple -> System.out.println(prettify(productTuple.first + " x" + productTuple.second)), productIntegerTuple -> productIntegerTuple.first + " x" + productIntegerTuple.second, returnProducts::add);
    //
    //        if (returnProducts.isEmpty()) {
    //            System.out.println(prettify("No products selected to return"));
    //            waitForKey();
    //        }
    //
    //        TicketCause cause = prettyMenu("Select the type of issue", TicketCause.class);
    //
    //        Ticket ticket = order.createTicket("", new ArrayList<>(returnProducts), cause, TicketState.OpenAuto, null);
    //        ticket.setSuggestedSolution("Return request accepted. Please bring the package to your nearest post office.");
    //        System.out.println(prettify(ticket.getSuggestedSolution()));
    //
    //        waitForKey();
    //    }
    //
    //    // TODO
    //    private static void displayExchangeMenu(Order order) {
    //        System.out.println(prettify("This functionality is not yet implemented"));
    //        waitForKey();
    //    }
    //
    //    public static void updateBuyerInfo() {
    //        Buyer buyer = (Buyer) unishop.getCurrentUser();
    //
    //        String[] options = new String[]{"Go back", "First name", "Last name", "Password", "Email", "Phone number", "Address"};
    //        while (true) {
    //            clearConsole();
    //            int answer = prettyMenu("Select the information you'd like to change", options);
    //
    //            switch (answer) {
    //                case 0 -> {
    //                    return;
    //                }
    //                case 1 -> {
    //                    String newVal = prettyPrompt("Set a new first name", Utils::validateName);
    //                    buyer.setFirstName(newVal);
    //                }
    //                case 2 -> {
    //                    String newVal = prettyPrompt("Set a new last name", Utils::validateName);
    //                    buyer.setLastName(newVal);
    //                }
    //                case 3 -> {
    //                    while (true) {
    //                        String oldPassword = prettyPrompt("Enter old password", Utils::validateNotEmpty);
    //                        if (unishop.isPasswordMatching(oldPassword)) {
    //                            String newVal = prettyPrompt("Set a new password", Utils::validateNotEmpty);
    //                            buyer.setPassword(newVal);
    //                            break;
    //                        } else {
    //                            System.out.println(prettify("Old password invalid"));
    //                            if (!prettyPromptBool("Try again?")) break;
    //                        }
    //                    }
    //                }
    //                case 4 -> {
    //                    String newVal = prettyPrompt("Set a new email address", Utils::validateEmail);
    //                    buyer.setEmail(newVal);
    //                }
    //                case 5 -> {
    //                    String newVal = prettyPrompt("Set a new phone number", Utils::validatePhoneNumber);
    //                    buyer.setPhoneNumber(newVal);
    //                }
    //                case 6 -> {
    //                    String newVal = prettyPrompt("Set a new address", Utils::validateNotEmpty);
    //                    buyer.setAddress(newVal);
    //                }
    //            }
    //        }
    //    }
    //
    //    public static void offerProduct() {
    //        Product product = null;
    //        clearConsole();
    //
    //        try {
    //            String title = prettyPrompt("Title", Utils::validateNotEmpty);
    //            ProductCategory productCategory = prettyMenu("Category", ProductCategory.class);
    //            String description = prettyPrompt("Description");
    //            int price = prettyPromptCurrency("Price");
    //            int fidelityPoints = prettyPromptInt("Fidelity points", bonusPoints -> validateBonusFidelityPoints(bonusPoints, price));
    //            int quantity = prettyPromptInt("Quantity", amount -> validateNumberRange(amount, 0, Integer.MAX_VALUE));
    //            Seller seller = (Seller) unishop.getCurrentUser();
    //
    //            switch (productCategory) {
    //                case BookOrManual -> {
    //                    String author = prettyPrompt("Author", Utils::validateNotEmpty);
    //                    String editor = prettyPrompt("Publisher", Utils::validateNotEmpty);
    //                    LocalDate releaseDate = prettyPromptDate("Release date");
    //                    BookOrManualGenre genre = prettyMenu("Genre", BookOrManualGenre.class);
    //                    int edition = prettyPromptInt("Edition number (enter 0 if not applicable)");
    //                    int volume = prettyPromptInt("Volume number (enter 0 if not applicable)");
    //                    String isbn = prettyPrompt("ISBN", Utils::validateISBN);
    //                    product = new BookOrManual(price, quantity, title, description, seller, fidelityPoints, isbn, author, editor, genre, releaseDate, edition, volume);
    //                }
    //                case IT -> {
    //                    String brand = prettyPrompt("Brand name", Utils::validateNotEmpty);
    //                    String models = prettyPrompt("Model name", Utils::validateNotEmpty);
    //                    LocalDate releaseDate = prettyPromptDate("Release date");
    //                    ITCategory itCategory = prettyMenu("Sub-category", ITCategory.class);
    //                    product = new IT(price, quantity, title, description, seller, fidelityPoints, brand, models, releaseDate, itCategory);
    //                }
    //                case LearningResource -> {
    //                    String org = prettyPrompt("Organization", Utils::validateNotEmpty);
    //                    LocalDate releaseDate = prettyPromptDate("Release date");
    //                    LearningResourceType type = prettyMenu("Sub-category", LearningResourceType.class);
    //                    int edition = prettyPromptInt("Edition number (enter 0 if not applicable)");
    //                    int isbn = prettyPromptInt("ISBN");
    //                    product = new LearningResource(price, quantity, title, description, seller, fidelityPoints, isbn, org, releaseDate, type, edition);
    //                }
    //                case OfficeEquipment -> {
    //                    String brand = prettyPrompt("Brand name", Utils::validateNotEmpty);
    //                    String models = prettyPrompt("Model name", Utils::validateNotEmpty);
    //                    OfficeEquipmentCategory oeCategory = prettyMenu("Sub-category", OfficeEquipmentCategory.class);
    //                    product = new OfficeEquipment(price, quantity, title, description, seller, fidelityPoints, brand, models, oeCategory);
    //                }
    //                case StationeryArticle -> {
    //                    String brand = prettyPrompt("Brand name", Utils::validateNotEmpty);
    //                    String models = prettyPrompt("Model name", Utils::validateNotEmpty);
    //                    StationeryArticleCategory saCategory = prettyMenu("Sub-category", StationeryArticleCategory.class);
    //                    product = new StationeryArticle(price, quantity, title, description, seller, fidelityPoints, brand, models, saCategory);
    //                }
    //            }
    //
    //            unishop.updateCatalog();
    //            System.out.println("Product " + title + " added!");
    //            waitForKey();
    //        } catch (Exception e) {
    //            System.out.println(e.getMessage());
    //        }
    //    }
    //
    //    public static void displayPendingSellerOrders() {
    //        Seller seller = (Seller) unishop.getCurrentUser();
    //        List<Order> orders = seller.getOrdersSold().stream().filter(order -> order.getState().equals(OrderState.InProduction)).toList();
    //        clearConsole();
    //
    //        if (orders.isEmpty()) {
    //            System.out.println(prettify("No orders"));
    //            waitForKey();
    //            return;
    //        }
    //
    //        prettyPaginationMenu(orders, 2, "Select order to ship", (order) -> {
    //            System.out.println(prettify("--------------------"));
    //            System.out.println(prettify("Buyer username: " + order.getBuyer().getUsername()));
    //            System.out.println(prettify("State: " + order.getState()));
    //            System.out.println(prettify("Order date: " + order.getOrderDate()));
    //            System.out.println(prettify("Address: " + order.getAddress()));
    //            System.out.println(prettify("Number of products: " + order.getProducts().size()));
    //        }, (order) -> "Order of " + order.getBuyer().getUsername() + " - " + order.getOrderDate(), Client::displayOrderShipmentMenu);
    //    }
    //
    //    public static void displayOrderShipmentMenu(Order order) {
    //        clearConsole();
    //        if (order.getState() != OrderState.InProduction) {
    //            System.out.println(prettify("Order has already been shipped."));
    //            waitForKey();
    //            return;
    //        }
    //        displayOrder(order);
    //
    //        String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);
    //        String trackingNumber = prettyPrompt("Tracking number", Utils::validateNotEmpty);
    //        LocalDate expectedDeliveryDate = prettyPromptDate("Expected delivery date");
    //
    //        if (prettyPromptBool("Ship order?")) {
    //            order.setInTransit(shippingCompany, trackingNumber, expectedDeliveryDate);
    //            System.out.println("Order status updated");
    //        } else {
    //            System.out.println("Order status change cancelled");
    //        }
    //    }
    //
    //    public static void displayTickets() {
    //        User currentUser = unishop.getCurrentUser();
    //        ArrayList<Ticket> tickets = currentUser.getTickets();
    //        clearConsole();
    //
    //        if (tickets.isEmpty()) {
    //            System.out.println(prettify("No tickets"));
    //            waitForKey();
    //        } else {
    //            prettyPaginationMenu(tickets, 3, "Display ticket", (ticket) -> {
    //                System.out.println(prettify("--------------------"));
    //                System.out.println(prettify("Creation date: " + ticket.getCreationDate()));
    //                System.out.println(prettify("State: " + ticket.getState()));
    //                System.out.println(prettify("For order placed on: " + ticket.getOrder().getOrderDate()));
    //                System.out.println(prettify("Buyer: " + ticket.getBuyer().getUsername()));
    //                System.out.println(prettify("Seller: " + ticket.getSeller().getName()));
    //                System.out.println(prettify("Number of products in ticket: " + ticket.getProducts().size()));
    //            }, (ticket) -> "Ticket of " + ticket.getCreationDate(), Client::displayTicketActions);
    //        }
    //    }
    //
    //    private static void displayTicketActions(Ticket ticket) {
    //        if (unishop.getCurrentUser() instanceof Buyer) {
    //
    //            String[] options = {"Go back", "Create return shipment", "Confirm reception of replacement shipment"};
    //
    //            loop:
    //            while (true) {
    //                clearConsole();
    //                displayTicket(ticket);
    //
    //                // Setup action menu
    //                int answer = prettyMenu("Select action", options);
    //                switch (answer) {
    //                    case 0 -> {
    //                        break loop;
    //                    }
    //                    case 1 -> {
    //                        String trackingNumber = prettyPrompt("Tracking number of return shipment", Utils::validateNotEmpty);
    //                        LocalDate deliveryDate = prettyPromptDate("Expected delivery date");
    //                        String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);
    //                        ticket.createReturnShipment(trackingNumber, deliveryDate, shippingCompany);
    //                    }
    //                    case 2 -> {
    //                        boolean confirmation = prettyPromptBool("Do you really want to confirm the reception of the replacement shipment");
    //                        if (confirmation) {
    //                            ticket.getReplacementShipment().confirmDelivery();
    //                            ticket.updateState();
    //                        } else {
    //                            System.out.println(prettify("Action cancelled"));
    //                            waitForKey();
    //                        }
    //                    }
    //                }
    //            }
    //        } else {
    //            String[] options = {"Go back", "Set suggested solution", "Confirm reception of return shipment", "Set replacement product description", "Create replacement shipment"};
    //
    //            loop:
    //            while (true) {
    //                clearConsole();
    //                displayTicket(ticket);
    //
    //                // Setup action menu
    //                int answer = prettyMenu("Select action", options);
    //                switch (answer) {
    //                    case 0 -> {
    //                        break loop;
    //                    }
    //                    case 1 -> {
    //                        String suggestedSolution = prettyPrompt("Suggested solution", Utils::validateNotEmpty);
    //                        ticket.setSuggestedSolution(suggestedSolution);
    //                    }
    //                    case 2 -> {
    //                        boolean confirmation = prettyPromptBool("Do you really want to confirm the reception of the return shipment");
    //                        if (confirmation) {
    //                            ticket.getReturnShipment().confirmDelivery();
    //                            ticket.updateState();
    //                        } else {
    //                            System.out.println(prettify("Action cancelled"));
    //                            waitForKey();
    //                        }
    //                    }
    //                    case 3 -> {
    //                        String replacementProductDescription = prettyPrompt("Replacement product description", Utils::validateNotEmpty);
    //                        ticket.setReplacementProductDescription(replacementProductDescription);
    //                    }
    //                    case 4 -> {
    //                        String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);
    //                        String trackingNumber = prettyPrompt("Tracking number of replacement shipment", Utils::validateNotEmpty);
    //                        LocalDate expectedDeliveryDate = prettyPromptDate("Expected delivery date");
    //                        ticket.createReplacementShipment(trackingNumber, expectedDeliveryDate, shippingCompany);
    //                    }
    //                }
    //            }
    //        }
    //    }
    //
    //    private static void displayTicket(Ticket ticket) {
    //        clearConsole();
    //        System.out.println(prettify("Creation date: " + ticket.getCreationDate()));
    //        System.out.println(prettify("State: " + ticket.getState()));
    //        System.out.println(prettify("For order placed on: " + ticket.getOrder().getOrderDate()));
    //        System.out.println(prettify("Buyer: " + ticket.getBuyer().getUsername()));
    //        System.out.println(prettify("Seller: " + ticket.getSeller().getName()));
    //        System.out.println(prettify("Number of products in ticket: " + ticket.getProducts().size()));
    //        System.out.println(prettify("Cause of ticket: " + ticket.getCause()));
    //        System.out.println(prettify("Problem description: " + ticket.getProblemDescription()));
    //        System.out.println(prettify("Suggested solution: " + ticket.getSuggestedSolution()));
    //        System.out.println(prettify("Replacement product description: " + ticket.getReplacementProductDescription()));
    //
    //        if (ticket.getState().equals(TicketState.ReturnInTransit)) {
    //            System.out.println(prettify("Return shipment creation date: " + ticket.getReturnShipment().getCreationDate()));
    //            System.out.println(prettify("Return shipment tracking number: " + ticket.getReturnShipment().getTrackingNumber()));
    //        } else if (ticket.getState().equals(TicketState.ReplacementInTransit)) {
    //            System.out.println(prettify("Replacement shipment creation date: " + ticket.getReplacementShipment().getCreationDate()));
    //            System.out.println(prettify("Replacement shipment tracking number: " + ticket.getReplacementShipment().getTrackingNumber()));
    //        }
    //        waitForKey();
    //    }
    //
    //    public static void updateSellerInfo() {
    //        Seller seller = (Seller) unishop.getCurrentUser();
    //
    //        String[] options = new String[]{"Go back", "Name", "Password", "Email", "Phone number", "Address"};
    //        while (true) {
    //            clearConsole();
    //            int answer = prettyMenu("Select the information you'd like to change", options);
    //
    //            switch (answer) {
    //                case 0 -> {
    //                    return;
    //                }
    //                case 1 -> {
    //                    String newVal = prettyPrompt("Set a new name", Utils::validateName);
    //                    seller.setName(newVal);
    //                }
    //                case 2 -> {
    //                    while (true) {
    //                        String oldPassword = prettyPrompt("Enter old password", Utils::validateNotEmpty);
    //                        if (unishop.isPasswordMatching(oldPassword)) {
    //                            String newVal = prettyPrompt("Set a new password", Utils::validateNotEmpty);
    //                            seller.setPassword(newVal);
    //                            break;
    //                        } else {
    //                            System.out.println(prettify("Old password invalid"));
    //                            if (!prettyPromptBool("Try again?")) break;
    //                        }
    //                    }
    //                }
    //                case 3 -> {
    //                    String newVal = prettyPrompt("Set a new email address", Utils::validateEmail);
    //                    seller.setEmail(newVal);
    //                }
    //                case 4 -> {
    //                    String newVal = prettyPrompt("Set a new phone number", Utils::validatePhoneNumber);
    //                    seller.setPhoneNumber(newVal);
    //                }
    //                case 5 -> {
    //                    String newVal = prettyPrompt("Set a new address", Utils::validateNotEmpty);
    //                    seller.setAddress(newVal);
    //                }
    //            }
    //        }
    //    }
    //
    //    public static void displayProduct(Product product) {
    //        clearConsole();
    //        System.out.println(prettify("Title: " + product.getTitle()));
    //        System.out.println(prettify("Description: ") + product.getDescription());
    //        System.out.println(prettify("Category: ") + product.getCategory());
    //        System.out.println(prettify("Subcategory: ") + product.getSubCategory());
    //        System.out.println(prettify("Price : ") + product.getCost() / 100 + "." + product.getCost() % 100 + "$");
    //        System.out.println(prettify("Discount: ") + product.getDiscount() + "%");
    //        System.out.println(prettify("Quantity: ") + product.getQuantity());
    //        System.out.println(prettify("Fidelity Points: ") + product.getBonusFidelityPoints());
    //        System.out.println(prettify("Sold by: ") + product.getSeller().getName());
    //        System.out.println(prettify("Likes: ") + product.getFollowedBy().size());
    //        System.out.println(prettify("Commercialization date: ") + product.getCommercializationDate());
    //        waitForKey();
    //    }
    //
    //    private static void displayBuyerProductActions(Product product) {
    //        String[] options = {"Go back", "Toggle like", "Display reviews", "Add to cart"};
    //        while (true) {
    //            clearConsole();
    //            int answer = prettyMenu("Select action", options);
    //            switch (answer) {
    //                case 0 -> {
    //                    return;
    //                }
    //                case 1 -> {
    //                    Buyer buyer = (Buyer) unishop.getCurrentUser();
    //                    boolean wasLiked = buyer.toggleLike(product);
    //                    unishop.updateCatalog();
    //
    //                    if (wasLiked) {
    //                        System.out.println(prettify("Product successfully liked"));
    //                    } else {
    //                        System.out.println(prettify("Product successfully unliked"));
    //                    }
    //                }
    //                case 2 -> {
    //                    displayReviews(product);
    //                }
    //                case 3 -> {
    //                    int qty = prettyPromptInt("Quantity");
    //                    if (qty > product.getQuantity()) {
    //                        System.out.println(prettify("Insufficient product quantity in inventory"));
    //                    } else {
    //                        ((Buyer) unishop.getCurrentUser()).getCart().addProduct(product, qty);
    //                        System.out.println(prettify("Product successfully added to cart"));
    //                    }
    //                }
    //            }
    //
    //            if (!prettyPromptBool("New action?")) break;
    //        }
    //    }
    //
    //    private static void placeOrder() {
    //        Buyer buyer = (Buyer) unishop.getCurrentUser();
    //        Cart cart = buyer.getCart();
    //        clearConsole();
    //
    //        System.out.println(prettify("Payment form"));
    //        String shippingAddress = prettyPrompt("Shipping address");
    //
    //        int fidelityPointsUsed = 0;
    //        if (buyer.getFidelityPoints() > 0) {
    //            boolean doUsePoints = prettyPromptBool("You have " + buyer.getFidelityPoints() + " fidelity points. Do you want to use them?");
    //            if (doUsePoints) {
    //                int availablePointMoney = buyer.getFidelityPoints() * 2; // In cents
    //                if (availablePointMoney > cart.getCost()) {
    //                    fidelityPointsUsed = cart.getCost() / 2;
    //                } else {
    //                    fidelityPointsUsed = buyer.getFidelityPoints();
    //                }
    //
    //                int toPayWithMoney = cart.getCost() - fidelityPointsUsed * 2;
    //                System.out.println(prettify("Remaining to pay: " + toPayWithMoney / 100 + "." + toPayWithMoney % 100 + "$"));
    //            }
    //        }
    //
    //        String creditCardName = prettyPrompt("Credit card name");
    //
    //        String creditCardNumber;
    //        while (true) {
    //            creditCardNumber = prettyPrompt("Credit card number");
    //            if (creditCardNumber.length() == 16 && creditCardNumber.matches("\\d+")) {
    //                break;
    //            } else {
    //                System.out.println(prettify("Invalid credit card number"));
    //            }
    //        }
    //
    //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyy");
    //        YearMonth expirationDate;
    //        while (true) {
    //            try {
    //                expirationDate = YearMonth.parse(prettyPrompt("Expiration date MMYY"), formatter);
    //                break;
    //            } catch (DateTimeParseException e) {
    //                System.out.println(prettify("Invalid expiration date"));
    //            }
    //        }
    //
    //        String cvc;
    //        while (true) {
    //            cvc = prettyPrompt("CVC");
    //            if (cvc.length() == 3 && cvc.matches("\\d+")) {
    //                break;
    //            } else {
    //                System.out.println(prettify("Invalid CVC"));
    //            }
    //        }
    //
    //        // Confirm order
    //        boolean doOrder = prettyPromptBool("Do you want to place the order?");
    //
    //        if (!doOrder) {
    //            System.out.println("Cancelled order.");
    //            return;
    //        }
    //
    //        buyer.getCart().createOrder(buyer.getEmail(), buyer.getPhoneNumber(), shippingAddress, buyer.getAddress(), creditCardName, creditCardNumber, expirationDate, cvc, fidelityPointsUsed, 0);
    //
    //        System.out.println(prettify("Your order has been placed successfully"));
    //    }
    //
    //    private static void findBuyer() {
    //        loop:
    //        while (true) {
    //            clearConsole();
    //            String[] searchBy = {"Go Back", "Name", "Phone number", "email"};
    //            int search = prettyMenu("Search buyer by", searchBy);
    //
    //            ArrayList<Buyer> matchList = new ArrayList<>();
    //            ArrayList<String> matchListString = new ArrayList<>();
    //            switch (search) {
    //                case 0 -> {
    //                    break loop;
    //                }
    //                case 1 -> {
    //                    String name = prettyPrompt("Name").toLowerCase();
    //                    for (Buyer buyer : unishop.getBuyerList().values()) {
    //                        String fullId = (buyer.getFirstName() + " " + buyer.getLastName() + " " + buyer.getUsername()).toLowerCase();
    //                        if (fullId.contains(name)) {
    //                            matchList.add(buyer);
    //                            matchListString.add(buyer.getUsername());
    //                        }
    //                    }
    //                }
    //                case 2 -> {
    //                    String phoneNumber = prettyPrompt("Phone number");
    //                    for (Buyer buyer : unishop.getBuyerList().values()) {
    //                        if (buyer.getPhoneNumber().contains(phoneNumber)) {
    //                            matchList.add(buyer);
    //                            matchListString.add(buyer.getUsername());
    //                        }
    //                    }
    //                }
    //                case 3 -> {
    //                    String email = prettyPrompt("email").toLowerCase();
    //                    for (Buyer buyer : unishop.getBuyerList().values()) {
    //                        if (buyer.getEmail().contains(email)) {
    //                            matchList.add(buyer);
    //                            matchListString.add(buyer.getUsername());
    //                        }
    //                    }
    //                }
    //            }
    //
    //            matchListString.add("Go back");
    //            while (true) {
    //                if (matchList.isEmpty()) {
    //                    System.out.println("------------");
    //                    System.out.println(prettify("No match found"));
    //                    waitForKey();
    //                    break;
    //                }
    //                int index = prettyMenu("Select buyer", matchListString);
    //                if (index == matchListString.size() - 1) break;
    //
    //                Buyer buyer = matchList.get(index);
    //                displayBuyer(buyer);
    //            }
    //        }
    //    }
    //
    //    private static void findSeller() {
    //        loop:
    //        while (true) {
    //            clearConsole();
    //            String[] searchBy = {"Go back", "Name", "Address", "Phone number", "email"};
    //            int search = prettyMenu("Search seller by", searchBy);
    //
    //            ArrayList<Seller> matchList = new ArrayList<>();
    //            ArrayList<String> matchListString = new ArrayList<>();
    //            switch (search) {
    //                case 0 -> {
    //                    break loop;
    //                }
    //                case 1 -> {
    //                    String name = prettyPrompt("Name").toLowerCase();
    //                    for (Seller seller : unishop.getSellerList().values()) {
    //                        if (seller.getName().toLowerCase().contains(name)) {
    //                            matchList.add(seller);
    //                            matchListString.add(seller.getName());
    //                        }
    //                    }
    //                }
    //                case 2 -> {
    //                    String address = prettyPrompt("Address").toLowerCase();
    //                    for (Seller seller : unishop.getSellerList().values()) {
    //                        if (seller.getAddress().toLowerCase().contains(address)) {
    //                            matchList.add(seller);
    //                            matchListString.add(seller.getName());
    //                        }
    //                    }
    //                }
    //                case 3 -> {
    //                    String phoneNumber = prettyPrompt("Phone number");
    //                    for (Seller seller : unishop.getSellerList().values()) {
    //                        if (seller.getPhoneNumber().contains(phoneNumber)) {
    //                            matchList.add(seller);
    //                            matchListString.add(seller.getName());
    //                        }
    //                    }
    //                }
    //                case 4 -> {
    //                    String email = prettyPrompt("email").toLowerCase();
    //                    for (Seller seller : unishop.getSellerList().values()) {
    //                        if (seller.getEmail().contains(email)) {
    //                            matchList.add(seller);
    //                            matchListString.add(seller.getName());
    //                        }
    //                    }
    //                }
    //            }
    //
    //            matchListString.add("Go back");
    //            while (true) {
    //                if (matchList.isEmpty()) {
    //                    System.out.println("------------");
    //                    System.out.println(prettify("No match found"));
    //                    waitForKey();
    //                    break;
    //                }
    //                int index = prettyMenu("Select seller", matchListString);
    //                if (index == matchListString.size() - 1) break;
    //
    //                Seller seller = matchList.get(index);
    //                displaySeller(seller);
    //            }
    //        }
    //    }
    //
    //    private static void displayReviews(Product product) {
    //        clearConsole();
    //        ArrayList<Review> reviews = product.getReviews();
    //
    //        if (reviews.isEmpty()) {
    //            System.out.println(prettify("No reviews for this product"));
    //            waitForKey();
    //            return;
    //        }
    //
    //        // Print reviews in batches of 3
    //        int itemsPerPage = 3;
    //        for (int i = 0; i < reviews.size(); i += itemsPerPage) {
    //            clearConsole();
    //            int itemsOnPage = Math.min(itemsPerPage, reviews.size() - i);
    //
    //            System.out.println(prettify("Reviews " + (i + 1) + " to " + (i + itemsOnPage) + ":"));
    //            for (int j = i; j < i + itemsOnPage; j++) {
    //                Review review = reviews.get(j);
    //                Buyer author = review.getAuthor();
    //                System.out.println(prettify("--------------------"));
    //                System.out.println(prettify(author.getFirstName() + " " + author.getLastName() + " - " + review.getRating() + "/100"));
    //                System.out.println(prettify("Title: " + review.getTitle()));
    //                System.out.println(prettify(review.getContent()));
    //            }
    //
    //            if (!prettyPromptBool("See more reviews?")) break;
    //        }
    //    }
    //
    //    public static void displaySeller(Seller seller) {
    //        Buyer currentBuyer = (Buyer) unishop.getCurrentUser();
    //
    //        loop:
    //        while (true) {
    //            clearConsole();
    //            System.out.println(prettify("Name: " + seller.getName()));
    //            System.out.println(prettify("Email: " + seller.getEmail()));
    //            System.out.println(prettify("Address: " + seller.getAddress()));
    //            System.out.println(prettify("Phone number: " + seller.getPhoneNumber()));
    //            System.out.println(prettify("Number of products offered: " + seller.getProductsOffered().size()));
    //            System.out.println(prettify("Number of orders sold: " + seller.getOrdersSold().size()));
    //            System.out.println(prettify("Followed by you: " + currentBuyer.doesLike(seller)));
    //
    //            String[] options = {"Go back", "Toggle follow", "Display seller's products"};
    //            int answer = prettyMenu("Select action", options);
    //            switch (answer) {
    //                case 0 -> {
    //                    break loop;
    //                }
    //                case 1 -> {
    //                    currentBuyer.toggleLike(seller);
    //                    System.out.println(prettify("Successfully toggled follow"));
    //                    waitForKey();
    //                }
    //                case 2 -> displayProducts(seller.getProductsOffered());
    //            }
    //        }
    //    }
    //
    //    public static void displayProducts(ArrayList<Product> source) {
    //        while (true) {
    //            clearConsole();
    //
    //            ProductCategory selectedCategory = null;
    //            Enum<?> selectedSubCategory = null;
    //
    //            // Select category
    //            ArrayList<String> options = ProductCategory.getOptions();
    //            options.add("All");
    //            options.add("Main menu");
    //            int choice = prettyMenu("Categories", options);
    //            if (choice == options.size() - 1) break;
    //            if (choice != options.size() - 2) {
    //                selectedCategory = ProductCategory.values()[choice];
    //
    //                // Select subcategory
    //                ArrayList<String> subOptions = selectedCategory.getSubOptions();
    //                subOptions.add("All");
    //                subOptions.add("Main menu");
    //
    //                int subChoice = prettyMenu("Sub-Categories", subOptions);
    //                if (subChoice == subOptions.size() - 1) break;
    //                if (subChoice != subOptions.size() - 2) {
    //                    selectedSubCategory = selectedCategory.getEnum().getEnumConstants()[subChoice];
    //                }
    //            }
    //            // Get products that match category/subcategory
    //
    //            ArrayList<Product> matchedProducts = new ArrayList<>();
    //            ArrayList<String> matchedProductsString = new ArrayList<>();
    //            matchedProductsString.add("Back to categories");
    //            matchedProductsString.add("Back to main menu");
    //
    //            for (Product product : source) {
    //                if ((selectedCategory == null || product.getCategory().equals(selectedCategory)) && (selectedSubCategory == null || product.getSubCategory().equals(selectedSubCategory))) {
    //                    matchedProducts.add(product);
    //                    matchedProductsString.add(product.getTitle());
    //                }
    //            }
    //
    //            if (matchedProducts.isEmpty()) {
    //                System.out.println("------------");
    //                System.out.println(prettify("No match found"));
    //                waitForKey();
    //                continue;
    //            }
    //
    //            int answer = prettyMenu("Select a product", matchedProductsString);
    //
    //            // Check if we go back
    //            if (answer == 0) {
    //                continue;
    //            } else if (answer == 1) {
    //                break;
    //            }
    //
    //            // Get product
    //            Product product = matchedProducts.get(answer - 2);
    //            displayProduct(product);
    //            displayBuyerProductActions(product);
    //
    //            if (!prettyPromptBool("Keep browsing product?")) break;
    //        }
    //    }
    //
    //    public static void displayBuyer(Buyer buyer) {
    //        Buyer currentBuyer = (Buyer) unishop.getCurrentUser();
    //
    //        loop:
    //        while (true) {
    //            clearConsole();
    //            System.out.println(prettify("Username: " + buyer.getUsername()));
    //            System.out.println(prettify("Full name: ") + buyer.getFirstName() + " " + buyer.getLastName());
    //            System.out.println(prettify("Followed by you: " + currentBuyer.doesLike(buyer)));
    //            System.out.println(prettify("Follows you: " + buyer.doesLike(currentBuyer)));
    //            System.out.println(prettify("Number of fidelity points: " + buyer.getFidelityPoints()));
    //            System.out.println(prettify("Number of reviews written: " + buyer.getReviewsWritten().size()));
    //            System.out.println(prettify("Number of reviews liked: " + buyer.getReviewsLiked().size()));
    //            System.out.println(prettify("Number of products liked: " + buyer.getProductsLiked().size()));
    //            System.out.println(prettify("Number of order bought: " + buyer.getOrders().size()));
    //
    //            String[] options = {"Go back", "Toggle follow"};
    //            int answer = prettyMenu("Select action", options);
    //            switch (answer) {
    //                case 0 -> {
    //                    break loop;
    //                }
    //                case 1 -> {
    //                    currentBuyer.toggleLike(buyer);
    //                    System.out.println(prettify("Successfully toggled follow"));
    //                    waitForKey();
    //                }
    //            }
    //        }
    //    }
}
