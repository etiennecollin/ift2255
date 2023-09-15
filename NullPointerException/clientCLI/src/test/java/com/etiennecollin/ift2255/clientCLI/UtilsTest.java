package com.etiennecollin.ift2255.clientCLI;

import org.junit.jupiter.api.Test;

import static com.etiennecollin.ift2255.clientCLI.Utils.prettify;
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
}
