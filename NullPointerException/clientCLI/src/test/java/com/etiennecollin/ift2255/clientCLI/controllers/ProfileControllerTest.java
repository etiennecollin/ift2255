/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.controllers;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.models.ProfileModel;
import com.etiennecollin.ift2255.clientCLI.models.SocialModel;
import com.etiennecollin.ift2255.clientCLI.models.data.Buyer;
import com.etiennecollin.ift2255.clientCLI.models.data.UserType;
import com.etiennecollin.ift2255.clientCLI.views.ViewRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ProfileControllerTest {

    // Mock objects to simulate the behavior of real objects during testing
    private ViewRenderer mockRenderer;
    private ProfileModel mockProfileModel;
    private SocialModel mockSocialModel;
    private ProfileController profileController;

    /**
     * Executes before each test method.
     * Initializes mock objects and creates an instance of the ProfileController class.
     */
    @BeforeEach
    void setUp() {
        // Initialize mock objects using Mockito
        mockRenderer = Mockito.mock(ViewRenderer.class);
        mockProfileModel = Mockito.mock(ProfileModel.class);
        mockSocialModel = Mockito.mock(SocialModel.class);

        // Create an instance of ProfileController using the mock objects
        profileController = new ProfileController(mockRenderer, mockProfileModel, mockSocialModel);
    }

    /**
     * Tests the updateBuyer method with a successful update scenario.
     */
    @Test
    void testUpdateBuyer_SuccessfulUpdate() {

        UUID userId = UUID.randomUUID();
        Buyer currentBuyer = new Buyer("username", 123456, "Juan", "Pillou", "juanPillou@example.com", "123-456", "789 Moon Street", 100);
        OperationResult mockResult = new OperationResult(true, "Update successful.");

        // Mock the behavior of the profile model
        Mockito.when(mockProfileModel.getBuyer(userId)).thenReturn(currentBuyer);
        Mockito.when(mockProfileModel.updateBuyer(userId, "NewJuan", "NewPillou", "newPassword", "invalid-email", "123-456", "789 Moon Street"))
                .thenReturn(mockResult);


        OperationResult result = profileController.updateBuyer("NewJuan", "NewPillou", "newPassword", "invalid-email", "123-456", "789 Moon Street");

        //------------------------------- UNIT TESTS -----------------------------------
        assertTrue(result.isValid());
        assertEquals("Update successful.", result.getMessage());
        assertNotNull(result, "Result should not be null");
        assertFalse(result.hasErrors(), "There should be no errors");


        // Verify that the update method was called with the correct parameters
        Mockito.verify(mockProfileModel).updateBuyer(userId, "NewJuan", "NewPillou", "newPassword", "invalid-email", "123-456", "789 Moon Street");
    }

    /**
     * Tests the updateBuyer method with a failed update scenario.
     */
    @Test
    void testUpdateBuyer_FailedUpdate() {

        UUID userId = UUID.randomUUID();
        Buyer currentBuyer = new Buyer("username", 123456, "Juan", "Pillou", "juanPillou@example.com", "123-456", "789 Moon Street", 100);
        OperationResult mockResult = new OperationResult(false, "Update failed.");

        // Mock the behavior of the profile model
        Mockito.when(mockProfileModel.getBuyer(userId)).thenReturn(currentBuyer);
        Mockito.when(mockProfileModel.updateBuyer(userId, "NewJuan", "NewPillou", "newPassword", "invalid-email", "123-456", "789 Moon Street"))
                .thenReturn(mockResult);


        OperationResult result = profileController.updateBuyer("NewJuan", "NewPillou", "newPassword", "invalid-email", "123-456", "789 Moon Street");

        //------------------------------- UNIT TESTS -----------------------------------
        assertFalse(result.isValid());
        assertEquals("Update failed.", result.getMessage());
        assertNotNull(result, "Result should not be null");
        assertTrue(result.hasErrors(), "There should be errors");

        // Verify that the update method was called with the correct parameters
        Mockito.verify(mockProfileModel).updateBuyer(userId, "NewJuan", "NewPillou", "newPassword", "invalid-email", "123-456", "789 Moon Street");
    }

    @Test
    void testUpdateBuyer_InvalidEmail() {
        UUID userId = UUID.randomUUID();
        Buyer currentBuyer = new Buyer("username", 123456, "Juan", "Pillou", "juanPillou@example.com", "123-456", "789 Moon Street", 100);
        OperationResult mockResult = new OperationResult(false, "Invalid email address.");

        // Mock the behavior of the profile model
        Mockito.when(mockProfileModel.getBuyer(userId)).thenReturn(currentBuyer);
        Mockito.when(mockProfileModel.updateBuyer(userId, "NewJuan", "NewPillou", "newPassword", "invalid-email", "123-456", "789 Moon Street"))
                .thenReturn(mockResult);

        OperationResult result = profileController.updateBuyer("NewJuan", "NewPillou", "newPassword", "invalid-email", "123-456", "789 Moon Street");
        //------------------------------- UNIT TESTS -----------------------------------
        assertFalse(result.isValid());
        assertEquals("Invalid email address.", result.getMessage());
        assertNotNull(result, "Result should not be null");
        assertTrue(result.hasErrors(), "There should be errors");
    }

}