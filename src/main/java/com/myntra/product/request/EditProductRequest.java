package com.myntra.product.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myntra.product.util.ErrorConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditProductRequest {

    @NotEmpty(message = ErrorConstants.INVALID_PRODUCT_ID)
    @NotBlank(message = ErrorConstants.INVALID_PRODUCT_ID)
    @NotNull(message = ErrorConstants.INVALID_PRODUCT_ID)
    private long productId;

    @Length(min = 1, max = 100, message = "ProductName length must be between 1 and 100")
    private String productName;

    @Min(value = 1l, message = "mrp must be positive")
    private long mrp;
    @Min(value = 1l, message = "price must be positive")
    private long salePrice;

    @Valid
    @JsonProperty(value = "brand")
    private BrandRequest brand;

    @Valid
    @JsonProperty(value = "category")
    private CategoryRequest category;
}
