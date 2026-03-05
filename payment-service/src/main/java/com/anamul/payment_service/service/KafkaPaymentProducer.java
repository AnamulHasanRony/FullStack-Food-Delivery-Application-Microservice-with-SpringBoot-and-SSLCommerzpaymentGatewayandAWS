package com.anamul.payment_service.service;
import com.anamul.payment_service.io.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPaymentProducer {
    private final KafkaTemplate<String, OrderResponse> kafkaTemplate;
        public void sendPaymentSuccessEvent( OrderResponse event) {

        kafkaTemplate.send("payment-success-topic", event);

    }

}
