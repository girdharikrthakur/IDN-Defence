package com.idn.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.TagDTO;
import com.idn.backend.dto.response.ApiResponse;
import com.idn.backend.services.TagService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping()
    public ResponseEntity<ApiResponse<TagDTO>> saveTag(@RequestBody TagDTO dto) {
        TagDTO savedTag = tagService.saveTag(dto);

        ApiResponse<TagDTO> apiResponse = new ApiResponse<>("Tag Saved", savedTag);

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<TagDTO>>> getAllTags() {
        List<TagDTO> tagList = tagService.getAllTags();
        ApiResponse<List<TagDTO>> response = new ApiResponse<>("Fetched All the Tags", tagList);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<TagDTO>> deleteTag(Long id) {
        TagDTO tag = tagService.deleteTag(id);
        ApiResponse<TagDTO> apiResponse = new ApiResponse<>("Tag Deleted", tag);
        return ResponseEntity.ok().body(apiResponse);
    }

}
