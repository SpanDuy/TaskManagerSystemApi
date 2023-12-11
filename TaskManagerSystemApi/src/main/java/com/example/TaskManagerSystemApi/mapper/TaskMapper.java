package com.example.TaskManagerSystemApi.mapper;

import com.example.TaskManagerSystemApi.dto.TaskRequest;
import com.example.TaskManagerSystemApi.dto.TaskResponse;
import com.example.TaskManagerSystemApi.entity.Task;

public class TaskMapper {
    public static Task mapToTask(TaskRequest taskRequest) {
        return Task.builder()
                .id(taskRequest.getId())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(taskRequest.getStatus())
                .priority(taskRequest.getPriority())
                .build();
    }

    public static TaskResponse mapToTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .author(task.getAuthor().getUsername())
                .assignee(task.getAssignee().getUsername())
                .comments(task.getComments())
                .build();
    }
}
