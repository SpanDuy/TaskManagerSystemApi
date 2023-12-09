package com.example.TaskManagerSystemApi.repository;

import com.example.TaskManagerSystemApi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
