package com.microserviceprjct.OrderService.service;

import com.microserviceprjct.OrderService.model.OrderRequest;
import com.microserviceprjct.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
