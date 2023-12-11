package com.example.TaskManagerSystemApi.controller;

import com.example.TaskManagerSystemApi.dto.CommentRequest;
import com.example.TaskManagerSystemApi.dto.RequestEntity;
import com.example.TaskManagerSystemApi.dto.TaskRequest;
import com.example.TaskManagerSystemApi.dto.TaskResponse;
import com.example.TaskManagerSystemApi.entity.Task;
import com.example.TaskManagerSystemApi.exception.BadRequestException;
import com.example.TaskManagerSystemApi.exception.NotFoundException;
import com.example.TaskManagerSystemApi.exception.ResponseException;
import com.example.TaskManagerSystemApi.service.TaskService;
import com.example.TaskManagerSystemApi.validator.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Find all tasks", description = "Get a list of tasks based on the provided criteria, support filtration and pagination")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = {
                            @ExampleObject(name = "Without parameters", value = "{\"taskRequest\": {\"id\": null, \"title\": null, \"description\": null, \"status\": null, \"priority\": null, \"assignee\": null}, \"pagination\": {\"page\": 0, \"size\": 2}}"),
                            @ExampleObject(name = "With title", value = "{\"taskRequest\": {\"id\": null, \"title\": \"Another Task\", \"description\": null, \"status\": null, \"priority\": null, \"assignee\": null}, \"pagination\": {\"page\": 0, \"size\": 2}}"),
                            @ExampleObject(name = "With description", value = "{\"taskRequest\": {\"id\": null, \"title\": null, \"description\": \"This is another task description\", \"status\": null, \"priority\": null, \"assignee\": null}, \"pagination\": {\"page\": 0, \"size\": 2}}"),
                            @ExampleObject(name = "With status", value = "{\"taskRequest\": {\"id\": null, \"title\": null, \"description\": null, \"status\": \"IN_PROGRESS\", \"priority\": null, \"assignee\": null}, \"pagination\": {\"page\": 0, \"size\": 2}}"),
                            @ExampleObject(name = "With priority", value = "{\"taskRequest\": {\"id\": null, \"title\": null, \"description\": null, \"status\": null, \"priority\": \"MEDIUM\", \"assignee\": null}, \"pagination\": {\"page\": 0, \"size\": 2}}"),
                            @ExampleObject(name = "With status and priority", value = "{\"taskRequest\": {\"id\": null, \"title\": null, \"description\": null, \"status\": \"IN_PROGRESS\", \"priority\": \"MEDIUM\", \"assignee\": null}, \"pagination\": {\"page\": 0, \"size\": 2}}"),
                            @ExampleObject(name = "With title and priority", value = "{\"taskRequest\": {\"id\": null, \"title\": \"Another Task\", \"description\": null, \"status\": null, \"priority\": \"MEDIUM\", \"assignee\": null}, \"pagination\": {\"page\": 0, \"size\": 2}}")
                    }
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of tasks",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class),
                            examples = {
                                    @ExampleObject(name = "Two tasks with comments", value = "[{\"id\": 452, \"title\": \"Another Task\", \"description\": \"This is another task description\", \"status\": \"IN_PROGRESS\", \"priority\": \"MEDIUM\", \"author\": \"vlad\", \"assignee\": \"hdhrbux@gmail.com\", \"comments\": [{\"id\": 6, \"text\": \"Hello, World!\"}, {\"id\": 7, \"text\": \"Nice task\"}]}, {\"id\": 453, \"title\": \"Another Task\", \"description\": \"This is \", \"status\": \"COMPLETED\", \"priority\": \"MEDIUM\", \"author\": \"hdhrbux@gmail.com\", \"assignee\": \"vlad\", \"comments\": [{\"id\": 2, \"text\": \"qwerty\"}, {\"id\": 3, \"text\": \"qwerty\"}, {\"id\": 4, \"text\": \"qwerty\"}, {\"id\": 5, \"text\": \"qwerty\"}]}]"),
                                    @ExampleObject(name = "When no tasks", value = "[]")
                            }
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ResponseException.class),
                            examples = {
                                    @ExampleObject(name = "With not positive size of page", value = "{\"message\": \"Size is positive\"}"),
                                    @ExampleObject(name = "With negative number of page", value = "{\"message\": \"Page is not negative\"}"),
                                    @ExampleObject(name = "When pagination is null", value = "{\"message\": \"Pagination is required\"}"),
                                    @ExampleObject(name = "When task request is null", value = "{\"message\": \"Task Request is required\"}")
                            }
                    )
            )
    })
    @PostMapping
    public ResponseEntity<List<TaskResponse>> findAllTasks(@RequestBody RequestEntity requestEntity) {
        validator.validateFindAllTasks(requestEntity);
        return new ResponseEntity<>(taskService.getTasks(requestEntity), HttpStatus.OK);
    }

    @Operation(summary = "Find tasks by author", description = "Get a list of tasks created by a specific author, support filtration and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of tasks", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseException.class)))
    })
    @PostMapping("/author/{author}")
    public ResponseEntity<List<TaskResponse>> findTasksByAuthor(
            @Parameter(description = "The author's username", required = true)
            @PathVariable String author,
            @RequestBody RequestEntity requestEntity) {
        validator.validateFindTasksByUser(author, requestEntity);
        return new ResponseEntity<>(taskService.getTasksByAuthor(author, requestEntity), HttpStatus.OK);
    }

    @Operation(summary = "Find tasks by assignee", description = "Get a list of tasks assigned to a specific user, support filtration and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of tasks", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseException.class)))
    })
    @PostMapping("/assignee/{assignee}")
    public ResponseEntity<List<TaskResponse>> findTasksByAssignee(
            @Parameter(description = "The assignee's username", required = true)
            @PathVariable String assignee,
            @RequestBody RequestEntity requestEntity) {
        validator.validateFindTasksByUser(assignee, requestEntity);
        return new ResponseEntity<>(taskService.getTasksByAssignee(assignee, requestEntity), HttpStatus.OK);
    }

    @Operation(summary = "Find task by ID", description = "Get details of a task by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the task", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ResponseException.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findTaskById(
            @Parameter(description = "The ID of the task", required = true)
            @PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @Operation(summary = "Add a new task", description = "Create a new task based on the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseException.class)))
    })
    @PostMapping("/add")
    public ResponseEntity<TaskResponse> saveTask(
            @Parameter(description = "Details of the task to be created", required = true)
            @RequestBody TaskRequest taskRequest) {
        validator.validateSaveTask(taskRequest);
        return new ResponseEntity<>(taskService.saveTask(taskRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a task by ID", description = "Delete a task based on its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully", content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ResponseException.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> deleteTask(
            @Parameter(description = "The ID of the task to be deleted", required = true)
            @PathVariable Long id) {
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update an existing task", description = "Modify an existing task based on the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task updated successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseException.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<TaskResponse> updateTask(
            @Parameter(description = "Details of the task to be updated", required = true)
            @RequestBody TaskRequest newTask) {
        validator.validateUpdateTask(newTask);
        return new ResponseEntity<>(taskService.updateTask(newTask), HttpStatus.CREATED);
    }

    @Operation(summary = "Add a comment to a task", description = "Create and add a comment to an existing task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment added successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseException.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseException.class)))
    })
    @PostMapping("/comment/add")
    public ResponseEntity<TaskResponse> addCommentToTask(
            @Parameter(description = "Details of the comment to be added", required = true)
            @RequestBody CommentRequest commentRequest) {
        validator.validateAddCommentToTask(commentRequest);
        return new ResponseEntity<>(taskService.addCommentToTask(commentRequest), HttpStatus.CREATED);
    }

}
