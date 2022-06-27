package com.myntra.product.service.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.myntra.product.config.AzureBlobClientConfig;
import com.myntra.product.service.AzureStorageService;
import com.myntra.product.util.ImageUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AzureStorageServiceImpl implements AzureStorageService {

    private final BlobClientBuilder blobClientBuilder;

    @Override
    public ImageUploadUtil uploadImages(List<MultipartFile> images, long productId) {
        List<String> imageUrls = new ArrayList<>();
        List<String> notUploadedImages = new ArrayList<>();
        Set<String> imageExtensions = new HashSet<>(
                Arrays.asList("jpg", "jpeg", "png")
        );
        BlobHttpHeaders blobHttpHeaders = new BlobHttpHeaders();
        for (MultipartFile image : images) {
            var fileNameExtension = StringUtils.getFilenameExtension(image.getOriginalFilename());
            if (!imageExtensions.contains(fileNameExtension)) {
                notUploadedImages.add(image.getOriginalFilename());
            } else if (ObjectUtils.isEmpty(image) || image.getSize() <= 0) {
                notUploadedImages.add(image.getOriginalFilename());
            } else {
                var key = UUID.randomUUID();
                var imageName = "" + productId + "/" + key;
                BlobClient client = blobClientBuilder.blobName(imageName).buildClient();
                try {
                    client.upload(image.getInputStream(), image.getSize());
                    blobHttpHeaders.setContentType("image/" + fileNameExtension);
                    client.setHttpHeaders(blobHttpHeaders);
                    imageUrls.add(client.getBlobUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ImageUploadUtil uploadUtil = new ImageUploadUtil();
        if (!ObjectUtils.isEmpty(notUploadedImages)) {
            uploadUtil.setNotUploadedImages(notUploadedImages);
        }
        uploadUtil.setImageUrls(imageUrls);

        return uploadUtil;

    }
}
