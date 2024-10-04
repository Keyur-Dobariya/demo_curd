package com.demoproject.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demoproject.demo.api_service.JwtUtil;
import com.demoproject.demo.model.RegistrationModel;
import com.demoproject.demo.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public RegistrationModel addUser(RegistrationModel registration) {
        if (registration.getFullName().isEmpty()) {
            throw new RuntimeException("Please Enter Fullname");
        }
        if (registration.getEmailAddress().isEmpty()) {
            throw new RuntimeException("Please Enter Email Adress");
        }
        if (registration.getMobileNumber().isEmpty()) {
            throw new RuntimeException("Please Enter Mobile Number");
        }
        if (!repository.findByMobileNumber(registration.getMobileNumber()).isEmpty() || 
            !repository.getTaskByEmailAddress(registration.getEmailAddress()).isEmpty()) {
            throw new RuntimeException("Mobile number or email address already registered.");
        }
        registration.setUserId(UUID.randomUUID().toString().split("-")[0]);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        registration.setCreatedAt(dtf.format(now));
        return repository.save(registration);
    }

    public List<RegistrationModel> findAllUser() {
        return repository.findAll();
    }

    public RegistrationModel getUserByUserId(String userId) {
        return repository.findById(userId).get();
    }

    public List<RegistrationModel> getUserByMobileNumber(String mobileNumber) {
        return repository.findByMobileNumber(mobileNumber);
    }

    public List<RegistrationModel> getUserByEmailAddress(String emailAddress) {
        return repository.getTaskByEmailAddress(emailAddress);
    }

    public RegistrationModel updateUser(RegistrationModel registrationRequest) {
        RegistrationModel existingTask = repository.findById(registrationRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingTask.setUserId(registrationRequest.getUserId() == null ? existingTask.getUserId() : registrationRequest.getUserId());
        existingTask.setFullName(registrationRequest.getFullName() == null ? existingTask.getFullName() : registrationRequest.getFullName());
        existingTask.setEmailAddress(registrationRequest.getEmailAddress() == null ? existingTask.getEmailAddress() : registrationRequest.getEmailAddress());
        existingTask.setMobileNumber(registrationRequest.getMobileNumber() == null ? existingTask.getMobileNumber() : registrationRequest.getMobileNumber());
        existingTask.setPassword(registrationRequest.getPassword() == null ? existingTask.getPassword() : registrationRequest.getPassword());
        existingTask.setUserRole(registrationRequest.getUserRole() == null ? existingTask.getUserRole() : registrationRequest.getUserRole());
        existingTask.setApprovalStatus(registrationRequest.getApprovalStatus() == null ? existingTask.getApprovalStatus() : registrationRequest.getApprovalStatus());
        existingTask.setCreatedAt(registrationRequest.getCreatedAt() == null ? existingTask.getCreatedAt() : registrationRequest.getCreatedAt());
        existingTask.setLastLogin(registrationRequest.getLastLogin() == null ? existingTask.getLastLogin() : registrationRequest.getLastLogin());
        existingTask.setActiveStatus(!registrationRequest.isActiveStatus() ? existingTask.isActiveStatus() : registrationRequest.isActiveStatus());
        existingTask.setMPin(registrationRequest.getMPin() == null ? existingTask.getMPin() : registrationRequest.getMPin());
        existingTask.setLatitude(registrationRequest.getLatitude() == null ? existingTask.getLatitude() : registrationRequest.getLatitude());
        existingTask.setLongitude(registrationRequest.getLongitude() == null ? existingTask.getLongitude() : registrationRequest.getLongitude());
        existingTask.setEnabled(!registrationRequest.isEnabled() ? existingTask.isEnabled() : registrationRequest.isEnabled());
        existingTask.setProfilePhoto(registrationRequest.getProfilePhoto() == null ? existingTask.getProfilePhoto() : registrationRequest.getProfilePhoto());
        existingTask.setDeviceId(registrationRequest.getDeviceId() == null ? existingTask.getDeviceId() : registrationRequest.getDeviceId());
        existingTask.setOnesignalPlayerId(registrationRequest.getOnesignalPlayerId() == null ? existingTask.getOnesignalPlayerId() : registrationRequest.getOnesignalPlayerId());
        existingTask.setUserPermission(registrationRequest.getUserPermission() == null ? existingTask.getUserPermission() : registrationRequest.getUserPermission());
        return repository.save(existingTask);
    }

    @SuppressWarnings("unchecked")
    public RegistrationModel updateUserFields(String userId, Map<String, Object> updates) {
        Optional<RegistrationModel> optionalUser = repository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }

        RegistrationModel user = optionalUser.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "fullName":
                    user.setFullName((String) value);
                    break;
                case "emailAddress":
                    user.setEmailAddress((String) value);
                    break;
                case "mobileNumber":
                    user.setMobileNumber((String) value);
                    break;
                case "password":
                    user.setPassword((String) value);
                    break;
                case "userRole":
                    user.setUserRole((String) value);
                    break;
                case "approvalStatus":
                    user.setApprovalStatus((String) value);
                    break;
                case "createdAt":
                    user.setCreatedAt((String) value);
                    break;
                case "lastLogin":
                    user.setLastLogin((String) value);
                    break;
                case "activeStatus":
                    user.setActiveStatus((boolean) value);
                    break;
                case "mPin":
                    user.setMPin((String) value);
                    break;
                case "latitude":
                    user.setLatitude((String) value);
                    break;
                case "longitude":
                    user.setLongitude((String) value);
                    break;
                case "isEnabled":
                    user.setEnabled((boolean) value);
                    break;
                case "profilePhoto":
                    user.setProfilePhoto((String) value);
                    break;
                case "deviceId":
                    user.setDeviceId((String) value);
                    break;
                case "onesignalPlayerId":
                    user.setOnesignalPlayerId((String) value);
                    break;
                case "userPermission":
                    user.setUserPermission((Map<String, Boolean>) value);
                    break;
                default:
                    throw new RuntimeException("Invalid field: " + key);
            }
        });

        return repository.save(user);
    }

    public String deleteUser(String userId) {
        repository.deleteById(userId);
        return userId + " User deleted from dashboard.";
    }

    public RegistrationModel loginUser(String mobileOrEmail, String password) {
        List<RegistrationModel> users = repository.findByMobileNumber(mobileOrEmail);
        if (users.isEmpty()) {
            users = repository.getTaskByEmailAddress(mobileOrEmail);
        }
        if (users.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }
        RegistrationModel user = users.get(0);
        user.setJwtToken(jwtUtil.generateToken(user.getEmailAddress()));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

}
