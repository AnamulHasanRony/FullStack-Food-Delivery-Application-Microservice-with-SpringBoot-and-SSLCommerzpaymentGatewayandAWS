package com.anamul.food_service.service;


import com.anamul.food_service.entity.FoodEntity;
import com.anamul.food_service.io.FoodRequest;
import com.anamul.food_service.io.FoodResponse;
import com.anamul.food_service.repository.FoodRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FoodServiceImplementation implements FoodService {
    @Value("${aws.bucketname}")
    private String bucketName;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        String filenameExtension= multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.')+1);
        String key= UUID.randomUUID().toString()+"."+filenameExtension;

        try {
            PutObjectRequest putObjectRequest=PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .acl("public-read")
                    .contentType(multipartFile.getContentType())
                    .build();
            PutObjectResponse putObjectResponse=s3Client.putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
            if(putObjectResponse.sdkHttpResponse().isSuccessful()){
                return "https://"+bucketName+".s3.amazonaws.com/"+key;
            }
            else{
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error while uploading image");
            }
        }
        catch(IOException ioException){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error while uploading image");
        }

    }

    @Override
    public FoodResponse addFood(FoodRequest foodRequest, MultipartFile multipartFile) {
        FoodEntity foodEntity=convertFoodRequestToFoodEntity(foodRequest);
        String imageUrl=uploadFile(multipartFile);
        foodEntity.setImageUrl(imageUrl);
        foodEntity=foodRepository.save(foodEntity);
        return convertFoodEntityToFoodResponse(foodEntity);
    }

    @Override
    public List<FoodResponse> getAllFoods() {
        List<FoodEntity> foodEntityList= foodRepository.findAll();
        return foodEntityList.stream().map(foodEntity -> convertFoodEntityToFoodResponse(foodEntity)).collect(Collectors.toList());

    }

    @Override
    public FoodResponse getFoodById(String id) {
        FoodEntity foodEntity=foodRepository.findById(id).orElseThrow(()-> new RuntimeException("Food id "+id+" do not exist"));
        return convertFoodEntityToFoodResponse(foodEntity);
    }

    @Override
    public void deleteFoodById(String id) {
        FoodResponse foodResponse=getFoodById(id);
        String imageUrl=foodResponse.getImageUrl();
        String filename=imageUrl.substring(imageUrl.lastIndexOf("/"));
        if(deleteFoodImageFromAWS(filename)){
            foodRepository.deleteById(id);
        }

    }

    private FoodEntity convertFoodRequestToFoodEntity(FoodRequest foodRequest){
        return FoodEntity.builder()
                .name(foodRequest.getName())
                .description(foodRequest.getDescription())
                .category(foodRequest.getCategory())
                .price(foodRequest.getPrice())
                .build();

    }


    private FoodResponse convertFoodEntityToFoodResponse(FoodEntity foodEntity){
        return FoodResponse.builder()
                .id(foodEntity.getId())
                .name(foodEntity.getName())
                .description(foodEntity.getDescription())
                .category(foodEntity.getCategory())
                .price(foodEntity.getPrice())
                .imageUrl(foodEntity.getImageUrl())
                .build();

    }
    private Boolean deleteFoodImageFromAWS(String filename){
        DeleteObjectRequest deleteObjectRequest=DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
        return true;
    }


}
