package com.idn.backend.services;

import java.util.List;

import com.idn.backend.dto.request.CategoryRequestDTO;
import com.idn.backend.dto.response.CategoryResponseDTO;

public interface CategoryService {

    public CategoryResponseDTO addNewCategory(CategoryRequestDTO dto);

    public List<CategoryResponseDTO> getCategory();

    public CategoryResponseDTO getCategoryById(Long id);

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto);

    public CategoryResponseDTO deleteCategory(Long id);

}
