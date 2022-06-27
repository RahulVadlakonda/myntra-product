package com.myntra.product.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadUtil {

    private List<String> imageUrls;
    private List<String> notUploadedImages;
}
