package com.myntra.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myntra.product.request.CreateProductRequest;
import com.myntra.product.request.EditProductRequest;
import com.myntra.product.request.GetProductsRequest;
import com.myntra.product.response.*;
import com.myntra.product.service.ProductService;
import com.myntra.product.validators.ModelValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/product")
@Validated
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper mapper;
    private final ModelValidator modelValidator;

    @PostMapping()
    public ResponseEntity<CreateProductResponse> createProduct(@RequestParam("images") List<MultipartFile> images,
                                                               @RequestParam("createProductRequest") String request) throws JsonProcessingException {

        CreateProductRequest createProductRequest = mapper.readValue(request, CreateProductRequest.class);
        modelValidator.validate(createProductRequest);
        return productService.createProduct(createProductRequest, images);
    }

    @GetMapping()
    public ResponseEntity<GetProductResponse> getProductById(@RequestParam("productId") long productId) {

        return productService.getProductById(productId);
    }

    @GetMapping("/list")
    public ResponseEntity<GetProductsResponse> getListOfProducts(@RequestParam("listOfProductIds") String listOfProductIds) throws JsonProcessingException{
        GetProductsRequest getProductsRequest = mapper.readValue(listOfProductIds,GetProductsRequest.class);
        modelValidator.validate(getProductsRequest);
        return productService.getProductsList(getProductsRequest);
    }

    @PutMapping()
    public ResponseEntity<EditProductResponse> editProductDetails(@RequestBody EditProductRequest editProductRequest) {
        return productService.editProduct(editProductRequest);
    }

    @PutMapping("/images")
    public ResponseEntity<EditProductImagesResponse> editProductImages(@RequestParam("images") List<MultipartFile> images,
                                                                       @RequestParam("productId") long productId) {
        return productService.editProductImages(productId,images);
    }
}
