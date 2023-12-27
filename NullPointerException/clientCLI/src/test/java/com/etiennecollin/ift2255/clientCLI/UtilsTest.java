/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import org.junit.jupiter.api.Test;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The UtilsTest class contains unit tests for the utility methods in the Utils class.
 * It ensures the proper functionality of utility methods such as prettify, validatePhoneNumber, validateName, and validateEmail.
 */
public class UtilsTest {
    /**
     * Tests the prettify method in the Utils class.
     */
    @Test
    public void testPrettify() {
        String input = "Test String";
        assertEquals("| " + input, prettify(input));
        assertEquals("| ", prettify(""));
    }

    /**
     * Tests the validatePhoneNumber method in the Utils class.
     */
    @Test
    public void testValidatePhoneNumber() {
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("8888888888"), "8888888888 should be accepted");    // 10 digits
        assertEquals(new OperationResult(false, "Your phone number has a wrong format"), validatePhoneNumber("88888888889"), "Failed to refuse a phone number with too many digit");   // more digits than required
        assertEquals(new OperationResult(false, "Your phone number has a wrong format"), validatePhoneNumber("888888887"), "Failed to refuse a phone number with not enough digit");    // less digits than required
        assertEquals(new OperationResult(false, "Your phone number has a wrong format"), validatePhoneNumber("xxxxxxxxxx"), "Failed to refuse a phone number with characters instead of digits");   // char instead of numbers
        assertEquals(new OperationResult(false, "Your phone number has a wrong format"), validatePhoneNumber("888888888x"), "Failed to refuse a phone number with at least one character");   // char within numbers
        assertEquals(new OperationResult(false, "Your phone number has a wrong format"), validatePhoneNumber(""), "Failed to refuse empty phone number");             // no phone number entered
    }

    /**
     * Tests the validateName method in the Utils class.
     */
    @Test
    public void testValidateName() {
        assertEquals(new OperationResult(true, ""), validateName("Jane Doe"), "Jane Doe should be accepted");
        assertEquals(new OperationResult(false, "Your name should only contains letters"), validateName(""), "Failed to refuse empty name");
        assertEquals(new OperationResult(false, "Your name should only contains letters"), validateName("Jane Doe5"), "Failed to refuse a name with digits");
        assertEquals(new OperationResult(false, "Your name should only contains letters"), validateName("Jane Doe+"), "Failed to refuse a name with special characters");
    }

    /**
     * Tests the validateEmail method in the Utils class.
     */
    @Test
    public void testValidateEmail() {
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail(""), "failed to refuse empty email");
        assertEquals(new OperationResult(true, ""), validateEmail("example@example.exe"), "should accept every domain name");
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail("exampleexample.com"), "failed to refuse email without @");
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail("@example.com"), "failed to refuse email starting with @");
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail("example@.com"), "failed to refuse email with empty second email part");
    }
}
