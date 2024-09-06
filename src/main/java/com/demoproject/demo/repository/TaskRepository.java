package com.demoproject.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.demoproject.demo.model.Task;

public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findBySeverity(int severity);

    @Query("{ assignee: ?0 }")
    List<Task> getTaskByAssignee(String assignee);

}
