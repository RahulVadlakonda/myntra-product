package com.myntra.product.request;


import com.myntra.product.util.ErrorConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotNull(message = ErrorConstants.INVALID_CATEGORY_NAME)
    @NotEmpty(message = ErrorConstants.INVALID_CATEGORY_NAME)
    @Size(min = 1, max = 60, message = ErrorConstants.INVALID_CATEGORY_NAME)
    private String name;
}
