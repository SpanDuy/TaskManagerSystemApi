package com.example.TaskManagerSystemApi.controller;

import com.example.TaskManagerSystemApi.dto.CommentRequest;
import com.example.TaskManagerSystemApi.dto.RequestEntity;
import com.example.TaskManagerSystemApi.dto.TaskRequest;
import com.example.TaskManagerSystemApi.dto.TaskResponse;
import com.example.TaskManagerSystemApi.entity.Task;
import com.example.TaskManagerSystemApi.service.TaskService;
import com.example.TaskManagerSystemApi.validator.Validator;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final Validator validator;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAllTasks(@RequestBody @Valid RequestEntity requestEntity) {
        validator.validateFindAllTasks(requestEntity);
        return new ResponseEntity<>(taskService.getTasks(requestEntity), HttpStatus.OK);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<TaskResponse>> findTasksByAuthor(@PathVariable String author,
                                                                @RequestBody RequestEntity requestEntity) {
        validator.validateFindTasksByUser(author, requestEntity);
        return new ResponseEntity<>(taskService.getTasksByAuthor(author, requestEntity), HttpStatus.OK);
    }

    @GetMapping("/assignee/{assignee}")
    public ResponseEntity<List<TaskResponse>> findTasksByAssignee(@PathVariable String assignee,
                                                                  @RequestBody RequestEntity requestEntity) {
        validator.validateFindTasksByUser(assignee, requestEntity);
        return new ResponseEntity<>(taskService.getTasksByAssignee(assignee, requestEntity), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<TaskResponse> saveTask(@RequestBody TaskRequest taskRequest) {
        validator.validateSaveTask(taskRequest);
        return new ResponseEntity<>(taskService.saveTask(taskRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody TaskRequest newTask) {
        validator.validateUpdateTask(newTask);
        return new ResponseEntity<>(taskService.updateTask(newTask), HttpStatus.CREATED);
    }

    @PostMapping("/comment/add")
    public ResponseEntity<TaskResponse> addCommentToTask(@RequestBody CommentRequest commentRequest) {
        validator.validateAddCommentToTask(commentRequest);
        return new ResponseEntity<>(taskService.addCommentToTask(commentRequest), HttpStatus.CREATED);
    }

}
