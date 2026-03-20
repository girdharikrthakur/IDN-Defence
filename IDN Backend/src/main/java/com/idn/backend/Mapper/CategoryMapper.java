package com.idn.backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.dto.request.CategoryRequestDTO;
import com.idn.backend.dto.response.CategoryResponseDTO;
import com.idn.backend.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDTO toCategoryResponseDTO(Category category);

    List<CategoryResponseDTO> toCategoryResponseDTOList(List<Category> categories);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "active", ignore = true)
    Category toCategory(CategoryRequestDTO dto);
}
