package com.microserviceprjct.PaymentService.service;

import com.microserviceprjct.PaymentService.model.PaymentRequest;
import com.microserviceprjct.PaymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String oderId);
}
