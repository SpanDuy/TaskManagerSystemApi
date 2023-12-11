package com.example.TaskManagerSystemApi.service.impl;

import com.example.TaskManagerSystemApi.dto.CommentRequest;
import com.example.TaskManagerSystemApi.dto.RequestEntity;
import com.example.TaskManagerSystemApi.dto.TaskRequest;
import com.example.TaskManagerSystemApi.dto.TaskResponse;
import com.example.TaskManagerSystemApi.entity.Comment;
import com.example.TaskManagerSystemApi.entity.Task;
import com.example.TaskManagerSystemApi.entity.User;
import com.example.TaskManagerSystemApi.exception.NotFoundException;
import com.example.TaskManagerSystemApi.mapper.CommentMapper;
import com.example.TaskManagerSystemApi.mapper.TaskMapper;
import com.example.TaskManagerSystemApi.repository.CommentRepository;
import com.example.TaskManagerSystemApi.repository.TaskRepository;
import com.example.TaskManagerSystemApi.repository.UserRepository;
import com.example.TaskManagerSystemApi.security.SecurityUtil;
import com.example.TaskManagerSystemApi.service.AuthenticationService;
import com.example.TaskManagerSystemApi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AuthenticationService authenticationService;

    private Example<Task> taskExampleFilter(Task task) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("priority", ExampleMatcher.GenericPropertyMatchers.exact());

        return Example.of(task, matcher);
    }

    @Override
    public List<TaskResponse> getTasks(RequestEntity requestEntity) {
        Task task = TaskMapper.mapToTask(requestEntity.getTaskRequest());
        return taskRepository.findAll(taskExampleFilter(task), requestEntity.getPagination().toPageable())
                .stream().map(TaskMapper::mapToTaskResponse).toList();
    }

    @Override
    public List<TaskResponse> getTasksByAuthor(String author, RequestEntity requestEntity) {
        User user = userRepository.findByUsername(author)
                .orElseThrow(() -> new NotFoundException("AUTHOR NOT FOUND"));

        Task task = TaskMapper.mapToTask(requestEntity.getTaskRequest());
        task.setAuthor(user);
        return taskRepository.findAll(taskExampleFilter(task), requestEntity.getPagination().toPageable())
                .stream().map(TaskMapper::mapToTaskResponse).toList();
    }

    @Override
    public List<TaskResponse> getTasksByAssignee(String assignee, RequestEntity requestEntity) {
        User user = userRepository.findByUsername(assignee)
                .orElseThrow(() -> new NotFoundException("ASSIGNEE NOT FOUND"));

        Task task = TaskMapper.mapToTask(requestEntity.getTaskRequest());
        task.setAssignee(user);
        return taskRepository.findAll(taskExampleFilter(task), requestEntity.getPagination().toPageable())
                .stream().map(TaskMapper::mapToTaskResponse).toList();
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        return TaskMapper.mapToTaskResponse(taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TASK WITH ID = " + id + " NOT FOUND")));
    }

    @Override
    public TaskResponse saveTask(TaskRequest taskRequest) {
        User user = authenticationService.getCurrentUser();
        User assignee = userRepository.findByUsername(taskRequest.getAssignee())
                .orElseThrow(() -> new NotFoundException("ASSIGNEE NOT FOUND"));

        Task task = TaskMapper.mapToTask(taskRequest);
        task.setAuthor(user);
        task.setAssignee(assignee);
        taskRepository.save(task);

        return TaskMapper.mapToTaskResponse(task);
    }

    @Override
    public TaskResponse deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TASK WITH ID = " + id + " NOT FOUND"));
        taskRepository.deleteById(id);
        return TaskMapper.mapToTaskResponse(task);
    }

    @Override
    public TaskResponse updateTask(TaskRequest taskRequest) {
        Task task = taskRepository.findById(taskRequest.getId())
                .orElseThrow(() -> new NotFoundException("TASK WITH ID = " + taskRequest.getId() + " NOT FOUND"));

        if (authenticationService.getCurrentUser().equals(task.getAuthor())) {
            return updateTaskByOwner(task, taskRequest);
        }

        if (authenticationService.getCurrentUser().getUsername().equals(task.getAssignee())) {
            return changeTaskStatus(task, taskRequest);
        }

        return TaskMapper.mapToTaskResponse(task);
    }

    public TaskResponse updateTaskByOwner(Task task, TaskRequest taskRequest) {
        User assignee = userRepository.findByUsername(taskRequest.getAssignee())
                .orElseThrow(() -> new NotFoundException("ASSIGNEE NOT FOUND"));

        task.setTask(TaskMapper.mapToTask(taskRequest));
        task.setAuthor(authenticationService.getCurrentUser());
        task.setAssignee(assignee);

        taskRepository.save(task);

        return TaskMapper.mapToTaskResponse(task);
    }

    public TaskResponse changeTaskStatus(Task task, TaskRequest taskRequest) {
        task.setStatus(taskRequest.getStatus());

        taskRepository.save(task);

        return TaskMapper.mapToTaskResponse(task);
    }

    @Override
    public TaskResponse addCommentToTask(CommentRequest commentRequest) {
        Task task = taskRepository.findById(commentRequest.getTaskId())
                .orElseThrow(() -> new NotFoundException("TASK WITH ID = " + commentRequest.getTaskId() + " NOT FOUND"));

        Comment comment = CommentMapper.mapToComment(commentRequest);
        comment.setTask(task);
        commentRepository.save(comment);

        return TaskMapper.mapToTaskResponse(task);
    }
}
