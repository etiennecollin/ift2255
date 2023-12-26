/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.model;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.model.data.JavaSerializedDatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationModelTest {

    @Test
    void testAuthenticateBuyer() {
        //Mock datas for unit tests
        AuthenticationModel am = new AuthenticationModel(new JavaSerializedDatabase());
        am.registerNewBuyer("username", "abc123", "test", "test", "test@test.com", "8888888888", "test");

        // ------------------ UNIT TESTS ----------------------
        assertEquals(new OperationResult(true, ""), am.authenticateBuyer("username", "abc123"));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateBuyer("username", ""));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateBuyer("", "abc123"));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateBuyer("username3", "abc123"));

    }

    @Test
    void testAuthenticateSeller(){
        //Mock datas for unit tests
        AuthenticationModel am = new AuthenticationModel(new JavaSerializedDatabase());
        am.registerNewSeller("Jane Doe", "abc123", "test@test.com", "8888888888", "test");
        am.registerNewSeller("Jane Bell", "abc12", "test@test.com", "8888888888", "test");

        //-----------------UNIT TESTS-----------------------

        assertEquals(new OperationResult(true, ""), am.authenticateSeller("Jane Doe", "abc123"));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateSeller("Jane Doe", ""));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateSeller("Jane Bell", "abc123"));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), am.authenticateSeller("", "abc123"));

    }

    @Test
    void testIsBuyerNameAvailable(){
        //Mock datas for unit tests
        AuthenticationModel am = new AuthenticationModel(new JavaSerializedDatabase());
        am.registerNewBuyer("username", "abc123", "test", "test", "test@test.com", "8888888888", "test");

        //-----------------UNIT TESTS-----------------------

        assertEquals(false, am.isBuyerNameAvailable("username"), "BUYER: Taken username should appear taken");
        assertEquals(true, am.isBuyerNameAvailable(""), "BUYER: Empty username shouldn't appear taken");
        assertEquals(true,am.isBuyerNameAvailable("username-post"), "BUYER: if a username is a substring of actual username, it shouldn't appear taken");
        assertEquals(true, am.isBuyerNameAvailable("pref-username"), "BUYER: if a username is a substring of actual username, it shouldn't appear taken");


    }

    @Test
    void testIsSellerNameAvailable(){
        //Mock datas for unit tests
        AuthenticationModel am = new AuthenticationModel(new JavaSerializedDatabase());
        am.registerNewSeller("Jane Doe", "abc123", "test@test.com", "8888888888", "test");
        am.registerNewSeller("Jane Bell", "abc12", "test@test.com", "8888888888", "test");

        //-----------------UNIT TESTS-----------------------

        assertEquals(false, am.isSellerNameAvailable("Jane Doe"), "SELLER: matching name should return true");
        assertEquals(true, am.isSellerNameAvailable(""), "SELLER: Empty string in username should return true");
        assertEquals(true, am.isSellerNameAvailable("Jane Doe-post"), "SELLER: if a username is a substring of actual username, it shouldn't appear taken");
        assertEquals(true, am.isSellerNameAvailable("pref-Jane Doe"), "SELLER: if a username is a substring of actual username, it shouldn't appear taken");
    }
}