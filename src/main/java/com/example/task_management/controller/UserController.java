package com.example.task_management.controller;

import com.example.task_management.commons.CustomPageResponse;
import com.example.task_management.dto.UserDto;
import com.example.task_management.dto.UserRequest;
import com.example.task_management.entity.Task;
import com.example.task_management.entity.User;
import com.example.task_management.service.TaskService;
import com.example.task_management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User endpoint", description = "This endpoint handles operations related to users.")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

//    public UserController(UserService userService, TaskService taskService) {
//        this.userService = userService;
//        this.taskService = taskService;
//    }

    @PostMapping
    @Operation(summary = "Create a user", description = "This endpoint creates a user.")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRequest request) {
        UserDto createdUser = userService.createUser(request);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "List all users", description = "This endpoint lists all users in the database in a paginated format.")
    public ResponseEntity<CustomPageResponse<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(userService.getAllUsers(pageNo,pageSize));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "This endpoint Gets a user using the user's ID.")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "This endpoint Updates a user's data in the database.")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "This endpoint deletes a user from the database.")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint for: GET /api/users/{id}/tasks
    @GetMapping("/{id}/tasks")
    @Operation(summary = "Get a user's task", description = "This endpoint get all tha tasks a user has.")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTasksByUserId(id));
    }
}