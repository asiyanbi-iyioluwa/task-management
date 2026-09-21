package com.example.task_management.service;

import com.example.task_management.dto.TaskRequest;
import com.example.task_management.dto.TaskResponse;
import com.example.task_management.entity.Category;
import com.example.task_management.entity.Task;
import com.example.task_management.enums.TaskPriority;
import com.example.task_management.enums.TaskStatus;
import com.example.task_management.mapper.TaskMapper;
import com.example.task_management.entity.User;
import com.example.task_management.exception.ResourceNotFoundException;
import com.example.task_management.repository.CategoryRepository;
import com.example.task_management.repository.TaskRepository;
import com.example.task_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TaskMapper taskMapper;

    public TaskResponse createTask(TaskRequest request) {
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("userId is required"); }
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException( "User not found with id: " + request.getUserId() ));
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException( "Category not found with id: " + request.getCategoryId() )); }
        Task task = taskMapper.toEntity(request, user, category);
        task.setCreatedAt(LocalDateTime.now());
        Task savedTask = taskRepository.save(task); return taskMapper.toResponse(savedTask);
    }

    public Page<Task> getAllTasks(TaskStatus status, TaskPriority priority, Pageable pageable) {
        if (status != null) {
            return taskRepository.findByStatus(status, pageable);
        }
        if (priority != null) {
            return taskRepository.findByPriority(priority, pageable);
        }
        return taskRepository.findAll(pageable);
    }

    public TaskResponse getTaskById(Long id) {
        Task task = findTaskEntityById(id);
        return taskMapper.toResponse(task);
    }

    private Task findTaskEntityById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public List<Task> getTasksByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return taskRepository.findByUserId(userId);
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = findTaskEntityById(id);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
            task.setCategory(category);
        }
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toResponse(updatedTask);
    }


    public void deleteTask(Long id) {
        Task task = findTaskEntityById(id);
        taskRepository.delete(task);
    }
}