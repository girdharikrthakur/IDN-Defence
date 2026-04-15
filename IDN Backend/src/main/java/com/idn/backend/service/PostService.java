package com.idn.backend.service;


import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.idn.backend.dto.request.PostRequestDTO;
import com.idn.backend.dto.response.PostResponseDTO;

public interface PostService {

    public PostResponseDTO savePost(PostRequestDTO dto, List<MultipartFile> file) throws IOException;

    public List<PostResponseDTO> getPosts(Long cursor, int limit);

    public PostResponseDTO getPostById(Long id);

    public List<PostResponseDTO> searchPosts(String query);

    public PostResponseDTO updatePost(Long id, PostRequestDTO dto, List<MultipartFile> file) throws IOException;

    public void deletePost(Long id);

    public List<PostResponseDTO> getMostViewedPosts(int limit);

    public List<PostResponseDTO> getTrendingPosts(int limit);
}
