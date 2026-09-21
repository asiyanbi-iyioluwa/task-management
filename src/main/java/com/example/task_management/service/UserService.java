package com.example.task_management.service;

import com.example.task_management.commons.CustomPageResponse;
import com.example.task_management.dto.UserDto;
import com.example.task_management.dto.UserRequest;
import com.example.task_management.entity.User;
import com.example.task_management.exception.ResourceNotFoundException;
import com.example.task_management.mapper.UserMapper;
import com.example.task_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Constructor Injection (instead of @Autowired)
//    public UserService(UserRepository userRepository, UserMapper userMapper) {
//        this.userRepository = userRepository;
//        this.userMapper = userMapper;
//    }

    public UserDto createUser(UserRequest request) {
        User user = userMapper.toEntity(request);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    public CustomPageResponse<UserDto> getAllUsers(int pageNo,int pageSize ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Page<User> users = userRepository.findAll(pageable);
        return userMapper.toPagedResponse(users);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));

        return userMapper.toResponse(user);
    }


    public UserDto updateUser(Long id, UserRequest request) {
        User user = userMapper.toEntity(request);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userMapper.toResponse(user);
    }
}