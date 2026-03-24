package com.idn.backend.services.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import com.idn.backend.dto.request.PostRequestDTO;
import com.idn.backend.dto.response.PostResponseDTO;
import com.idn.backend.entity.Media;
import com.idn.backend.entity.Post;
import com.idn.backend.entity.PostTag;
import com.idn.backend.entity.Tag;
import com.idn.backend.exception.PostNotFoundException;
import com.idn.backend.mapper.PostMapper;
import com.idn.backend.repo.PostRepo;
import com.idn.backend.repo.PostTagRepo;
import com.idn.backend.repo.PostViewRepo;
import com.idn.backend.repo.TagRepo;
import com.idn.backend.services.PostService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final GCSServiceImpl gcsService;
    private final PostViewRepo postViewRepo;
    private final TagRepo tagRepo;
    private final PostTagRepo postTagRepo;

    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    @Transactional
    @Override
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

        List<Tag> tags = tagRepo.findAllById(dto.getTagIds());

        if (tags.size() != dto.getTagIds().size()) {
            throw new RuntimeException("Some tags not found");
        }

        List<PostTag> postTags = new ArrayList<>();

        for (Tag tag : tags) {
            PostTag pt = new PostTag();
            pt.setPost(savedPost);
            pt.setTag(tag);
            postTags.add(pt);
        }

        postTagRepo.saveAll(postTags);

        return postMapper.toPostResponseDTO(savedPost);
    }

    @Override
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

    @Override
    public PostResponseDTO getPostById(Long id) {

        Post post = postRepo.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        return postMapper.toPostResponseDTO(post);
    }

    @Override
    public List<PostResponseDTO> searchPosts(String query) {
        List<Post> posts = postRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query);
        return postMapper.toPostResponseDTOList(posts);
    }

    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    @Transactional
    @Override
    public PostResponseDTO updatePost(Long id, PostRequestDTO dto, List<MultipartFile> file) throws IOException {

        Post post = postRepo.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        validatePostAccess(post);

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setStatus(dto.getStatus());

        if (file != null && !file.isEmpty()) {
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

            post.getMediaList().addAll(mediaList);
        }

        postTagRepo.deleteByPost(post);

        List<Tag> tags = tagRepo.findAllById(dto.getTagIds());

        if (tags.size() != dto.getTagIds().size()) {
            throw new RuntimeException("Some tags not found");
        }

        List<PostTag> postTags = new ArrayList<>();

        for (Tag tag : tags) {
            PostTag pt = new PostTag();
            pt.setPost(post);
            pt.setTag(tag);
            postTags.add(pt);
        }

        postTagRepo.saveAll(postTags);

        Post savedPost = postRepo.save(post);

        return postMapper.toPostResponseDTO(savedPost);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
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

    @Override
    public List<PostResponseDTO> getMostViewedPosts(int limit) {

        Pageable pageable = PageRequest.of(0, limit);

        List<Object[]> results = postViewRepo.findMostViewedPosts(pageable);

        List<Long> postIds = results.stream()
                .map(r -> (Long) r[0])
                .toList();

        List<Post> posts = postRepo.findAllById(postIds);

        return posts.stream()
                .map(postMapper::toPostResponseDTO)
                .toList();
    }

    @Override
    public List<PostResponseDTO> getTrendingPosts(int limit) {

        LocalDateTime since = LocalDateTime.now().minusDays(1);
        Pageable pageable = PageRequest.of(0, limit);

        List<Object[]> results = postViewRepo.findTrendingPosts(since, pageable);

        List<Long> postIds = results.stream()
                .map(r -> (Long) r[0])
                .toList();

        List<Post> posts = postRepo.findAllById(postIds);

        return posts.stream()
                .map(postMapper::toPostResponseDTO)
                .toList();
    }

    // Helper Method
    private void validatePostAccess(Post post) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !post.getAuthor().getUserName().equals(username)) {
            throw new RuntimeException("You are not allowed to modify this post");
        }
    }

}
