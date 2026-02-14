package com.idn.backend.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.DTO.CategoryRequestDTO;
import com.idn.backend.DTO.CategoryResponseDTO;
import com.idn.backend.Model.Category;

public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Category toEntity(CategoryRequestDTO dto);

    CategoryResponseDTO toResponseDTO(Category category);

    List<CategoryResponseDTO> toResponseDTO(List<Category> categories);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Category updateCategoryFromDTO(CategoryRequestDTO categoryRequestDTO, Category category);

}
