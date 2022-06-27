package com.myntra.product.service.helper;

import com.myntra.product.entities.*;
import com.myntra.product.repository.BrandRepository;
import com.myntra.product.repository.CategoryRepository;
import com.myntra.product.repository.ImageRepository;
import com.myntra.product.repository.ProductRepository;
import com.myntra.product.request.CreateProductRequest;
import com.myntra.product.response.CreateProductResponse;
import com.myntra.product.service.impl.AzureStorageServiceImpl;
import com.myntra.product.util.ImageUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateProductHelper {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final AzureStorageServiceImpl azureStorageService;

    public CreateProductResponse createProduct(CreateProductRequest request, List<MultipartFile> images) {
        CreateProductResponse createProductResponse = new CreateProductResponse();
        Product product = new Product();
        setProductFields(product,request);
        Product savedProduct = productRepository.save(product);
        createProductResponse.setProductId(savedProduct.getProductId());
        setImageFields(product,createProductResponse,images);
        return createProductResponse;
    }

    public void setProductFields(Product product, CreateProductRequest request) {
        product.setProductName(request.getProductName());
        setCategory(product,request);
        setBrand(product,request);
        setPrice(product,request);
    }

    private void setCategory(Product product, CreateProductRequest request) {
        Category category = categoryRepository.findTopByCategoryName(request.getCategory().getName());
        category = category != null ? category :new Category(request.getCategory().getName());
        product.setCategory(category);
    }

    private void setBrand(Product product, CreateProductRequest request) {
        Brand brand = brandRepository.findTopByBrandName(request.getBrand().getName());
        brand = brand != null ? brand : new Brand(request.getBrand().getName());
        product.setProductBrand(brand);
    }

    private void setPrice(Product product, CreateProductRequest request) {
        Price price = new Price();
        price.setSalePrice(request.getSalePrice());
        price.setMrp(request.getMrp());
        price.setProduct(product);
        product.setPrice(price);
    }

    public void setImageFields(Product savedProduct, CreateProductResponse productResponse, List<MultipartFile> images) {
        ImageUploadUtil uploadUtil = azureStorageService.uploadImages(images, productResponse.getProductId());
        List<String> imageUrls = uploadUtil.getImageUrls();
        for (String imageUrl : imageUrls) {
            Image image = new Image();
            image.setImageUrl(imageUrl);
            image.setProduct(savedProduct);
            imageRepository.save(image);
        }
        productResponse.setImages(imageUrls);
        if(!ObjectUtils.isEmpty(uploadUtil.getNotUploadedImages())) {
            productResponse.setNotUploadedImages(uploadUtil.getNotUploadedImages());
        }
    }


}
