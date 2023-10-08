
/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import java.util.ArrayList;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The `Client` class serves as the main entry point for the client command-line interface (CLI).
 */
public class Client {
    // Hardcoded for prototype
    static String[] sellerMenu = {"Offer product", "Modify order status", "Manage issues", "Update account information", "Disconnect"};
    static String[] buyerMenu = {"Catalog", "Search a product", "My cart", "My activities", "Find a seller", "My orders", "Disconnect"};
    static ArrayList<String> shoppingCart = new ArrayList<>();
    static ArrayList<String> likedProducts = new ArrayList<>();
    static String[] categories = {"Books and manuals", "Learning ressources", "Stationery", "Hardware", "Office equipment"};
    static String[] productListDataBase = {"Computer", "Manual", "Utilities"};
    static String[] addToCartMenu = new String[productListDataBase.length + 1];
    static ArrayList<String> sellersUsername = new ArrayList<>();
    static ArrayList<ArrayList<String>> ordersPlaced = new ArrayList<>();

    /**
     * The main method of the `Client` class, which is the entry point for the CLI application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {

        /* String[] buyerMenu = {"Product catalog", "Search product", "Review a product", "Cart", "Order", "Review previous orders", "Confirm order reception", "Signal product issue", "Return/Exchange", "My activities", "Find seller", "Disconnect"};*/
        // for prototype
        sellersUsername.add("sellerUsernameExample");

        // Make productListDataBase = addToCartMenu
        System.arraycopy(productListDataBase, 0, addToCartMenu, 0, productListDataBase.length);

        // last elm = back menu
        addToCartMenu[addToCartMenu.length - 1] = "Back";

        System.out.println(prettify("Welcome to UniShop"));

        String[] loginMenu = {"Login", "Register"};
        String answer = prettyMenu("Login menu", loginMenu);

        UserRole userRole;
        if (answer.equals(loginMenu[0])) {
            userRole = loginForm();
        } else {
            userRole = createAccount();
        }

        if (userRole == UserRole.Buyer) {
            buyerMenu();
        } else if (userRole == UserRole.Seller) {
            sellerMenu();
        }
    }

    private static UserRole loginForm() {
        System.out.println(prettify("Login menu"));
        while (true) {
            String username = prettyPrompt("Username");
            String password = prettyPrompt("Password");

            if (!true) { // TODO add user info validation
                System.out.println(prettify("The username or password is incorrect"));
                continue;
            }
            break;
        }
        System.out.println(prettify("Successfully logged in"));
        return UserRole.Buyer;
    }

    private static UserRole createAccount() {
        UserRole role = prettyMenu("Choose a menu to display", UserRole.class);

        if (role == UserRole.Buyer) {
            buyerCreationForm();
        } else if (role == UserRole.Seller) {
            sellerCreationForm();
        }

        return role;
    }

    public static void buyerMenu() {
        boolean disconnect = false;
        do {
            int buyerAnswer = prettyMenuInt("Main menu", buyerMenu);
            switch (buyerAnswer) {
                case 0 -> displayCatalog();
                case 1 -> searchProduct();
                case 2 -> displayCart();
                case 3 -> displayActivities();
                case 4 -> findSeller();
                case 5 -> {
                // displayOrders();
                }
                case 6 -> disconnect = true;
            }
        } while (!disconnect);
        System.out.println(prettify("You have been successfully disconnected"));
    }

    public static void sellerMenu() {
        boolean disconnect = false;

        do {
            int answer = prettyMenuInt("Main menu", sellerMenu);
            switch (answer) {
                case 0 -> addProduct();
                case 1 -> changeOrderStatus();
                case 2 -> manageProblems();
                case 3 -> updateSellerInfo();
                case 4 -> disconnect = true;
            }
        } while (!disconnect);

        System.out.println(prettify("You have been successfully disconnected"));
    }

    private static void buyerCreationForm() { // TODO return buyer
        String firstName = prettyPrompt("First name");
        String lastName = prettyPrompt("Last name");
        String username = prettyPrompt("Username");
        String password = prettyPrompt("Password");
        String email = prettyPrompt("Email");
        String phoneNumber = prettyPrompt("Phone number");
        String address = prettyPrompt("Shipping address");

        System.out.println(prettify("Successfully registered"));
    }

    private static void sellerCreationForm() { // TODO return seller
        String username = prettyPrompt("Username");
        String password = prettyPrompt("Password");
        String email = prettyPrompt("Email");
        String phoneNumber = prettyPrompt("Phone number");
        String address = prettyPrompt("Shipping address");

        System.out.println(prettify("Successfully registered"));
    }

    private static void displayCatalog() {
        String[] catChoice = {"Books and manuals", "Learning ressources", "Stationery", "Hardware", "Office equipment", "Main menu"};
        while (true) { // 5 is the option for Main Menu
            // for prototype only
            int choice = prettyMenuInt("Categories", catChoice);
            if (choice == catChoice.length - 1) break;

            int addToCart = prettyMenuInt("Add to cart", addToCartMenu);

            if (addToCart != (addToCartMenu.length - 1)) {
                shoppingCart.add(addToCartMenu[addToCart]);
                System.out.println(prettify("Item successfully added to cart."));
                boolean answer = prettyYesNo("Keep browsing product?");
                if (!answer) break;         // 5 goes back to main menu
            }
        }
    }

    private static void searchProduct() {
        ArrayList<String> searchResult = new ArrayList<>();
        String keyWord = prettyPrompt("Search");

        // search for result
        for (String item : productListDataBase) {
            int index = item.indexOf(keyWord);
            if (!(index == -1)) {
                searchResult.add(item);
            }
        }
        searchResult.add("Main menu");
        // display result
        int choice = prettyMenuInt("Resultats for \"" + keyWord + "\"", searchResult);

        if (!(choice == searchResult.size() - 1)) {
            String[] productMenu = {"Like the product", "Add to cart", "Main Menu"};
            int answer = prettyMenuInt(searchResult.get(choice), productMenu);

            switch (answer) {
                case 0 -> {
                    likedProducts.add(searchResult.get(choice));
                    System.out.println(prettify("Item successfully liked"));
                }
                case 1 -> {
                    shoppingCart.add(searchResult.get(choice));
                    System.out.println(prettify("Item successfully added to cart."));
                }
            }
        }
    }

    public static void displayCart() {
        System.out.println(prettify("My cart: "));
        if (shoppingCart.isEmpty()) {
            System.out.println(prettify("Empty cart"));
        } else {
            for (String item : shoppingCart) {
                System.out.println(prettify(item));
            }
        }
        // Fictional amount for prototype only.
        System.out.println(prettify("Total: 1000$"));
        boolean placeOrder = prettyYesNo("Place an order?");
        if (placeOrder) {
            ordersPlaced.add(shoppingCart);
            System.out.println("Your order has been placed successfully");
        }
    }

    // General metrics
    public static void displayActivities() {
        System.out.println(prettify("My activities:"));
        System.out.println(prettify("Total orders: " + ordersPlaced.size()));
        System.out.println(prettify("Total likes: " + likedProducts.size()));
    }

    // For prototype only
    private static void findSeller() {
        String[] searchBy = {"Name", "Address", "Article category"};
        int search = prettyMenuInt("Search by", searchBy);

        switch (search) {
            case 0 -> { // Name
                String name = prettyPrompt("Search by name");
                /*nameSearch(name);*/
            }
            case 1 -> { // Address
                String address = prettyPrompt("Search by address");
                /*addressSearch(address);*/
            }
            case 2 -> { // Article category
                String[] catChoice = {"Books and manuals", "Learning ressources", "Stationery", "Hardware", "Office equipment", "Main menu"};
                int cat = prettyMenuInt("Categories", catChoice);
            }
        }
        displaySellers();
    }

    public static void addProduct() {
        String title = prettyPrompt("Title");
        String category = prettyMenu("Category", categories);
        String description = prettyPrompt("Description");
        String brandName = prettyPrompt("Brand name");
        String modelName = prettyPrompt("Model name");
        int quantity = prettyPromptInt("Quantity");
        float price = prettyPromptCurrency("Price");

        System.out.println("Product " + title + " added!");
    }

    public static void changeOrderStatus() {
        String[] orderMenu = new String[]{"#123 - Pending Seller", "Main menu"};
        int orderIdx = prettyMenuInt("Select the order to update", orderMenu);
        if (orderIdx == orderMenu.length - 1) { // last index returns to main menu
            return;
        }

        String[] orderStatusMenu = new String[]{"In transit", "Main menu"};
        int statusIdx = prettyMenuInt("Select new status", orderStatusMenu);
        if (statusIdx == orderStatusMenu.length - 1) { // last index returns to main menu
            return;
        }

        String shippingCompany = prettyPromptValidated("Shipping company", (s) -> !s.isEmpty());
        String trackingId = prettyPromptValidated("Tracking ID", (s) -> !s.isEmpty());

        System.out.println("Order status updated!");
    }

    public static void manageProblems() {
        String[] problemList = new String[]{"#123 - Order not received", "Main menu"};

        int problemIdx = prettyMenuInt("Select a problem", problemList);
        if (problemIdx == problemList.length - 1) {
            return;
        }

        System.out.println("#123 - Order not received");
        System.out.println("I put in an order 5 minutes ago and I still have not received it!!!");

        String[] solutionTypes = new String[]{"Refund", "Replace", "Repair"};
        String solution = prettyMenu("Select a solution", solutionTypes);
        String description = prettyPrompt("Provide solution details to the buyer");
        // set new problem status

        System.out.println("Problem response sent!");
    }

    public static void updateSellerInfo() {
        String[] updateInfoMenu = new String[]{"Password", "Email", "Phone number", "Shipping address", "Main menu"};
        while (true) {
            int menuIdx = prettyMenuInt("Select the information you'd like to change", updateInfoMenu);

            switch (menuIdx) {
                case 0 -> prettyPrompt("Set a new password");
                case 1 -> prettyPrompt("Set a new email address");
                case 2 -> prettyPrompt("Set a new phone number");
                case 3 -> prettyPrompt("Set a new shipping address");
                case 4 -> {
                    return;
                }
            }
        }
    }

    public static void displaySellers() {
        for (String seller : sellersUsername) {
            System.out.println(prettify(seller));
        }
    }
}

