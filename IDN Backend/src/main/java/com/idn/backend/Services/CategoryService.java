package com.idn.backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.idn.backend.DTO.CategoryRequestDTO;
import com.idn.backend.DTO.CategoryResponseDTO;
import com.idn.backend.Mapper.CategoryMapper;
import com.idn.backend.Model.Category;
import com.idn.backend.Repo.CategoryRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepo categoryRepo;

    public List<CategoryResponseDTO> findAllCategory() {

        List<Category> category = categoryRepo.findAll();
        return categoryMapper.toResponseDTO(category);
    }

    public CategoryResponseDTO saveCatgeory(CategoryRequestDTO categoryRequestDTO) {

        Optional<Category> existingCategory = categoryRepo.findByName(categoryRequestDTO.getName());
        if (existingCategory.isPresent()) {
            return categoryMapper.toResponseDTO(existingCategory.get());
        }
        Category category = categoryMapper.toEntity(categoryRequestDTO);
        Category savedCategory = categoryRepo.save(category);
        return categoryMapper.toResponseDTO(savedCategory);
    }

}
