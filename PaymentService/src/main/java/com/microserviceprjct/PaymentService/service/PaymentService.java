package com.microserviceprjct.PaymentService.service;

import com.microserviceprjct.PaymentService.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
