package com.example.task_management.dto;

import com.example.task_management.enums.TaskPriority;
import com.example.task_management.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;

    // Flat metadata instead of entity instances
    private Long userId;
    private String userFullName;
    private Long categoryId;
    private String categoryName;
}