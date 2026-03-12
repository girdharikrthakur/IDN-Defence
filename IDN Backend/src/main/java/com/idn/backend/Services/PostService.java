package com.idn.backend.Services;

import org.springframework.stereotype.Service;

import com.idn.backend.DTO.RequestDTO.PostRequestDTO;
import com.idn.backend.DTO.ResponseDTO.PostResponseDTO;
import com.idn.backend.Mapper.PostMapper;
import com.idn.backend.Model.Post;
import com.idn.backend.Repo.PostRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final PostMapper postMapper;

    public PostResponseDTO savePost(PostRequestDTO dto) {

        Post post = postMapper.toPost(dto);

        Post savedPost = postRepo.save(post);

        return postMapper.toPostResponseDTO(savedPost);
    }

}
