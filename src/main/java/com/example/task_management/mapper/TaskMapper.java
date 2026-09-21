package com.example.task_management.mapper;

import com.example.task_management.commons.CustomPageResponse;
import com.example.task_management.dto.TaskRequest;
import com.example.task_management.dto.TaskResponse;
import com.example.task_management.entity.Category;
import com.example.task_management.entity.Task;
import com.example.task_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {

    public TaskResponse toResponse(Task task) {
        TaskResponse.TaskResponseBuilder builder = TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate());

        if (task.getUser() != null) {
            builder.userId(task.getUser().getId());
            builder.userFullName(task.getUser().getFirstName() + " " + task.getUser().getLastName());
        }

        if (task.getCategory() != null) {
            builder.categoryId(task.getCategory().getId());
            builder.categoryName(task.getCategory().getName());
        }

        return builder.build();
    }

    public Task toEntity(TaskRequest request, User user, Category category) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .user(user)
                .category(category)
                .build();
    }

    public CustomPageResponse<TaskResponse> toPagedResponse(Page<Task> taskPage) {
        List<TaskResponse> responses = taskPage.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return CustomPageResponse.resolvePageResponse(
                responses,
                taskPage.getTotalElements(),
                taskPage.getPageable()
        );
    }
}