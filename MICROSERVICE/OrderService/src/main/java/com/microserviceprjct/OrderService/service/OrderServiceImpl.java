package com.microserviceprjct.OrderService.service;

import com.microserviceprjct.OrderService.entity.Order;
import com.microserviceprjct.OrderService.external.client.ProductService;
import com.microserviceprjct.OrderService.model.OrderRequest;
import com.microserviceprjct.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //create order entity and save data with order status created
        //call product service to blk prdct and reduce quantity
        //call pymt servc
        log.info("Placing order request",orderRequest);
        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
        log.info("Creating with status CREATED");
        Order order= Order.builder()
                .amount(orderRequest.getAmount())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .build();
        order=orderRepository.save(order);
        log.info("Order placed successfully with order id: ",order.getId());

        return order.getId();
    }
}
