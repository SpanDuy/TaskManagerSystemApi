package com.example.TaskManagerSystemApi.controller;

import com.example.TaskManagerSystemApi.entity.Task;
import com.example.TaskManagerSystemApi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<Task> findAllTasks() {
        return taskService.getBooks();
    }

    @GetMapping("/{id}")
    public Task findTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/add")
    public Task saveTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @DeleteMapping("/{id}")
    public Task deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    @PutMapping("/update/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

}
