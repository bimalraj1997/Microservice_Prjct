package com.microserviceprjct.PaymentService.controller;

import com.microserviceprjct.PaymentService.model.PaymentRequest;
import com.microserviceprjct.PaymentService.model.PaymentResponse;
import com.microserviceprjct.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){
        return new ResponseEntity<>(
                paymentService.doPayment(paymentRequest), HttpStatus.OK
        );
    }
    @GetMapping("/order/{oderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String oderId){
        return new ResponseEntity<>(paymentService.getPaymentDetailsByOrderId(oderId),
                HttpStatus.OK);
    }
}
