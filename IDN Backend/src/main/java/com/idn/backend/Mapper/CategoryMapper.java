package com.idn.backend.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.DTO.RequestDTO.CategoryRequestDTO;
import com.idn.backend.DTO.ResponseDTO.CategoryResponseDTO;
import com.idn.backend.Model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDTO toCategoryResponseDTO(Category category);

    List<CategoryResponseDTO> toCategoryResponseDTOList(List<Category> categories);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Category toCategory(CategoryRequestDTO dto);
}
