package com.idn.backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.idn.backend.DTO.CategoryRequestDTO;
import com.idn.backend.DTO.CategoryResponseDTO;
import com.idn.backend.Mapper.CategoryMapper;
import com.idn.backend.Model.Category;
import com.idn.backend.Repo.CategoryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepo categoryRepo;

    public CategoryResponseDTO saveCatgeory(CategoryRequestDTO categoryRequestDTO) {

        Optional<Category> existingCategory = categoryRepo.findByName(categoryRequestDTO.getName());
        if (existingCategory.isPresent()) {
            throw new RuntimeException("Category Already Exist");
        }

        Category category = categoryMapper.toEntity(categoryRequestDTO);
        Category savedCategory = categoryRepo.save(category);
        return categoryMapper.toResponseDTO(savedCategory);
    }

    public List<CategoryResponseDTO> findAllCategory() {

        List<Category> category = categoryRepo.findAll();
        return categoryMapper.toResponseDTO(category);
    }

    public CategoryResponseDTO deleteCategory(Long id) {

        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Catehgory not found by id : " + id));
        categoryRepo.deleteById(id);
        return categoryMapper.toResponseDTO(category);

    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The comment not avaliable with id: " + id));
        Category updatedCategory = categoryMapper.updateCategoryFromDTO(dto, category);
        return categoryMapper.toResponseDTO(updatedCategory);
    }

}
