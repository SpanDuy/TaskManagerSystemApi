package com.example.TaskManagerSystemApi.service;

import com.example.TaskManagerSystemApi.dto.CommentRequest;
import com.example.TaskManagerSystemApi.dto.RequestEntity;
import com.example.TaskManagerSystemApi.dto.TaskRequest;
import com.example.TaskManagerSystemApi.dto.TaskResponse;
import com.example.TaskManagerSystemApi.entity.Task;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getTasks(RequestEntity requestEntity);
    List<TaskResponse> getTasksByAuthor(String author, RequestEntity requestEntity);
    List<TaskResponse> getTasksByAssignee(String assignee, RequestEntity requestEntity);
    TaskResponse getTaskById(Long id);
    TaskResponse saveTask(TaskRequest taskRequest);
    TaskResponse deleteTask(Long id);
    TaskResponse updateTask(TaskRequest taskRequest);
    TaskResponse addCommentToTask(CommentRequest commentRequest);
}
