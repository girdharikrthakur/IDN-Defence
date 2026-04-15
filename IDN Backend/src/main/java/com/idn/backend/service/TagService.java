package com.idn.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.idn.backend.dto.TagDTO;
import com.idn.backend.entity.Tag;
import com.idn.backend.mapper.TagMapper;
import com.idn.backend.repo.TagRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepo tagRepo;
    private final TagMapper tagMapper;

    public List<TagDTO> getAllTags() {
        List<Tag> tagList = tagRepo.findAll();
        return tagMapper.toDTOList(tagList);
    }

    public TagDTO saveTag(TagDTO dto) {
        Tag tag = tagMapper.toEntity(dto);
        Tag savedTag = tagRepo.save(tag);
        return tagMapper.toDTO(savedTag);
    }

    public TagDTO deleteTag(Long id) {
        Tag tag = tagRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with " + id));
        tagRepo.delete(tag);

        return tagMapper.toDTO(tag);
    }

}
