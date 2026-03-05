package com.anamul.payment_service.controller;

import com.anamul.payment_service.feignClient.OrderServiceClient;
import com.anamul.payment_service.io.OrderEntity;
import com.anamul.payment_service.io.OrderResponse;
import com.anamul.payment_service.service.KafkaPaymentProducer;
import com.anamul.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderServiceClient orderService;
    private final KafkaPaymentProducer kafkaPaymentProducer;

    @Value("${frontend.base.url}")
    private  String Frontend_Base_URL;

    @PostMapping
    public String initiatePayment(@RequestBody OrderEntity orderEntity) {
        return paymentService.initiatePayment(orderEntity);
    }



    public PaymentController(PaymentService paymentService, OrderServiceClient orderService, KafkaPaymentProducer kafkaPaymentProducer) {
        this.paymentService = paymentService;
        this.orderService = orderService;
        this.kafkaPaymentProducer = kafkaPaymentProducer;
    }




    @PostMapping("/success")
    public ResponseEntity<String> paymentSuccess(@RequestParam Map<String,String> payload){
        String tranId = payload.get("tran_id");
        String valId = payload.get("val_id");

        System.out.println("orderId : " + tranId + " "+ "varify try");

        boolean verified = paymentService.verifyPayment(tranId, valId);
        System.out.println("orderId : " + tranId + " "+ "varified");


        if (verified) {

            orderService.updatePaymentStatus(tranId, "PAID");
            OrderResponse orderResponse= orderService.getOrderById(tranId);
            ///kafka producer
            kafkaPaymentProducer.sendPaymentSuccessEvent(orderResponse);

        } else {
            orderService.updatePaymentStatus(tranId, "FAILED");
        }

        String redirectUrl =
                Frontend_Base_URL + "/order/placeOrder?tranId=" + tranId;

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUrl)
                .build();

    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> paymentVerificationSendToFrontend(@RequestParam Map<String,String> payload){

        String tranId = payload.get("tranId");

        Map<String, Object> res = new HashMap<>();
        OrderResponse order = orderService.getOrderById(tranId);
        System.out.println("tran id ---> " + tranId + " status---->" + order.getPaymentStatus());

        if (order != null && "PAID".equals(order.getPaymentStatus())) {

            res.put("status", "success");
            res.put("order", order);
        } else {
            res.put("status", "failed");
        }

        System.out.println(res.get("status"));

        return ResponseEntity.ok(res);

    }


    @PostMapping("/fail")
    public ResponseEntity<Void> paymentFail(@RequestParam Map<String, String> payload) {
        String tranId = payload.get("tran_id");
        String status = payload.get("status");

        System.out.println("Payment failed for transaction: " + tranId + " status: " + status);

        // Update your order status
        orderService.updatePaymentStatus(tranId, "FAILED");


        String frontendUrl = Frontend_Base_URL +"/order/placeOrder?tranId=" + tranId;
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, frontendUrl)
                .build();
    }


    @PostMapping("/cancel")
    public ResponseEntity<Void> paymentCancelHandle(@RequestParam Map<String, String> payload) {
        String tranId = payload.get("tran_id");
        String status = payload.get("status");

        System.out.println("Payment cancel for transaction: " + tranId + " status: " + status);
        orderService.updatePaymentStatus(tranId, "CANCEL");

        String frontendUrl = Frontend_Base_URL + "/order/placeOrder";
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, frontendUrl)
                .build();
    }



    @PostMapping("/ipn")
    public ResponseEntity<String> paymentSuccessIPN(@RequestParam Map<String,String> payload){
        String tranId = payload.get("tran_id");
        String valId = payload.get("val_id");
        System.out.println("hit by ipn" + tranId);

        return ResponseEntity.ok("OK");


    }

    @PostMapping("/test")
    public ResponseEntity<String> paymentSuccess(@RequestBody OrderResponse orderResponse){

        kafkaPaymentProducer.sendPaymentSuccessEvent(orderResponse);

        return ResponseEntity.ok("ok");

    }

}
