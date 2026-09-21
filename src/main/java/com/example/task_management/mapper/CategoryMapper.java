package com.example.task_management.mapper;

import com.example.task_management.commons.CustomPageResponse;
import com.example.task_management.dto.CategoryRequest;
import com.example.task_management.dto.CategoryResponse;
import com.example.task_management.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category toEntity(CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .build();
    }

    public CustomPageResponse<CategoryResponse> toPagedResponse(Page<Category> categoryPage) {
        List<CategoryResponse> responses = categoryPage.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return CustomPageResponse.resolvePageResponse(
                responses,
                categoryPage.getTotalElements(),
                categoryPage.getPageable()
        );
    }
}