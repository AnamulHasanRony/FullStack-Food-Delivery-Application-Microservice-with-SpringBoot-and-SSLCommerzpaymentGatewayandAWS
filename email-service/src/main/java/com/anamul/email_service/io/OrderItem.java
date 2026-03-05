package com.anamul.email_service.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private String foodId;
    private int quantity;
    private double price;
    private String description;
    private String imageUrl;
    private String name;
    private String category;

}
