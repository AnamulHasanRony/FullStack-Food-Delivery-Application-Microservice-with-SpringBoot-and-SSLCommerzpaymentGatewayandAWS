package com.anamul.contact_service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository extends MongoRepository<ContactUsEntity, String> {
}
