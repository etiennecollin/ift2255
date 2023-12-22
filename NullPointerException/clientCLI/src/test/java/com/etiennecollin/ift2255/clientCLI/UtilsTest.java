/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import org.junit.jupiter.api.Test;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The `UtilsTest` class contains test cases for the utility methods in the `Utils` class.
 * It uses JUnit 5 to create and run the tests.
 */
public class UtilsTest {
    /**
     * Test case for the `prettify` method in the `Utils` class.
     * It verifies that the `prettify` method correctly adds a delimiter to the input string.
     */
    @Test
    public void testPrettify() {
        String input = "Test String";
        assertEquals("| " + input, prettify(input));
    }



    // --------------- TEST VALIDATE PHONE NUMBER --------------------

    @Test                //10 digits
    public void testValidatePhoneNumberWhenValid() {
        String phoneNumber = "8888888888";
        assertEquals( new OperationResult(true, ""), validatePhoneNumber(phoneNumber));
    }

    @Test                //11 digits
    public void testValidatePhoneNumberWhenMoreDigits() {
        String phoneNumber = "88888888889";
        assertEquals( new OperationResult(false, "Your phone number has a wrong format"), validatePhoneNumber(phoneNumber));
    }

    @Test                //9 digits
    public void testValidatePhoneNumberWhenLessDigits() {
        String phoneNumber = "888888887";
        assertEquals( new OperationResult(false, "Your phone number has a wrong format"), validatePhoneNumber(phoneNumber));
    }

    @Test
    public void testValidatePhoneNumberWithNonDigits() {
        String phoneNumber = "xxxxxxxxxx";
        assertEquals( new OperationResult(false, "Your phone number has a wrong format"), validatePhoneNumber(phoneNumber));
    }

    @Test
    public void testValidatePhoneNumberWithNonDigitAndDigits() {
        String phoneNumber = "888888888x";
        assertEquals( new OperationResult(false, "Your phone number has a wrong format"), validatePhoneNumber(phoneNumber));
    }

    @Test
    public void testValidatePhoneNumberIfEmpty() {
        String phoneNumber = "";
        assertEquals( new OperationResult(false, "Your phone number has a wrong format"), validatePhoneNumber(phoneNumber));
    }

    //-------------- VALIDATE NAME --------------------------

    @Test
    public void testValidateNameIfEmpty() {
        String name = "";
        assertEquals( new OperationResult(false, "Your name should only contains letters"), validateName(name));

    }

    @Test
    public void testValidateNameIfCorrect() {
        String name = "Jane Doe";
        assertEquals(new OperationResult(true, ""), validateName(name));

    }

    @Test
    public void testValidateNameIfNumber() {
        String name = "Jane Doe5";
        assertEquals( new OperationResult(false, "Your name should only contains letters"), validateName(name));
    }

    @Test
    public void testValidateNameIfSpecialChar() {
        String name = "Jane Doe+";
        assertEquals( new OperationResult(false, "Your name should only contains letters"), validateName(name));
    }

    //--------------- VALIDATE EMAIL ------------------------

    /*    public static OperationResult validateEmail(String s) throws RuntimeException {
        if (!s.matches("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}")) {
            return new OperationResult(false, "Your email has a wrong format");
        }
        return new OperationResult(true, "");
    }*/

    @Test
    public void testValidateEmailIfEmpty() {
        String email = "";
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail(email));
    }

    //4 foolowing test are different domain names
    @Test
    public void testValidateEmailIfCorrectCom() {
        String email = "example@example.com";
        assertEquals(new OperationResult(true, ""), validateEmail(email));
    }

    @Test
    public void testValidateEmailIfNoAt() {
        String email = "exampleexample.com";
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail(email));
    }

    @Test
    public void testValidateEmailIfNoDot() {
        String email = "example@examplecom";
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail(email));
    }

    @Test
    public void testValidateEmailIfNoFirstPart() {
        String email = "@example.com";
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail(email));
    }

    @Test
    public void testValidateEmailIfNoLastPart() {
        String email = "example@.com";
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail(email));
    }
}
