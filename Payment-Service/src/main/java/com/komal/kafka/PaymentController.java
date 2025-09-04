package com.komal.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentProducer paymentProducer;

    @PostMapping
    public Payment makePayment(@RequestBody Payment payment) {
        if (payment.getAmount() <= 0) {
            payment.setStatus("FAIL");
            payment.setMessage("Payment failed! Invalid amount: " + payment.getAmount());
        } else {
            payment.setStatus("SUCCESS");
            payment.setMessage("Your payment of " + payment.getAmount() + " is successful!");
        }
        log.warn(" payment object {}",payment);
        paymentProducer.sendPayment(payment);
        return payment;
    }

}
