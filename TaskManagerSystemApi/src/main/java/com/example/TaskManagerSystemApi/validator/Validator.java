package com.example.TaskManagerSystemApi.validator;

import com.example.TaskManagerSystemApi.dto.CommentRequest;
import com.example.TaskManagerSystemApi.dto.RequestEntity;
import com.example.TaskManagerSystemApi.dto.TaskRequest;
import com.example.TaskManagerSystemApi.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class Validator {
    public void validateFindAllTasks(RequestEntity requestEntity) {
        if (requestEntity.getTaskRequest() == null) {
            throw new BadRequestException("Task Request is required");
        }
        if (requestEntity.getPagination() == null) {
            throw new BadRequestException("Pagination is required");
        }
        if (requestEntity.getPagination().getPage() == null) {
            throw new BadRequestException("Page is required");
        }
        if (requestEntity.getPagination().getPage() < 0) {
            throw new BadRequestException("Page is not negative");
        }
        if (requestEntity.getPagination().getSize() == null) {
            throw new BadRequestException("Size is required");
        }
        if (requestEntity.getPagination().getSize() < 1) {
            throw new BadRequestException("Size is positive");
        }
    }

    public void validateFindTasksByUser(String user, RequestEntity requestEntity) {
        validateFindAllTasks(requestEntity);
        if (user == null || user.equals("")) {
            throw new BadRequestException("User is required");
        }
    }

    public void validateSaveTask(TaskRequest taskRequest) {
        if (taskRequest.getTitle() == null || taskRequest.getTitle().equals("")) {
            throw new BadRequestException("Title is required");
        }
        if (taskRequest.getDescription() == null || taskRequest.getDescription().equals("")) {
            throw new BadRequestException("Description is required");
        }
        if (taskRequest.getStatus() == null) {
            throw new BadRequestException("Status is required");
        }
        if (taskRequest.getPriority() == null) {
            throw new BadRequestException("Priority is required");
        }
        if (taskRequest.getAssignee() == null || taskRequest.getAssignee().equals("")) {
            throw new BadRequestException("Assignee is required");
        }
    }

    public void validateUpdateTask(TaskRequest taskRequest) {
        if (taskRequest.getId() == null) {
            throw new BadRequestException("Id is required");
        }
    }

    public void validateAddCommentToTask(CommentRequest commentRequest) {
        if (commentRequest.getTaskId() == null) {
            throw new BadRequestException("Task Id is required");
        }
        if (commentRequest.getText() == null || commentRequest.getText().equals("")) {
            throw new BadRequestException("Text is required");
        }
    }
}
