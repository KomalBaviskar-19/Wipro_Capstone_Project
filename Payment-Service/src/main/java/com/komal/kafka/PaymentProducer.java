package com.komal.kafka;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, Payment> kafkaTemplate;

    public void sendPayment(Payment payment) {
        kafkaTemplate.send("payment-topic", payment);
    }
}
