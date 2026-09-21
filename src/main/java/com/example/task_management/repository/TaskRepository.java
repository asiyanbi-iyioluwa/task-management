package com.example.task_management.repository;
import com.example.task_management.enums.TaskPriority;
import com.example.task_management.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.task_management.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
    Page<Task> findByPriority(TaskPriority priority, Pageable pageable);
    List<Task> findByUserId(Long userId);
}