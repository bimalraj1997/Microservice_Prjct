package com.microserviceprjct.OrderService.service;

import com.microserviceprjct.OrderService.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
