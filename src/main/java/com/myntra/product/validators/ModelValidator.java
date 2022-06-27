package com.myntra.product.validators;

import com.myntra.product.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ModelValidator {

    private final Validator validator;

    public void validate(Object objectToValidate) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(objectToValidate);

        if(constraintViolations != null && constraintViolations.size() > 0) {
            Set<String> errorCodes = new HashSet<>();
            constraintViolations.forEach(constraintViolation -> errorCodes.add(constraintViolation.getMessage()));
            throw new ValidationException(errorCodes);
        }
    }
}
