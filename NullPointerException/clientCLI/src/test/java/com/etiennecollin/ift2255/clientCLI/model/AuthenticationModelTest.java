/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.model.data.JavaSerializedDatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The AuthenticationModelTest class contains unit tests for the AuthenticationModel class, focusing on the authenticateBuyer,
 * authenticateSeller, isBuyerNameAvailable, and isSellerNameAvailable methods.
 * It ensures the proper functionality of buyer and seller authentication, as well as username availability checks.
 */
class AuthenticationModelTest {
    /**
     * Tests the authenticateBuyer method in the AuthenticationModel class.
     */
    @Test
    void testAuthenticateBuyer() {
        // MOCK DATA FOR UNIT TESTS
        AuthenticationModel am = new AuthenticationModel(new JavaSerializedDatabase());
        am.registerNewBuyer("username", "abc123", "test", "test", "test@test.com", "8888888888", "test");

        // ------------------ UNIT TESTS ----------------------
        assertEquals(new OperationResult(true, ""), am.authenticateBuyer("username", "abc123"));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateBuyer("username", ""));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateBuyer("", "abc123"));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateBuyer("username3", "abc123"));
    }

    /**
     * Tests the authenticateSeller method in the AuthenticationModel class.
     */
    @Test
    void testAuthenticateSeller() {
        // MOCK DATA FOR UNIT TESTS
        AuthenticationModel am = new AuthenticationModel(new JavaSerializedDatabase());
        am.registerNewSeller("Jane Doe", "abc123", "test@test.com", "8888888888", "test");
        am.registerNewSeller("Jane Bell", "abc12", "test@test.com", "8888888888", "test");

        //-----------------UNIT TESTS-----------------------

        assertEquals(new OperationResult(true, ""), am.authenticateSeller("Jane Doe", "abc123"));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateSeller("Jane Doe", ""));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateSeller("Jane Bell", "abc123"));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateSeller("", "abc123"));
    }

    /**
     * Tests the isBuyerNameAvailable method in the AuthenticationModel class.
     */
    @Test
    void testIsBuyerNameAvailable() {
        // MOCK DATA FOR UNIT TESTS
        AuthenticationModel am = new AuthenticationModel(new JavaSerializedDatabase());
        am.registerNewBuyer("username", "abc123", "test", "test", "test@test.com", "8888888888", "test");

        //-----------------UNIT TESTS-----------------------

        assertFalse(am.isBuyerNameAvailable("username"), "BUYER: Taken username should appear taken");
        assertTrue(am.isBuyerNameAvailable(""), "BUYER: Empty username shouldn't appear taken");
        assertTrue(am.isBuyerNameAvailable("username-post"), "BUYER: if a username is a substring of actual username, it shouldn't appear taken");
        assertTrue(am.isBuyerNameAvailable("pref-username"), "BUYER: if a username is a substring of actual username, it shouldn't appear taken");
    }

    /**
     * Tests the isSellerNameAvailable method in the AuthenticationModel class.
     */
    @Test
    void testIsSellerNameAvailable() {
        // MOCK DATA FOR UNIT TESTS
        AuthenticationModel am = new AuthenticationModel(new JavaSerializedDatabase());
        am.registerNewSeller("Jane Doe", "abc123", "test@test.com", "8888888888", "test");
        am.registerNewSeller("Jane Bell", "abc12", "test@test.com", "8888888888", "test");

        //-----------------UNIT TESTS-----------------------

        assertFalse(am.isSellerNameAvailable("Jane Doe"), "SELLER: matching name should return true");
        assertTrue(am.isSellerNameAvailable(""), "SELLER: Empty string in username should return true");
        assertTrue(am.isSellerNameAvailable("Jane Doe-post"), "SELLER: if a username is a substring of actual username, it shouldn't appear taken");
        assertTrue(am.isSellerNameAvailable("pref-Jane Doe"), "SELLER: if a username is a substring of actual username, it shouldn't appear taken");
    }
}