package com.example.demo.service.impl;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<Task> getBooks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        taskRepository.deleteById(id);
        return task;
    }

    @Override
    public Task updateTask(Long id, Task newTask) {
        Task task = taskRepository.findById(id).orElse(null);

        newTask.setId(id);
        taskRepository.save(newTask);
        return newTask;
    }
}
