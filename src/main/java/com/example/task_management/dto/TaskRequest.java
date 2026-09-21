package com.example.task_management.dto;

import com.example.task_management.enums.TaskPriority;
import com.example.task_management.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDate dueDate;

    @NotNull(message = "User ID is required")
    private Long userId;

    private Long categoryId;

}