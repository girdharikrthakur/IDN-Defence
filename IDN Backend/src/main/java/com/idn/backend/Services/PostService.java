package com.idn.backend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import com.idn.backend.dto.request.PostRequestDTO;
import com.idn.backend.dto.response.PostResponseDTO;
import com.idn.backend.entity.Media;
import com.idn.backend.entity.Post;
import com.idn.backend.exception.PostNotFoundException;
import com.idn.backend.mapper.PostMapper;
import com.idn.backend.repo.PostRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final GCSService gcsService;

    @Transactional
    public PostResponseDTO savePost(PostRequestDTO dto, List<MultipartFile> file) throws IOException {

        Post post = postMapper.toPost(dto);

        List<Media> mediaList = new ArrayList<>();
        for (MultipartFile mFile : file) {
            String url = gcsService.uploadFile(mFile);
            Media media = new Media();
            media.setUrl(url);
            media.setFileName(mFile.getOriginalFilename());
            media.setMediaType(mFile.getContentType());
            media.setPost(post);
            mediaList.add(media);

        }
        post.setMediaList(mediaList);

        Post savedPost = postRepo.save(post);

        return postMapper.toPostResponseDTO(savedPost);
    }

    public List<PostResponseDTO> getPosts(Long cursor, int limit) {

        Pageable pageable = PageRequest.of(0, limit);

        List<Post> posts;

        if (cursor == null) {
            posts = postRepo.findByDeletedFalseOrderByIdDesc(pageable);
        } else {
            posts = postRepo.findByDeletedFalseAndIdLessThanOrderByIdDesc(cursor, pageable);
        }

        return postMapper.toPostResponseDTOList(posts);
    }

    public PostResponseDTO getPostById(Long id) {

        Post post = postRepo.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        return postMapper.toPostResponseDTO(post);
    }

    public List<PostResponseDTO> searchPosts(String query) {
        List<Post> posts = postRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query);
        return postMapper.toPostResponseDTOList(posts);
    }

    @Transactional
    public PostResponseDTO updatePost(Long id, PostRequestDTO dto, List<MultipartFile> file) throws IOException {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        List<Media> mediaList = new ArrayList<>();

        if (file != null && !file.isEmpty()) {
            for (MultipartFile mFile : file) {
                String url = gcsService.uploadFile(mFile);
                Media media = new Media();
                media.setUrl(url);
                media.setFileName(mFile.getOriginalFilename());
                media.setMediaType(mFile.getContentType());
                media.setPost(post);
                mediaList.add(media);
            }
        }
        post.getMediaList().addAll(mediaList);

        Post savedPost = postRepo.save(post);

        return postMapper.toPostResponseDTO(savedPost);
    }

    @Transactional
    public void deletePost(Long id) {

        Post post = postRepo.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin || post.getAuthor().getUserName().equals(username)) {

            post.setDeleted(true);
            postRepo.save(post);
            return;
        }

        throw new AccessDeniedException("You are not allowed to delete this post");
    }

}
