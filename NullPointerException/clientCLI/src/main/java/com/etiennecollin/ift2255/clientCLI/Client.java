
/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import com.etiennecollin.ift2255.clientCLI.classes.*;
import com.etiennecollin.ift2255.clientCLI.classes.products.BookOrManual;
import com.etiennecollin.ift2255.clientCLI.classes.products.BookOrManualGenre;
import com.etiennecollin.ift2255.clientCLI.classes.products.Product;
import com.etiennecollin.ift2255.clientCLI.classes.products.ProductCategory;

import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The `Client` class serves as the main entry point for the client command-line interface (CLI).
 */
public class Client {
    public static final String savePath;
    public static final UniShop unishop = new UniShop();
    // Hardcoded for prototype
    static String[] sellerMenu = {"Offer product", "Modify order status", "Manage issues", "Update account information", "Log out"};
    static String[] buyerMenu = {"Catalog", "Search a product", "My cart", "My activities", "Find a seller", "My orders", "Update account information", "Log out"};
    static ArrayList<String> shoppingCart = new ArrayList<>();
    static ArrayList<String> likedProducts = new ArrayList<>();
    static String[] categories = {"Books and manuals", "Learning ressources", "Stationery", "Hardware", "Office equipment"};
    static String[] productListDataBase = {"Computer", "Manual", "Utilities"};
    static String[] addToCartMenu = new String[productListDataBase.length + 1];
    static ArrayList<String> sellersUsername = new ArrayList<>();
    static ArrayList<ArrayList<String>> ordersPlaced = new ArrayList<>();

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
                    unishop.addUser((Buyer) user);
                } else if (answer == 1) {
                    user = sellerCreationForm();
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
        loop:
        while (true) {
            int buyerAnswer = prettyMenu("Main menu", buyerMenu);
            switch (buyerAnswer) {
                case 0 -> displayCatalog();
                case 1 -> searchProduct();
                case 2 -> displayCart(unishop);
                case 3 -> displayActivities();
                case 4 -> findSeller();
                case 5 -> displayOrders();
                case 6 -> updateBuyerInfo();
                case 7 -> {
                    logout(unishop);
                    break loop;
                }
            }
        }
    }

    // TODO complete
    public static void sellerMenu() {
        loop:
        while (true) {
            int answer = prettyMenu("Main menu", sellerMenu);
            switch (answer) {
                case 0 -> addProduct();
                case 1 -> changeOrderStatus();
                case 2 -> manageProblems();
                case 3 -> updateSellerInfo();
                case 4 -> {
                    logout(unishop);
                    break loop;
                }
            }
        }
    }

    private static Buyer buyerCreationForm() {
        clearConsole();
        String firstName = prettyPrompt("First name");
        String lastName = prettyPrompt("Last name");
        String username = prettyPrompt("Username");
        String email = prettyPrompt("Email");
        String password = prettyPrompt("Password");
        String phoneNumber = prettyPrompt("Phone number");
        String address = prettyPrompt("Shipping address");

        return new Buyer(firstName, lastName, username, email, phoneNumber, address, password);
    }

    private static Seller sellerCreationForm() {
        clearConsole();
        String name = prettyPrompt("Name");
        String email = prettyPrompt("Email");
        String password = prettyPrompt("Password");
        String phoneNumber = prettyPrompt("Phone number");
        String address = prettyPrompt("Shipping address");

        return new Seller(name, email, phoneNumber, address, password);
    }

    // TODO
    private static void displayCatalog() {
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
            ArrayList<Product> matchedProducts = unishop.getCatalog().stream().filter((product -> product.getCategory().equals(selectedCategory) && product.getSubCategory().equals(selectedSubCategory))).collect(Collectors.toCollection(ArrayList::new));

            // Select one of these products
            Product product = prettyMenuT("Select a product", matchedProducts);

            // TODO instead of adding to cart, we should display the product page. From there we can add it to cart.
            ((Buyer) unishop.getCurrentUser()).getCart().addProduct(product, 1);
            // displayProductPage(product);

            System.out.println(prettify("Item successfully added to cart"));

            boolean tryAgain = prettyPromptBool("Keep browsing product?");
            if (!tryAgain) break;
        }
    }

    // TODO
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
        int choice = prettyMenu("Resultats for \"" + keyWord + "\"", searchResult);

        if (!(choice == searchResult.size() - 1)) {
            String[] productMenu = {"Like the product", "Add to cart", "Main Menu"};
            int answer = prettyMenu(searchResult.get(choice), productMenu);

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

    // TODO
    public static void displayCart(UniShop unishop) {
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
        boolean placeOrder = prettyPromptBool("Place an order?");
        if (placeOrder) {
            paymentForm(unishop);
            ordersPlaced.add(shoppingCart);
            System.out.println("Your order has been placed successfully");
        }
    }

    // General metrics
    // TODO
    public static void displayActivities() {
        System.out.println(prettify("My activities:"));
        System.out.println(prettify("Total orders: " + ordersPlaced.size()));
        System.out.println(prettify("Total likes: " + likedProducts.size()));
    }

    // For prototype only
    // TODO
    private static void findSeller() {
        String[] searchBy = {"Name", "Address", "Article category"};
        int search = prettyMenu("Search by", searchBy);

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
                int cat = prettyMenu("Categories", catChoice);
            }
        }
        displaySellers();
    }

    // TODO
    public static void displayOrders() {
        System.out.println(prettify("Your orders: "));
        for (int i = 0; i < ordersPlaced.size(); i++) {
            System.out.println(prettify("Order " + (i + 1) + ": "));
            for (int j = 0; j < ordersPlaced.get(i).size(); j++) {
                System.out.println(prettify(ordersPlaced.get(i).get(j)));
            }
        }

        int answer = prettyMenu("Order menu", new String[]{"Confirm an order", "Signal an issue", "Back"});
        switch (answer) {
            case 0 -> {
                if (ordersPlaced.isEmpty()) {
                    System.out.println("No placed orders");
                } else {
                    int orderToConfirm = Utils.prettyMenu("What order do you want to confirm?", ordersPlaced, "Order");
                    System.out.println("Order " + orderToConfirm + " is successfully confirmed");
                }
            }
            case 1 -> {
                if (ordersPlaced.isEmpty()) {
                    System.out.println("No placed orders");
                } else {
                    int orderToSignal = Utils.prettyMenu("What order do you want to signal?", ordersPlaced, "Order");
                    System.out.println("Order " + orderToSignal + " is successfully signaled");
                }
            }
        }
    }

    // TODO
    public static void updateBuyerInfo() {
        String[] updateInfoMenu = new String[]{"First name", "Last name", "Password", "Email", "Phone number", "Shipping address", "Main menu"};
        while (true) {
            int menuIdx = prettyMenu("Select the information you'd like to change", updateInfoMenu);

            switch (menuIdx) {
                case 0 -> prettyPrompt("Set a new first name");
                case 1 -> prettyPrompt("Set a new last name");
                case 2 -> prettyPrompt("Set a new password");
                case 3 -> prettyPrompt("Set a new email address");
                case 4 -> prettyPrompt("Set a new phone number");
                case 5 -> prettyPrompt("Set a new shipping address");
                case 6 -> {
                    return;
                }
            }
        }
    }

    // TODO
    public static void addProduct() {
        String title = prettyPrompt("Title");
        ProductCategory productCategory = prettyMenu("Category", ProductCategory.class);
        String description = prettyPrompt("Description");
        String brandName = prettyPrompt("Brand name");
        String modelName = prettyPrompt("Model name");
        int quantity = prettyPromptInt("Quantity");
        float price = prettyPromptCurrency("Price");

        System.out.println("Product " + title + " added!");
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

        String shippingCompany = prettyPromptValidated("Shipping company", (s) -> !s.isEmpty());
        String trackingId = prettyPromptValidated("Tracking ID", (s) -> !s.isEmpty());

        System.out.println("Order status updated!");
    }

    // TODO
    public static void manageProblems() {
        String[] problemList = new String[]{"#123 - Order not received", "Main menu"};

        int problemIdx = prettyMenu("Select a problem", problemList);
        if (problemIdx == problemList.length - 1) {
            return;
        }

        System.out.println("#123 - Order not received");
        System.out.println("I put in an order 5 minutes ago and I still have not received it!!!");

        String[] solutionTypes = new String[]{"Refund", "Replace", "Repair"};
        int solution = prettyMenu("Select a solution", solutionTypes);
        String description = prettyPrompt("Provide solution details to the buyer");
        // set new problem status

        System.out.println("Problem response sent!");
    }

    // TODO
    public static void updateSellerInfo() {
        String[] updateInfoMenu = new String[]{"Password", "Email", "Phone number", "Shipping address", "Main menu"};
        while (true) {
            int menuIdx = prettyMenu("Select the information you'd like to change", updateInfoMenu);

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

    // TODO
    private static void paymentForm(UniShop unishop) {
        Buyer buyer = (Buyer) unishop.getCurrentUser();
        Cart cart = buyer.getCart();

        System.out.println(prettify("Paiement form"));
        String shippingAddress = prettyPrompt("Shipping address");

        if (buyer.getFidelityPoints() > 0) {
            boolean answer = prettyPromptBool("You have " + buyer.getFidelityPoints() + " fidelity points. Do you want to use them?");
            if (answer) {
                // cart.setCost(cart.getCost() - buyer.getFidelityPoints()*0.02)//TODO : setter pour cart cost
                buyer.setFidelityPoints(0);
                prettify("Your new total is: " + cart.getCost());
            }
        }

        String creditCardName = prettyPrompt("Credit card name");
        int creditCardNumber = Integer.parseInt(prettyPrompt("Credit card number"));
        int expirationDate = Integer.parseInt(prettyPrompt("Expiration date MMYY"));
        int cvc = Integer.parseInt(prettyPrompt("CVC"));

        buyer.getCart().createOrder(buyer.getEmail(), Integer.parseInt(buyer.getPhoneNumber()), shippingAddress, buyer.getAddress(), creditCardName, creditCardNumber, expirationDate, cvc);
    }

    // TODO
    public static void displaySellers() {
        for (String seller : sellersUsername) {
            System.out.println(prettify(seller));
        }
    }
}



