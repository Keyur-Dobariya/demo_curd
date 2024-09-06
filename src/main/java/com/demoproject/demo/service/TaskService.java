package com.demoproject.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoproject.demo.model.RegistrationModel;
import com.demoproject.demo.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public RegistrationModel addUser(RegistrationModel registration) {
        if (!repository.findByMobileNo(registration.getMobileNo()).isEmpty() || 
            !repository.getTaskByEmailAddress(registration.getEmailAddress()).isEmpty()) {
            throw new RuntimeException("Mobile number or email address already registered.");
        }
        registration.setUserId(UUID.randomUUID().toString().split("-")[0]);
        return repository.save(registration);
    }

    public List<RegistrationModel> findAllUser() {
        return repository.findAll();
    }

    public RegistrationModel getUserByUserId(String userId) {
        return repository.findById(userId).get();
    }

    public List<RegistrationModel> getUserByMobileNo(String mobileNo) {
        return repository.findByMobileNo(mobileNo);
    }

    public List<RegistrationModel> getUserByEmailAddress(String emailAddress) {
        return repository.getTaskByEmailAddress(emailAddress);
    }

    public RegistrationModel updateUser(RegistrationModel registrationRequest) {
        RegistrationModel existingTask = repository.findById(registrationRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingTask.setUserName(registrationRequest.getUserName());
        existingTask.setMobileNo(registrationRequest.getMobileNo());
        existingTask.setEmailAddress(registrationRequest.getEmailAddress());
        existingTask.setPassword(registrationRequest.getPassword());
        return repository.save(existingTask);
    }

    public String deleteUser(String userId) {
        repository.deleteById(userId);
        return userId + " User deleted from dashboard.";
    }

    public RegistrationModel loginUser(String mobileOrEmail, String password) {
        List<RegistrationModel> users = repository.findByMobileNo(mobileOrEmail);
        if (users.isEmpty()) {
            users = repository.getTaskByEmailAddress(mobileOrEmail);
        }
        if (users.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }
        RegistrationModel user = users.get(0);
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

}
