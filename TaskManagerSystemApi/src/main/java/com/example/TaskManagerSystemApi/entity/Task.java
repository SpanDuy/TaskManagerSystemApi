package com.example.TaskManagerSystemApi.entity;

import com.example.TaskManagerSystemApi.entity.enums.TaskPriority;
import com.example.TaskManagerSystemApi.entity.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @OneToMany(mappedBy = "task",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @ToString.Exclude
    private List<Comment> comments;

    public void setTask(Task task) {
        if (task.getId() != null) {
            id = task.getId();
        }
        if (task.getTitle() != null) {
            title = task.getTitle();
        }
        if (task.getDescription() != null) {
            description = task.getDescription();
        }
        if (task.getStatus() != null) {
            status = task.getStatus();
        }
        if (task.getPriority() != null) {
            priority = task.getPriority();
        }
        if (task.getAssignee() != null) {
            assignee = task.getAssignee();
        }
    }
}
