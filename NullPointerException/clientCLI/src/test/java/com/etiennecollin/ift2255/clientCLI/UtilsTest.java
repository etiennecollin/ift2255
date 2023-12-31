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
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("(888)8888888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("(888)888 8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("(888) 8888888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("(888) 888 8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("(888) 888-8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("(888)-888 8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("(888)-888-8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("(888)888-8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("(888)-8888888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("8888888888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("888888 8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("888 8888888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("888 888 8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("888 888-8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("888-888 8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("888-888-8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("888888-8888"), "Failed to accept valid phone number format");    // 10 digits
        assertEquals(new OperationResult(true, ""), validatePhoneNumber("888-8888888"), "Failed to accept valid phone number format");    // 10 digits
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
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail(""), "Failed to refuse empty email");
        assertEquals(new OperationResult(true, ""), validateEmail("example@example.exe"), "Failed to accept valid domain name");
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail("exampleexample.com"), "Failed to refuse email without @");
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail("@example.com"), "Failed to refuse email starting with @");
        assertEquals(new OperationResult(false, "Your email has a wrong format"), validateEmail("example@.com"), "Failed to refuse email with empty second email part");
    }

    /**
     * Tests the validateBonusFidelityPoints method in the Utils class.
     */
    @Test
    public void testValidateBonusFidelityPoints() {
        assertEquals(new OperationResult(false, "Bonus points cannot be negative."), validateBonusFidelityPoints(-10, 5000), "Failed to refuse negative bonus points");
        assertEquals(new OperationResult(false, "A maximum of " + 950 + " bonus points are allowed based on this product's price."), validateBonusFidelityPoints(951, 5000), "Failed to refuse bonus points greater than 19 points per dollar");
        assertEquals(new OperationResult(true, ""), validateBonusFidelityPoints(950, 5000), "Failed to accept valid bonus points");
    }

    /**
     * Test the formatMoney method in the Utils class.
     */
    @Test
    public void testFormatMoney() {
        assertEquals("0.00$", formatMoney(0));
        assertEquals("50.30$", formatMoney(5030));
        assertEquals("1.00$", formatMoney(100));
        assertEquals("1.00$", formatMoney(100));
        assertEquals("0.01$", formatMoney(1));
        assertEquals("0.10$", formatMoney(10));
    }

    /**
     * Test the validateNotEmpty method in the Utils class.
     */
    @Test
    public void testValidateNotEmpty() {
        assertEquals(new OperationResult(false, "This field must not be empty."), validateNotEmpty(""), "Does not accept empty string");
        assertEquals(new OperationResult(false, "This field must not be empty."), validateNotEmpty(" "), "Does not accept string with nothing but whitespace");
        assertEquals(new OperationResult(true, ""), validateNotEmpty("a"), "Accepts a string with a text character");
        assertEquals(new OperationResult(true, ""), validateNotEmpty(" a "), "Accepts a string with a text character surrounded by whitespace");
    }

    /**
     * Test the validateISBN method in the Utils class.
     */
    @Test
    public void testValidateISBN() {
        assertEquals(new OperationResult(false, "Your ISBN has a wrong format"), validateISBN("123456789"), "Does not accept ISBN with less than 10 digits");
        assertEquals(new OperationResult(true, ""), validateISBN("1234567890"), "Accepts a valid ISBN 10");
        assertEquals(new OperationResult(false, "Your ISBN has a wrong format"), validateISBN("123456789012"), "Does not accept ISBN with more than 10 but less than 13 digits");
        assertEquals(new OperationResult(false, "Your ISBN has a wrong format"), validateISBN("1234567890123a"), "Does not accept ISBN with at least one letter");
        assertEquals(new OperationResult(true, ""), validateISBN("1234567890123"), "Accepts a valid ISBN 13");
        assertEquals(new OperationResult(false, "Your ISBN has a wrong format"), validateISBN("12345678901234"), "Failed to refuse ISBN with more than 13 digits");

        assertEquals(new OperationResult(false, "Your ISBN has a wrong format"), validateISBN("12345678-9"), "Does not accept ISBN with 10 characters but less than 10 digits");
        assertEquals(new OperationResult(true, ""), validateISBN("1-2345-6789-0"), "Accepts a valid ISBN 10 with dashes");
        assertEquals(new OperationResult(false, "Your ISBN has a wrong format"), validateISBN("12345678-90-1"), "Does not accept ISBN with 13 characters but less than 13 digits");
        assertEquals(new OperationResult(true, ""), validateISBN("1-23-45678-9012-3"), "Accepts a valid ISBN 13 with dashes");
    }
}
