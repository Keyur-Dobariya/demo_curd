package com.demoproject.demo.model;

import java.util.Map;

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
    private String fullName;
    private String emailAddress;
    private String mobileNumber;
    private String password;
    private String userRole;
    private String approvalStatus;
    private String createdAt;
    private String lastLogin;
    private boolean activeStatus;
    private String mPin;
    private String latitude;
    private String longitude;
    private boolean isEnabled;
    private String profilePhoto;
    private String deviceId;
    private String onesignalPlayerId;
    private String jwtToken;
    private Map<String, Boolean> userPermission;
}
