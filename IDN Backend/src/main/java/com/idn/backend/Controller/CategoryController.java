package com.idn.backend.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.idn.backend.DTO.CategoryRequestDTO;
import com.idn.backend.DTO.CategoryResponseDTO;
import com.idn.backend.Services.CategoryService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/public/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategory() {
        List<CategoryResponseDTO> categories = categoryService.findAllCategory();
        return ResponseEntity.ok(categories);

    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> saveCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO savedCategory = categoryService.saveCatgeory(categoryRequestDTO);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable Long id) {
        CategoryResponseDTO response = categoryService.deleteCategory(id);
        return ResponseEntity.ok(response);
    }
}
