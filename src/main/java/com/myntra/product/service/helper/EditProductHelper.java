package com.myntra.product.service.helper;

import com.myntra.product.entities.*;
import com.myntra.product.exceptions.ProductNotFoundException;
import com.myntra.product.repository.BrandRepository;
import com.myntra.product.repository.CategoryRepository;
import com.myntra.product.repository.ImageRepository;
import com.myntra.product.repository.ProductRepository;
import com.myntra.product.request.BrandRequest;
import com.myntra.product.request.CategoryRequest;
import com.myntra.product.request.EditProductRequest;
import com.myntra.product.response.*;
import com.myntra.product.service.impl.AzureStorageServiceImpl;
import com.myntra.product.util.ErrorConstants;
import com.myntra.product.util.ImageUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EditProductHelper {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ImageRepository imageRepository;
    private final AzureStorageServiceImpl azureStorageService;

    public EditProductResponse editProduct(EditProductRequest request) {
        long productId = request.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        ErrorConstants.PRODUCT_NOT_FOUND_CODE,
                        ErrorConstants.PRODUCT_NOT_FOUND_MESSAGE + "-" +productId));
        EditProductResponse editProductResponse = new EditProductResponse();
        editProductResponse.setProductId(product.getProductId());
        if(StringUtils.hasText(request.getProductName())) {
            product.setProductName(request.getProductName());
        }
        if(!ObjectUtils.isEmpty(request.getBrand())) {
            editBrand(product,request.getBrand());
        }
        if(!ObjectUtils.isEmpty(request.getCategory())) {
            editCategory(product,request.getCategory());
        }
        if(request.getMrp() != 0) {
            Price editPrice = product.getPrice();
            editMrpOnly(product,editPrice,request.getMrp());
        }
        if(request.getSalePrice() != 0){
            Price editPrice = product.getPrice();
            editSalePriceOnly(product,editPrice,request.getSalePrice());
        }
        Product savedProduct = productRepository.save(product);
        setEditResponse(savedProduct,editProductResponse);
        return editProductResponse;
    }

    private void setEditResponse(Product savedProduct, EditProductResponse editProductResponse) {
        editProductResponse.setProductName(savedProduct.getProductName());
        BrandResponse brandResponse = new BrandResponse(savedProduct.getProductBrand().getBrandName());
        editProductResponse.setBrand(brandResponse);
        CategoryResponse categoryResponse = new CategoryResponse(savedProduct.getCategory().getCategoryName());
        editProductResponse.setCategory(categoryResponse);
        PriceResponse priceResponse = new PriceResponse(savedProduct.getPrice().getMrp(),savedProduct.getPrice().getSalePrice());
        editProductResponse.setPrice(priceResponse);
    }

    private void editBothPrice(Product product, Price editPrice, EditProductRequest request) {
        editPrice.setMrp(request.getMrp());
        editPrice.setSalePrice(request.getSalePrice());
        product.setPrice(editPrice);
    }

    private void editSalePriceOnly(Product product, Price editPrice, long salePrice) {
        editPrice.setSalePrice(salePrice);
        editPrice.setMrp(product.getPrice().getMrp());
        product.setPrice(editPrice);
    }

    private void editMrpOnly(Product product, Price editPrice, long mrp) {
        editPrice.setMrp(mrp);
        editPrice.setSalePrice(product.getPrice().getSalePrice());
        product.setPrice(editPrice);
    }

    private void editCategory(Product product, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findTopByCategoryName(categoryRequest.getName());
        category = category != null ? category :new Category(categoryRequest.getName());
        product.setCategory(category);
    }

    private void editBrand(Product product, BrandRequest brandRequest) {
        Brand brand = brandRepository.findTopByBrandName(brandRequest.getName());
        brand = brand != null ? brand : new Brand(brandRequest.getName());
        product.setProductBrand(brand);
    }

    public EditProductImagesResponse editProductImages(long productId, List<MultipartFile> images) {
        EditProductImagesResponse response = new EditProductImagesResponse();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        ErrorConstants.PRODUCT_NOT_FOUND_CODE,
                        ErrorConstants.PRODUCT_NOT_FOUND_MESSAGE + "-" +productId));
        response.setProductId(productId);
        List<Image> imageList = imageRepository.findAllByProductId(productId);
        for(Image image : imageList) {
            image.setProduct(null);
            imageRepository.save(image);
        }
        ImageUploadUtil uploadUtil = azureStorageService.uploadImages(images, productId);
        List<String> imageUrls = uploadUtil.getImageUrls();
        for (String imageUrl : imageUrls) {
            Image image = new Image();
            image.setImageUrl(imageUrl);
            image.setProduct(product);
            imageRepository.save(image);
        }
        response.setImages(imageUrls);
        if(!ObjectUtils.isEmpty(uploadUtil.getNotUploadedImages())) {
            response.setNotUploadedImages(uploadUtil.getNotUploadedImages());
        }
        return response;
    }
}
