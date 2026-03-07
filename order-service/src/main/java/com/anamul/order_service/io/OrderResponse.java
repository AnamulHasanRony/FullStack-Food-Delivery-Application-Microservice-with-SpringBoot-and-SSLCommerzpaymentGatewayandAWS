package com.anamul.order_service.io;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponse {

    private String id;
    private String userId;
    private List<OrderItem> orderedItem;
    private String paymentStatus;
    private String customerName;
    private String customerPhoneNo;
    private String customerAddress;
    private String customerCountry;
    private String customerEmail;
    private String customerCity;
    private String customerState;
    private  String orderStatus;
    private double totalAmount;


}
