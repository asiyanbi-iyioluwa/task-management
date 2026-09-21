package com.example.task_management.controller;

import com.example.task_management.dto.TaskRequest;
import com.example.task_management.dto.TaskResponse;
import com.example.task_management.entity.Task;
import com.example.task_management.enums.TaskPriority;
import com.example.task_management.enums.TaskStatus;
import com.example.task_management.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task endpoint", description = "This endpoint handles operations related to tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Create a Task", description = "This endpoint creates a task")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.CREATED);
    }

    // Handles:
    // - GET /api/tasks
    // - GET /api/tasks?status=COMPLETED
    // - GET /api/tasks?priority=HIGH
    // - GET /api/tasks?page=0&size=10&sort=dueDate,desc
    @GetMapping
    @Operation(summary = "Get all Task", description = "This endpoint Shows all the task in the database")
    public ResponseEntity<Page<Task>> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasks(status, priority, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific Task", description = "This endpoint Shows the specific task with the task ID")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Task", description = "This endpoint updates a task in the database")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Task", description = "This endpoint deletes a task from the database")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}