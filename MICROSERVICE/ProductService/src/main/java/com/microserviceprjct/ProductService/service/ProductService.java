package com.microserviceprjct.ProductService.service;

import com.microserviceprjct.ProductService.model.ProductRequest;
import com.microserviceprjct.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
