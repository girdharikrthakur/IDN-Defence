package com.idn.backend.service.impl;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import com.idn.backend.dto.request.PostRequestDTO;
import com.idn.backend.dto.response.CursorPageResponse;
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
import com.idn.backend.service.PostService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
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

    // Get All the Posts with Pagination
    @Override
    public CursorPageResponse<PostResponseDTO> getPosts(Long cursor, int limit) {

        Pageable pageable = PageRequest.of(0, limit + 1);

        List<Post> posts;

        if (cursor == null) {
            posts = postRepo.findByDeletedFalseOrderByIdDesc(pageable);
        } else {
            posts = postRepo.findByDeletedFalseAndIdLessThanOrderByIdDesc(cursor, pageable);
        }

        boolean hasMore = posts.size() > limit;

        if (hasMore) {
            posts = posts.subList(0, limit);
        }

        List<PostResponseDTO> postDTOs = postMapper.toPostResponseDTOList(posts);

        Long nextCursor = posts.isEmpty()
                ? null
                : posts.get(posts.size() - 1).getId();

        return new CursorPageResponse<PostResponseDTO>(postDTOs, nextCursor, hasMore);
    }

    // Get post by id for public view (only non-deleted posts)
    @Override
    public PostResponseDTO getPostById(Long id) {

        Post post = postRepo.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        return postMapper.toPostResponseDTO(post);
    }

    // Search posts by title or content
    @Override
    public List<PostResponseDTO> searchPosts(String query) {
        List<Post> posts = postRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query);
        return postMapper.toPostResponseDTOList(posts);
    }

    // Update post (only author or admin can update)
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

    // Delete post (only author or admin can delete) - Soft Delete
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public void deletePost(Long id) {

        Post post = postRepo.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin || post.getUser().getUserName().equals(username)) {

            post.setDeleted(true);
            postRepo.save(post);
            return;
        }

        throw new AccessDeniedException("You are not allowed to delete this post");
    }

    // Get most viewed posts in last 24 hours
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

    // Get trending posts (most views + recent) in last 24 hours
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

    // Helper Method for validating if the current user is the author of the post or
    // an admin
    private void validatePostAccess(Post post) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !post.getUser().getUserName().equals(username)) {
            throw new RuntimeException("You are not allowed to modify this post");
        }
    }

}
