package com.myntra.product.exceptions;

import com.myntra.product.response.BaseResponse;
import com.myntra.product.response.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@Component
@RestControllerAdvice
public class ErrorHandlers {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleValidationException(ValidationException e) {
        BaseResponse baseResponse = new BaseResponse();
        Set<Error> errors = new HashSet<>();
        for (String error : e.getErrors()) {
            errors.add(new Error(error));
        }
        baseResponse.setErrors(errors);
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleProductNotFoundException(ProductNotFoundException e) {
        BaseResponse baseResponse = new BaseResponse();
        Set<Error> errors = new HashSet<>();
        errors.add(new Error(e.getErrorCode(),e.getMessage()));
        baseResponse.setErrors(errors);
        return new ResponseEntity<>(baseResponse,HttpStatus.BAD_REQUEST);
    }
}
