package com.myntra.product.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditProductResponse {
    private long productId;
    private String productName;
    private BrandResponse brand;
    private CategoryResponse category;
    private PriceResponse price;
}
