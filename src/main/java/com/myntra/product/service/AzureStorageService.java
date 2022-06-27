package com.myntra.product.service;

import com.myntra.product.util.ImageUploadUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AzureStorageService {
    ImageUploadUtil uploadImages(List<MultipartFile> images, long productId);
}
