package com.anamul.email_service.service;


import com.anamul.email_service.io.OrderResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class KafkaPaymentSuccessEventConsumer {

    @Autowired
    private EmailSendService emailSendService;

        @KafkaListener(topics = "payment-success-topic", groupId = "email-service-group")
    public void consumePaymentSuccessEvent(OrderResponse event) {

        System.out.println("Payment received for order: " + event.getCustomerName());

            try {
                emailSendService.sendEmailForPaymentConfirmation(event);
            } catch (MessagingException e) {
                e.printStackTrace();
            }


    }


}
