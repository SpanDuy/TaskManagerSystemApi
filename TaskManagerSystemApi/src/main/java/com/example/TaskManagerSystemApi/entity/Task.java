package com.example.TaskManagerSystemApi.entity;

import com.example.TaskManagerSystemApi.entity.enums.TaskPriority;
import com.example.TaskManagerSystemApi.entity.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private String author;

    private String assignee;
}
