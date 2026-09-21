package com.example.task_management.mapper;

import com.example.task_management.commons.CustomPageResponse;
import com.example.task_management.dto.UserDto;
import com.example.task_management.dto.UserRequest;
import com.example.task_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserDto toResponse(User user){
        return UserDto.instance(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getTasks(), user.getCreatedAt());
    }


    public User toEntity(UserRequest userRequest){
        return User.instance(null,userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(),null);
    }

    public CustomPageResponse<UserDto> toPagedResponse(Page<User> users) {
        List<UserDto> contactResponses = users.getContent().stream().map(this::toResponse).toList();
        long totalElements = users.getTotalElements();
        Pageable pageable = users.getPageable();
        return CustomPageResponse.resolvePageResponse(contactResponses, totalElements, pageable);
    }
}
