package com.example.TaskManagerSystemApi.repository;

import com.example.TaskManagerSystemApi.entity.Task;
import com.example.TaskManagerSystemApi.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthor(User author, Example<Task> taskExample, Pageable pageable);
    List<Task> findByAssignee(User assignee, Example<Task> taskExample, Pageable pageable);
}
