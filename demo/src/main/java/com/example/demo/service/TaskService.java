package com.example.demo.service;

import com.example.demo.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> getBooks();
    Task getTaskById(Long id);
    Task saveTask(Task task);
    Task deleteTask(Long id);
    Task updateTask(Long id, Task task);
}
