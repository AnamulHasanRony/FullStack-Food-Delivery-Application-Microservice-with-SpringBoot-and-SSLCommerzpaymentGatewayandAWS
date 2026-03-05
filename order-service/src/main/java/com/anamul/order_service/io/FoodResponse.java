package com.anamul.order_service.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodResponse {

    private String id;
    private String name;
    private String description;
    private String category;
    private double price;
    private String imageUrl;

}
