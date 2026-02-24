package com.anamul.food_service.service;


import com.anamul.food_service.io.FoodRequest;
import com.anamul.food_service.io.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {
    String uploadFile(MultipartFile multipartFile);
    FoodResponse addFood(FoodRequest foodRequest, MultipartFile multipartFile);
    List<FoodResponse> getAllFoods();
    FoodResponse getFoodById(String id);
    void deleteFoodById(String id);

}
