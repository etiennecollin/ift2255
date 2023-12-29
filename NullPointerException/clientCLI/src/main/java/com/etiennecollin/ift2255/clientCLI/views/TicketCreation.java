/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.OperationResult;
import com.etiennecollin.ift2255.clientCLI.Utils;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;
import com.etiennecollin.ift2255.clientCLI.models.data.TicketCause;

import java.util.UUID;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

// TODO javadoc
public class TicketCreation extends View {
    private final TicketController ticketController;
    private final UUID orderId;

    public TicketCreation(UUID orderId, TicketController ticketController) {
        this.orderId = orderId;
        this.ticketController = ticketController;
    }

    @Override
    public void render() {
        TicketCause cause = prettyMenu("Select the type of issue", TicketCause.class);
        String description = prettyPrompt("Description of problem", Utils::validateNotEmpty);

        if (prettyPromptBool("Do you really want to open a ticket for this order?")) {
            OperationResult result = ticketController.createManualTicket(orderId, null, description, cause);

            System.out.println(prettify(result.message()));
        } else {
            System.out.println(prettify("Cancelled ticket creation"));
        }

        waitForKey();
    }
}
