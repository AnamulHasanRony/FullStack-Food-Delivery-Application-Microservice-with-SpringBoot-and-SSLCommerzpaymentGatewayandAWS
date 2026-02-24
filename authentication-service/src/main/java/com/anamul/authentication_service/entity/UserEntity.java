package com.anamul.authentication_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class UserEntity {
    @Id
    private  String id;
    private  String name;
    private  String email;
    private  String password;
    private String role;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}
