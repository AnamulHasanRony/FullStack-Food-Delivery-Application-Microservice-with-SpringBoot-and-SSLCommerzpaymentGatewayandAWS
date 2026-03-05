package com.anamul.payment_service.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
     private String id;
     private String userId;
     private String customerEmail;
     private List<OrderItem> orderedItem;
     private double totalAmount;
     private String paymentStatus;
     private String customerName;
     private String customerPhoneNo;
     private String customerAddress;
     private String customerCountry;
     private String customerCity;
     private String customerState;
     private  String orderStatus;

}
