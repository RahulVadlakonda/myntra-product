package com.myntra.product.service.impl;

import com.myntra.product.entities.*;
import com.myntra.product.exceptions.ProductNotFoundException;
import com.myntra.product.repository.ImageRepository;
import com.myntra.product.repository.ProductRepository;
import com.myntra.product.request.CreateProductRequest;
import com.myntra.product.request.EditProductRequest;
import com.myntra.product.response.*;
import com.myntra.product.service.ProductService;
import com.myntra.product.service.helper.CreateProductHelper;
import com.myntra.product.service.helper.EditProductHelper;
import com.myntra.product.service.helper.GetProductHelper;
import com.myntra.product.util.ErrorConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class ProductServiceImpl implements ProductService {

    private final CreateProductHelper createProductServiceHelper;
    private final GetProductHelper getProductHelper;
    private final EditProductHelper editProductHelper;

    @Override
    @Transactional
    public ResponseEntity<CreateProductResponse> createProduct(CreateProductRequest request, List<MultipartFile> images) {
        CreateProductResponse productResponse = createProductServiceHelper.createProduct(request,images);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<GetProductResponse> getProductById(long id){
        GetProductResponse product = getProductHelper.getProduct(id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<EditProductResponse> editProduct(EditProductRequest request) {
        EditProductResponse productResponse = editProductHelper.editProduct(request);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<EditProductImagesResponse> editProductImages(long productId, List<MultipartFile> images) {
        EditProductImagesResponse productImagesResponse = editProductHelper.editProductImages(productId,images);
        return new ResponseEntity<>(productImagesResponse,HttpStatus.OK);
    }
}
