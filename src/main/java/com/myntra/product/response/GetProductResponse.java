package com.myntra.product.response;


import com.myntra.product.entities.Image;
import com.myntra.product.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductResponse {

    private long productId;
    private String productName;
    private BrandResponse brand;
    private CategoryResponse category;
    private PriceResponse price;
    private List<String> images;
}
