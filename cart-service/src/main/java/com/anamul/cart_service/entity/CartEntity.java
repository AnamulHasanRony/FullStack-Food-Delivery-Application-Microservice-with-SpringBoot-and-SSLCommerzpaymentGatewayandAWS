package com.anamul.cart_service.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document(collection = "cartEntity")
public class CartEntity {
    @Id
    private String id;
    private  String userId;
    private Map<String,Integer> items= new HashMap<>();
    public CartEntity(String userId, Map<String, Integer>items){
        this.userId=userId;
        this.items=items;

    }

}
