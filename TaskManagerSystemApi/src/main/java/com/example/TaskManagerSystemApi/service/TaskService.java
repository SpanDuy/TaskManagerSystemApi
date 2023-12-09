package com.example.TaskManagerSystemApi.service;

import com.example.TaskManagerSystemApi.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> getBooks();
    Task getTaskById(Long id);
    Task saveTask(Task task);
    Task deleteTask(Long id);
    Task updateTask(Long id, Task task);
}
