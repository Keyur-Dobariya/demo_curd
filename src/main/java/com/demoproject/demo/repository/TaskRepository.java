package com.demoproject.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.demoproject.demo.model.RegistrationModel;

public interface TaskRepository extends MongoRepository<RegistrationModel, String> {

    List<RegistrationModel> findByMobileNo(String mobileNo);

    @Query("{ emailAddress: ?0 }")
    List<RegistrationModel> getTaskByEmailAddress(String emailAddress);

}
