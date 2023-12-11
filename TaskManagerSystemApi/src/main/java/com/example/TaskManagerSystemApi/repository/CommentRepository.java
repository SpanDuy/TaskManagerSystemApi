package com.example.TaskManagerSystemApi.repository;

import com.example.TaskManagerSystemApi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
