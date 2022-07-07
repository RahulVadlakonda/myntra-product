package com.myntra.product.service;

import com.myntra.product.request.CreateProductRequest;
import com.myntra.product.request.EditProductRequest;
import com.myntra.product.request.GetProductsRequest;
import com.myntra.product.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ResponseEntity<CreateProductResponse> createProduct(CreateProductRequest request, List<MultipartFile> images);

    ResponseEntity<GetProductResponse> getProductById(long id);

    ResponseEntity<EditProductResponse> editProduct(EditProductRequest request);

    ResponseEntity<EditProductImagesResponse> editProductImages(long productId, List<MultipartFile> images);

    ResponseEntity<GetProductsResponse> getProductsList(GetProductsRequest getProductsRequest);
}
