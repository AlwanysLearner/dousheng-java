package com.ssy.Entity;

import lombok.Data;

@Data
public class ValidationResult {
    private final boolean isValid;
    private final String message;
}
