package com.myntra.product.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ValidationException extends RuntimeException {
    private Set<String> errors;
}
