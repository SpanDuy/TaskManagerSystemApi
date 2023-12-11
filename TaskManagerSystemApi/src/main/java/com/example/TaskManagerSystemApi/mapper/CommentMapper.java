package com.example.TaskManagerSystemApi.mapper;

import com.example.TaskManagerSystemApi.dto.CommentRequest;
import com.example.TaskManagerSystemApi.entity.Comment;

public class CommentMapper {
    public static Comment mapToComment(CommentRequest commentRequest) {
        return Comment.builder()
                .text(commentRequest.getText())
                .build();
    }
}
