package com.idn.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.request.CategoryRequestDTO;
import com.idn.backend.dto.response.ApiResponse;
import com.idn.backend.dto.response.CategoryResponseDTO;
import com.idn.backend.service.impl.CategoryServiceImpl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> addNewCategory(@RequestBody @Valid CategoryRequestDTO dto) {

        CategoryResponseDTO response = categoryService.addNewCategory(dto);
        return ResponseEntity.ok().body(new ApiResponse<>("Category added successfully", response));

    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getCategory() {
        List<CategoryResponseDTO> response = categoryService.getCategory();
        return ResponseEntity.ok()
                .body(new ApiResponse<List<CategoryResponseDTO>>("Categories retrieved successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getCategoryById(@PathVariable Long id) {
        CategoryResponseDTO response = categoryService.getCategoryById(id);
        return ResponseEntity.ok()
                .body(new ApiResponse<CategoryResponseDTO>("Category retrieved successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(@PathVariable Long id,
            @RequestBody CategoryRequestDTO dto) {

        CategoryResponseDTO response = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok()
                .body(new ApiResponse<CategoryResponseDTO>("Category updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> deleteCategory(@PathVariable Long id) {

        CategoryResponseDTO deletedCategory = categoryService.deleteCategory(id);

        ApiResponse<CategoryResponseDTO> response = new ApiResponse<CategoryResponseDTO>(
                "Category deleted successfully", deletedCategory);

        return ResponseEntity.ok(response);
    }

}
