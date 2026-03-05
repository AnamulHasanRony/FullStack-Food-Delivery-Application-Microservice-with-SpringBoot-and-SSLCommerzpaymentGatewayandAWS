package com.anamul.payment_service.service;

import com.anamul.payment_service.client.SslCommerzClient;
import com.anamul.payment_service.io.OrderEntity;
import com.anamul.payment_service.io.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SslCommerzPaymentServiceImplementation implements PaymentService {

    @Autowired
    private SslCommerzClient sslCommerzClient;
    @Override
    public String initiatePayment(OrderEntity orderEntity) {

        Map<String, String> params = new HashMap<>();

        params.put("tran_id", orderEntity.getId());
        params.put("total_amount", String.valueOf(orderEntity.getTotalAmount()));
        params.put("currency", "BDT");


        params.put("cus_name", orderEntity.getCustomerName());
        params.put("cus_email", orderEntity.getCustomerEmail());
        params.put("cus_phone", orderEntity.getCustomerPhoneNo());
        params.put("cus_country", orderEntity.getCustomerCountry());
        params.put("cus_state", orderEntity.getCustomerState());
        params.put("cus_city", orderEntity.getCustomerCity());
        params.put("cus_add1", orderEntity.getCustomerAddress());

        params.put("product_profile", "general");
        params.put("product_name", "Food Order");
        params.put("product_category", "Food");
        params.put("cus_postcode","1000");

        int index = 0;
        for (OrderItem item : orderEntity.getOrderedItem()) {
            params.put("cart[" + index + "][product]", item.getName());
            params.put("cart[" + index + "][amount]", String.valueOf(item.getPrice()));
            params.put("cart[" + index + "][quantity]", String.valueOf(item.getQuantity()));
            index++;
        }


        params.put("shipping_method", "NO");
        System.out.println("param: " + params);

        return sslCommerzClient.createSession(params);
        
    }

    @Override
    public boolean verifyPayment(String tranId, String valId) {
        return sslCommerzClient.verifyPayment(tranId, valId);
    }

}
