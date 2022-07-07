package com.myntra.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetProductsRequest {

    @NotEmpty(message = "productIds must not be empty")
    @NotNull(message = "productIds must not be null")
    public List<String> productIds;
}
