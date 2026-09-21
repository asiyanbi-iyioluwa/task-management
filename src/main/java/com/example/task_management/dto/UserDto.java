package com.example.task_management.dto;

import com.example.task_management.entity.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "instance")
@NoArgsConstructor
@Schema(description = "User details payload")
public class UserDto {

    @Schema(description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Full name of the user", example = "Alex Mercer", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @Schema(description = "Full name of the user", example = "Alex Mercer", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Schema(description = "Email address", example = "alex@example.com")
    private String email;

    private List<Task> tasks = new ArrayList<>();

    private LocalDateTime createdAt;

    // Getters and Setters
}