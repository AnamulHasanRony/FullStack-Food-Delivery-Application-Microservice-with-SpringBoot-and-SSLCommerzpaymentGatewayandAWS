package com.anamul.order_service.io;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private String customerName;
    private String customerPhoneNo;
    private String customerAddress;
    private String customerCountry;
    private String customerCity;
    private String customerState;
    private String customerEmail;
    private List<OrderItem> orderedItem;
    private double totalAmount;
    private String paymentStatus;
    private  String orderStatus;
    private String userId;



}
