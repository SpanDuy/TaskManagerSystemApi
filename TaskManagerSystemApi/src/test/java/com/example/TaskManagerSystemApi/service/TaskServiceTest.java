package com.example.TaskManagerSystemApi.service;

import com.example.TaskManagerSystemApi.dto.Pagination;
import com.example.TaskManagerSystemApi.dto.RequestEntity;
import com.example.TaskManagerSystemApi.dto.TaskRequest;
import com.example.TaskManagerSystemApi.dto.TaskResponse;
import com.example.TaskManagerSystemApi.entity.Task;
import com.example.TaskManagerSystemApi.entity.User;
import com.example.TaskManagerSystemApi.entity.enums.TaskStatus;
import com.example.TaskManagerSystemApi.mapper.TaskMapper;
import com.example.TaskManagerSystemApi.repository.CommentRepository;
import com.example.TaskManagerSystemApi.repository.TaskRepository;
import com.example.TaskManagerSystemApi.repository.UserRepository;
import com.example.TaskManagerSystemApi.service.impl.TaskServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.TaskManagerSystemApi.entity.enums.TaskPriority.MEDIUM;
import static com.example.TaskManagerSystemApi.entity.enums.TaskStatus.AWAITING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;

import static org.mockito.Mockito.when;

import java.util.*;

@SpringBootTest
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private TaskServiceImpl taskService;

    private User user1;
    private User user2;
    private Task task;
    private String author;
    private String assignee;
    private TaskResponse taskResponse;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        author = "user_1@gmail.com";
        assignee = "user_2@gmail.com";

        user1 = new User(1L, author, "1111", "USER", new ArrayList<>());
        user2 = new User(1L, assignee, "1111", "USER", new ArrayList<>());
        task = new Task(1L, "Task 1", "Complete Project Report", AWAITING, MEDIUM, user1, user2, new ArrayList<>());
        taskResponse = new TaskResponse(1L, "Task 1", "Complete Project Report", AWAITING, MEDIUM, "user_1@gmail.com", "user_2@gmail.com", new ArrayList<>());
        taskRequest = new TaskRequest(1L, "Task 1", "Complete Project Report", AWAITING, MEDIUM, "user_2@gmail.com");

    }

    @Test
    public void testGetTasks() {
        TaskRequest taskRequest = new TaskRequest();
        Pagination pagination = new Pagination(0, 1);
        RequestEntity requestEntity = new RequestEntity(taskRequest, pagination);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        when(taskRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(new PageImpl(tasks));

        List<TaskResponse> taskResponses = new ArrayList<>();
        taskResponses.add(taskResponse);

        assertEquals(taskResponses, taskService.getTasks(requestEntity));
    }

    @Test
    public void testGetTasksByAuthor() {
        TaskRequest taskRequest = new TaskRequest();
        Pagination pagination = new Pagination(0, 1);
        RequestEntity requestEntity = new RequestEntity(taskRequest, pagination);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        when(userRepository.findByUsername(author)).thenReturn(Optional.of(user1));
        when(taskRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(new PageImpl(tasks));

        List<TaskResponse> taskResponses = new ArrayList<>();
        taskResponses.add(taskResponse);

        assertEquals(taskResponses, taskService.getTasksByAuthor(author ,requestEntity));
    }

    @Test
    public void testGetTasksByAssignee() {
        TaskRequest taskRequest = new TaskRequest();
        Pagination pagination = new Pagination(0, 1);
        RequestEntity requestEntity = new RequestEntity(taskRequest, pagination);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        when(userRepository.findByUsername(assignee)).thenReturn(Optional.of(user2));
        when(taskRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(new PageImpl(tasks));


        List<TaskResponse> taskResponses = new ArrayList<>();
        taskResponses.add(taskResponse);

        assertEquals(taskResponses, taskService.getTasksByAssignee(assignee ,requestEntity));
    }

    @Test
    public void testGetTaskTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertEquals(taskResponse, taskService.getTaskById(1L));
    }

    @Test
    public void testSaveTask() {
        taskResponse.setComments(null);

        when(authenticationService.getCurrentUser()).thenReturn(user1);
        when(userRepository.findByUsername(taskRequest.getAssignee())).thenReturn(Optional.of(user2));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertEquals(taskResponse, taskService.saveTask(taskRequest));
    }
}
