package com.anamul.contact_service;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class ContactUsEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String message;
}
