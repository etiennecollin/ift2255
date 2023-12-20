/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.model.AuthenticationModel;
import com.etiennecollin.ift2255.clientCLI.model.ProfileModel;
import com.etiennecollin.ift2255.clientCLI.model.data.Database;
import com.etiennecollin.ift2255.clientCLI.model.data.JavaSerializedDatabase;
import com.etiennecollin.ift2255.clientCLI.views.MainMenu;
import com.etiennecollin.ift2255.clientCLI.views.ViewRenderer;

public class UniShop {
    private static UniShop _instance;
    private Database db;
    private ViewRenderer renderer;
    private AuthenticationModel auth;
    private ProfileModel profile;
    private AuthenticationController authController;
    private ProfileController profileController;

    private UniShop() {
        this.db = new JavaSerializedDatabase();
        this.auth = new AuthenticationModel(db);
        this.profile = new ProfileModel(db);

        this.renderer = new ViewRenderer();

        this.authController = new AuthenticationController(renderer, auth);
        this.profileController = new ProfileController(renderer, profile);

        this.renderer.addNextView(new MainMenu(authController), true);
//        this.renderer.renderViews();

//        this.buyerList = new HashMap<>();
//        this.sellerList = new HashMap<>();
//        updateCatalog();
    }

    public static UniShop getInstance() {
        if (_instance == null) {
            _instance = new UniShop();
        }
        return _instance;
    }

    public ViewRenderer getRenderer() {
        return renderer;
    }

    public AuthenticationModel getAuth() {
        return auth;
    }

    public ProfileModel getProfile() {
        return profile;
    }

    public AuthenticationController getAuthController() {
        return authController;
    }

    public ProfileController getProfileController() {
        return profileController;
    }

//    public void updateCatalog() {
//        this.catalog = new ArrayList<>();
//        sellerList.forEach((sellerName, seller) -> this.catalog.addAll(seller.getProductsOffered()));
//    }
//
//    /**
//     * Loads user data from the specified file path.
//     *
//     * @param path The file path from which user data needs to be loaded.
//     */
//    @SuppressWarnings("unchecked")
//    public void loadUserList(String path) {
//        try (FileInputStream file = new FileInputStream(path)) {
//            try (ObjectInputStream input = new ObjectInputStream(file)) {
//                this.buyerList = (HashMap<String, Buyer>) input.readObject();
//                this.sellerList = (HashMap<String, Seller>) input.readObject();
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            // throw new RuntimeException("Could not load the user list");
//        }
//        updateCatalog();
//    }
//
//    public ArrayList<Product> getCatalog() {
//        return catalog;
//    }
//
//    /**
//     * Saves the current state of buyer and seller lists to the specified file path.
//     *
//     * @param path The file path to which user data needs to be saved.
//     */
//    public void saveUserList(String path) {
//        File file = new File(path);
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            throw new RuntimeException("Could not create the save file");
//        }
//
//        try (FileOutputStream outputFile = new FileOutputStream(file, false)) {
//            try (ObjectOutputStream output = new ObjectOutputStream(outputFile)) {
//                output.writeObject(getBuyerList());
//                output.writeObject(getSellerList());
//                output.flush();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Could not save the user list");
//        }
//    }
//
//    public HashMap<String, Buyer> getBuyerList() {
//        return buyerList;
//    }
//
//    public HashMap<String, Seller> getSellerList() {
//        return sellerList;
//    }
//
//    public User getCurrentUser() {
//        return currentUser;
//    }
//
//    public void setCurrentUser(User currentUser) {
//        this.currentUser = currentUser;
//    }
//
//    public void addUser(Buyer buyer) throws IllegalArgumentException {
//        if (buyerList.containsKey(buyer.getUsername().toLowerCase())) {
//            throw new IllegalArgumentException("This buyer already exists");
//        }
//        this.buyerList.put(buyer.getUsername().toLowerCase(), buyer);
//    }
//
//    public void addUser(Seller seller) throws IllegalArgumentException {
//        if (sellerList.containsKey(seller.getName().toLowerCase())) {
//            throw new IllegalArgumentException("This seller already exists");
//        }
//        this.sellerList.put(seller.getName().toLowerCase(), seller);
//        updateCatalog();
//    }
//
//    public void removeUser(Buyer buyer) {
//        if (!buyerList.containsKey(buyer.getUsername().toLowerCase())) {
//            throw new IllegalArgumentException("This buyer does not exist");
//        }
//        this.buyerList.remove(buyer.getUsername().toLowerCase());
//    }
//
//    public void removeUser(Seller seller) {
//        if (!sellerList.containsKey(seller.getName().toLowerCase())) {
//            throw new IllegalArgumentException("This seller does not exist");
//        }
//        this.sellerList.remove(seller.getName().toLowerCase());
//        updateCatalog();
//    }
//
//    public void loginBuyer(String username, String password) throws IllegalArgumentException {
//        Buyer buyer = buyerList.get(username.toLowerCase());
//
//        // Check that a buyer is found
//        if (buyer == null) {
//            throw new IllegalArgumentException("Incorrect username");
//        }
//
//        if (buyer.getPassword() != password.hashCode()) {
//            throw new IllegalArgumentException("Incorrect password");
//        }
//
//        this.setCurrentUser(buyer);
//    }
//
//    public void loginSeller(String name, String password) throws IllegalArgumentException {
//        Seller seller = sellerList.get(name.toLowerCase());
//
//        if (seller == null) {
//            throw new IllegalArgumentException("Incorrect name");
//        }
//
//        if (seller.getPassword() != password.hashCode()) {
//            throw new IllegalArgumentException("Incorrect password");
//        }
//
//        this.setCurrentUser(seller);
//    }
//
//    public boolean isPasswordMatching(String password) {
//        return this.currentUser.getPassword() == password.hashCode();
//    }
}
