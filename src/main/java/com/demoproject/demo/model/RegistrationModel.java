package com.demoproject.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationModel {
    @Id
    private String userId;
    private String userName;
    private String mobileNo;
    private String emailAddress;
    private String password;
}
