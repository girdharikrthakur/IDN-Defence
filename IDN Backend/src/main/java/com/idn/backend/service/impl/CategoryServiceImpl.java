package com.idn.backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idn.backend.dto.request.CategoryRequestDTO;
import com.idn.backend.dto.response.CategoryResponseDTO;
import com.idn.backend.entity.Category;
import com.idn.backend.exception.ResourceNotFoundException;
import com.idn.backend.mapper.CategoryMapper;
import com.idn.backend.repo.CategoryRepo;
import com.idn.backend.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponseDTO addNewCategory(CategoryRequestDTO dto) {
        Category category = categoryMapper.toCategory(dto);
        Category savedCategory = categoryRepo.save(category);
        return categoryMapper.toCategoryResponseDTO(savedCategory);
    }

    @Override
    public List<CategoryResponseDTO> getCategory() {
        List<Category> categories = categoryRepo.findAllByActiveTrue();
        return categories.stream()
                .map(categoryMapper::toCategoryResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Optional<Category> category = categoryRepo.findByIdAndActiveTrue(id);
        if (category.isPresent()) {
            return categoryMapper.toCategoryResponseDTO(category.get());
        } else {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        Optional<Category> existingCategory = categoryRepo.findByIdAndActiveTrue(id);
        if (existingCategory.isPresent()) {
            Category categoryToUpdate = existingCategory.get();
            categoryToUpdate.setName(dto.name());
            Category updatedCategory = categoryRepo.save(categoryToUpdate);
            return categoryMapper.toCategoryResponseDTO(updatedCategory);
        } else {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public CategoryResponseDTO deleteCategory(Long id) {

        Category category = categoryRepo.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        category.setActive(false);

        categoryRepo.save(category);

        return categoryMapper.toCategoryResponseDTO(category);
    }

}
