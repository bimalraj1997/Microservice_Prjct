package com.microserviceprjct.ProductService.service;

import com.microserviceprjct.ProductService.entity.Product;
import com.microserviceprjct.ProductService.exception.ProductServiceCustomException;
import com.microserviceprjct.ProductService.model.ProductRequest;
import com.microserviceprjct.ProductService.model.ProductResponse;
import com.microserviceprjct.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.ProviderNotFoundException;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product...");
        Product product
                = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product Created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product for product id:",productId);
        Product product
                =productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given id not found","Product_Not_Found"));
        ProductResponse productResponse= new ProductResponse();
        BeanUtils.copyProperties(product,productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce Quantity {} for id: {} ",quantity,productId);
        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() -> new ProductServiceCustomException(
                                "Product with given id not found",
                                "PRODUCT_NOT_FOUND"
                        ));
        if (product.getQuantity()<quantity){
            throw  new ProductServiceCustomException(
                    "Product does not have sufficient quantity",
                    "INSUFFICIENT_QUANTITY"
            );
        }
        product.setQuantity((product.getQuantity())- quantity);
        productRepository.save(product);
        log.info("Product Quantity updated successfully");
    }

}
