package com.myntra.product.service.helper;

import com.myntra.product.entities.*;
import com.myntra.product.exceptions.ProductNotFoundException;
import com.myntra.product.repository.ImageRepository;
import com.myntra.product.repository.ProductRepository;
import com.myntra.product.request.GetProductsRequest;
import com.myntra.product.response.*;
import com.myntra.product.response.Error;
import com.myntra.product.util.ErrorConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetProductHelper {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    public GetProductResponse getProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        ErrorConstants.PRODUCT_NOT_FOUND_CODE,
                        ErrorConstants.PRODUCT_NOT_FOUND_MESSAGE + "-" +productId));
        GetProductResponse productResponse = new GetProductResponse();

        productResponse.setProductId(product.getProductId());
        productResponse.setProductName(product.getProductName());
        setBrand(productResponse,product);
        setCategory(productResponse,product);
        setPrice(productResponse,product);
        setImages(productResponse,product);
        return productResponse;
    }

    private void setBrand(GetProductResponse productResponse, Product product) {
        Brand brand = product.getProductBrand();
        BrandResponse brandResponse = new BrandResponse(brand.getBrandName());
        productResponse.setBrand(brandResponse);
    }

    private void setCategory(GetProductResponse productResponse, Product product) {
        Category category = product.getCategory();
        CategoryResponse categoryResponse = new CategoryResponse(category.getCategoryName());
        productResponse.setCategory(categoryResponse);
    }

    private void setPrice(GetProductResponse productResponse, Product product) {
        Price price = product.getPrice();
        PriceResponse priceResponse = new PriceResponse(price.getMrp(),price.getSalePrice());
        productResponse.setPrice(priceResponse);
    }

    private void setImages(GetProductResponse productResponse, Product product) {
        List<Image> images = imageRepository.findAllByProductId(product.getProductId());
        List<String> imageResponses = new ArrayList<>();
        images.forEach(image -> imageResponses.add(image.getImageUrl()));
        productResponse.setImages(imageResponses);
    }

    public GetProductsResponse getProductsList(GetProductsRequest getProductsRequest) {
        GetProductsResponse response = new GetProductsResponse();
        List<GetProductResponse> products = new ArrayList<>();
        List<String> productIds = getProductsRequest.getProductIds();
        List<String> errorIds = new ArrayList<>();

//        List<Product> productList = productRepository.findByProductIdIn(productIds);
//        productList.stream().map(product -> new GetProductResponse(product.getProductId(),))
        for (String id : productIds) {

            try {
                long pId = Long.parseLong(id);
                GetProductResponse product = getProduct(pId);
                products.add(product);
            }
            catch (ProductNotFoundException e) {
                errorIds.add(id);
            }
            catch (NumberFormatException e) {
                errorIds.add(id);
            }
        }
        response.setProducts(products);
        response.setInvalidProductIds(errorIds);

        return response;
    }
}
