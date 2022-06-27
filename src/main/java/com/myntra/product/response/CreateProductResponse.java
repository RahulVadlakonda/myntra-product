package com.myntra.product.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myntra.product.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateProductResponse {
    private long productId;

    private List<String> images;

    private List<String> notUploadedImages;
}
