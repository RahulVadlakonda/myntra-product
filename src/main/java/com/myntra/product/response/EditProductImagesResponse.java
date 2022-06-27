package com.myntra.product.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditProductImagesResponse {
    private long productId;
    private List<String> images;
    private List<String> notUploadedImages;
}
