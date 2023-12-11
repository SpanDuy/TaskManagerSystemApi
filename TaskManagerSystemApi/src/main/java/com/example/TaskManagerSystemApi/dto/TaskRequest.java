package com.example.TaskManagerSystemApi.dto;

import com.example.TaskManagerSystemApi.entity.enums.TaskPriority;
import com.example.TaskManagerSystemApi.entity.enums.TaskStatus;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    @Email(message = "Email doesnt exist")
    private String assignee;
}
