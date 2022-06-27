package com.myntra.product.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private Set<Error> errors;
}
