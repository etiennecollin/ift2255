/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.model.AuthenticationModel;
import com.etiennecollin.ift2255.clientCLI.model.data.JavaSerializedDatabase;
import com.etiennecollin.ift2255.clientCLI.views.ViewRenderer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationControllerTest {

    @Test
    void testLoginBuyer() {
        // MOCK DATAS FOR UNIT TESTS
        AuthenticationModel am = new AuthenticationModel(new JavaSerializedDatabase());
        am.registerNewBuyer("username", "abc123", "test", "test", "test@test.com", "8888888888", "test");

        ViewRenderer vr = new ViewRenderer();

        AuthenticationController ac = new AuthenticationController(vr, am);

        //------------------------------- UNIT TESTS -----------------------------------
        assertEquals(new OperationResult(true, ""), ac.loginBuyer("username", "abc123"), "BUYER: matching username and matching password should be able to log in");
        assertEquals(new OperationResult(false, "Username or password is incorrect."), ac.loginBuyer("", "abc123"), "BUYER: failed to refuse empty username");
        assertEquals(new OperationResult(false, "Username or password is incorrect."), ac.loginBuyer("user", "abc123"), "BUYER: failed to refuse part of the username");
        assertEquals(new OperationResult(false, "Username or password is incorrect."), ac.loginBuyer("username", ""), "BUYER: failed to refuse an empty password");
        assertEquals(new OperationResult(false, "Username or password is incorrect."), ac.loginBuyer("username", "abc"), "BUYER: failed to refuse part of the password");

    }

    @Test
    void testLoginSeller() {
        //MOCK DATAS FOR UNIT TESTS
        AuthenticationModel am = new AuthenticationModel(new JavaSerializedDatabase());
        am.registerNewSeller("Jane Doe", "abc123", "test@test.com", "8888888888", "test");
        am.registerNewSeller("Jane Bell", "abc12", "test@test.com", "8888888888", "test");

        ViewRenderer vr = new ViewRenderer();

        AuthenticationController ac = new AuthenticationController(vr, am);

        //------------------------------- UNIT TESTS -----------------------------------
        assertEquals(new OperationResult(true, ""), ac.loginSeller("Jane Doe", "abc123"));
        assertEquals(new OperationResult(false, "Username or password is incorrect."), ac.loginSeller("", "abc123"), "SELLER: failed to refuse empty name");
        assertEquals(new OperationResult(false, "Username or password is incorrect."), ac.loginSeller("Jane", "abc123"), "SELLER: failed to refuse part of the username");
        assertEquals(new OperationResult(false, "Username or password is incorrect."), ac.loginSeller("Jane Doe", ""), "SELLER: failed to refuse an empty password");
        assertEquals(new OperationResult(false, "Username or password is incorrect."), ac.loginSeller("Jane Doe", "abc"), "SELLER: failed to refuse part of the password");

    }
}