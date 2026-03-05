package com.anamul.order_service.repository;

import com.anamul.order_service.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, String> {
    List<OrderEntity> findByUserId(String userId);

}
