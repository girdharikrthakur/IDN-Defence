package com.idn.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.idn.backend.dto.request.CategoryRequestDTO;
import com.idn.backend.dto.response.CategoryResponseDTO;
import com.idn.backend.entity.Category;
import com.idn.backend.exception.ResourceNotFoundException;
import com.idn.backend.mapper.CategoryMapper;
import com.idn.backend.repo.CategoryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;

    public CategoryResponseDTO addNewCategory(CategoryRequestDTO dto) {
        Category category = categoryMapper.toCategory(dto);
        Category savedCategory = categoryRepo.save(category);
        return categoryMapper.toCategoryResponseDTO(savedCategory);
    }

    public List<CategoryResponseDTO> getCategory() {
        List<Category> categories = categoryRepo.findAllByActiveTrue();
        return categories.stream()
                .map(categoryMapper::toCategoryResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        Optional<Category> category = categoryRepo.findByIdAndActiveTrue(id);
        if (category.isPresent()) {
            return categoryMapper.toCategoryResponseDTO(category.get());
        } else {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
    }

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

    public CategoryResponseDTO deleteCategory(Long id) {

        Category category = categoryRepo.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        category.setActive(false);

        categoryRepo.save(category);

        return categoryMapper.toCategoryResponseDTO(category);
    }

}
