package com.example.TaskManagerSystemApi.dto;

import com.example.TaskManagerSystemApi.entity.Comment;
import com.example.TaskManagerSystemApi.entity.enums.TaskPriority;
import com.example.TaskManagerSystemApi.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private String author;
    private String assignee;
    private List<Comment> comments;
}
