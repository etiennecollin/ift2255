/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

/**
 * A record representing the result of an operation, including whether it is valid
 * and an optional message providing additional information.
 *
 * @param isValid Indicates whether the operation result is valid.
 * @param message An optional message providing additional information about the result.
 */
public record OperationResult(boolean isValid, String message) {}
