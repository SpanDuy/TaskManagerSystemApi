package com.example.TaskManagerSystemApi.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @NotBlank(message = "Text is required")
    private String text;

    @Positive(message = "Task ID must be a positive number")
    @Digits(integer = 10, fraction = 0, message = "Task ID must be a valid number")
    private Long taskId;
}
