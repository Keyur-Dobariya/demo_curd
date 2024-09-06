package com.demoproject.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demoproject.demo.apiService.ApiResponse;
import com.demoproject.demo.model.LoginRequest;
import com.demoproject.demo.model.RegistrationModel;
import com.demoproject.demo.service.TaskService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;


@RestController
public class AuthController {

    @Autowired
    private TaskService service;

    @PostMapping("/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegistrationModel> createUser(@RequestBody RegistrationModel task) {
        try {
            RegistrationModel createdUser = service.addUser(task);
            return new ApiResponse<>(true, "User registered successfully.", createdUser);
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, ex.getMessage(), null);
        }
    }
    
    @GetMapping("/getAllUsers")
    public ApiResponse<List<RegistrationModel>> getAllUser() {
        List<RegistrationModel> users = service.findAllUser();
        return new ApiResponse<>(true, "Users retrieved successfully.", users);
    }

    @GetMapping("/getUserByUserId/{userId}")
    public ApiResponse<RegistrationModel> getUserByUserId(@PathVariable String userId) {
        try {
            RegistrationModel user = service.getUserByUserId(userId);
            return new ApiResponse<>(true, "User retrieved successfully.", user);
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, ex.getMessage(), null);
        }
    }

    @GetMapping("/findUserUsingMobileNo/{mobileNo}")
    public ApiResponse<List<RegistrationModel>> findUserUsingMobileNo(@PathVariable String mobileNo) {
        List<RegistrationModel> users = service.getUserByMobileNo(mobileNo);
        if (users.isEmpty()) {
            return new ApiResponse<>(false, "No user found with the provided mobile number.", null);
        }
        return new ApiResponse<>(true, "User(s) retrieved successfully.", users);
    }

    @GetMapping("/getUserByEmailAddress/{emailAddress}")
    public ApiResponse<List<RegistrationModel>> getUserByEmailAddress(@PathVariable String emailAddress) {
        List<RegistrationModel> users = service.getUserByEmailAddress(emailAddress);
        if (users.isEmpty()) {
            return new ApiResponse<>(false, "No user found with the provided email address.", null);
        }
        return new ApiResponse<>(true, "User(s) retrieved successfully.", users);
    }

    @PutMapping("/updateUser")
    public ApiResponse<RegistrationModel> updateUser(@RequestBody RegistrationModel task) {
        try {
            RegistrationModel updatedUser = service.updateUser(task);
            return new ApiResponse<>(true, "User updated successfully.", updatedUser);
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, ex.getMessage(), null);
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable String userId) {
        try {
            String response = service.deleteUser(userId);
            return new ApiResponse<>(true, "User deleted successfully.", response);
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, ex.getMessage(), null);
        }
    }

    @PostMapping("/login")
    public ApiResponse<RegistrationModel> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            RegistrationModel user = service.loginUser(loginRequest.getMobileOrEmail(), loginRequest.getPassword());
            return new ApiResponse<>(true, "Login successful.", user);
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, ex.getMessage(), null);
        }
    }

}
