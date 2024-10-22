package com.crud_service.helper;

import com.crud_service.controllers.dto.CategoryDto;
import com.crud_service.entities.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("categoryHelperFromHelper")
public class CategoryHelper {

    @Autowired
    private ModelMapper modelMapper;

    public CategoryHelper() {

    }

    public CategoryDto toModel(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public Category toEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    public List<CategoryDto> toModel(List<Category> categories) {
        return categories.stream().map(this::toModel).collect(Collectors.toList());
    }

    public List<Category> toEntity(List<CategoryDto> categoryDtos) {
        return categoryDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public Page<CategoryDto> toModel(Page<Category> categories) {
        return categories.map(this::toModel);
    }

}
