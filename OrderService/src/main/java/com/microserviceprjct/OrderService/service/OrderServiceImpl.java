package com.microserviceprjct.OrderService.service;

import com.microserviceprjct.OrderService.entity.Order;
import com.microserviceprjct.OrderService.exception.CustomException;
import com.microserviceprjct.OrderService.external.client.PaymentService;
import com.microserviceprjct.OrderService.external.client.ProductService;
import com.microserviceprjct.OrderService.external.request.PaymentRequest;
import com.microserviceprjct.OrderService.model.OrderRequest;
import com.microserviceprjct.OrderService.model.OrderResponse;
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
    @Autowired
    private PaymentService paymentService;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //create order entity and save data with order status created
        //call product service to blk prdct and reduce quantity
        //call pymt servc
        log.info("Placing order request: {}",orderRequest);
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

        log.info("Calling payment service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getAmount())
                .build();
        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Changing the order status to PLACED");
            orderStatus = "PLACED";
        }
        catch (Exception e){
            log.error("Error occurred in payment. Changing the status to PAYMENT FAILED ");
            orderStatus = "PAYMENT_FAILED";

        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order placed successfully with order id: {}",order.getId());

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get order details for Order Id : {}", orderId);

        Order order
                = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Order not found for orderId:" + orderId, "NOT_FOUND",404));

        OrderResponse orderResponse
                = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .build();
        return orderResponse;
    }
}
