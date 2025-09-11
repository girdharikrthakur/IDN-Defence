package com.idn.backend.Services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.idn.backend.DTO.PostResponseDTO;
import com.idn.backend.Mapper.PostMapper;
import com.idn.backend.Model.Category;
import com.idn.backend.Model.Post;
import com.idn.backend.Repo.CategoryRepo;
import com.idn.backend.Repo.PostRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final GCSService gcsService;
    private final CategoryRepo categoryRepo;

    public Post save(Post post) {
        return postRepo.save(post);
    }

    public Post savePost(String title, String content, Category category, MultipartFile file) throws IOException {

        String imageUrl = gcsService.uploadFile(file);

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCategory(category);
        post.setImgUrl(imageUrl);
        post.setPublishedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setPublished(true);
        Post savedPost = postRepo.save(post);
        return (savedPost);

    }

    public Post findAllPostsById(Long id) {
        return postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }

    public List<PostResponseDTO> search(String keyword) {

        List<Post> qureyPosts = postRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword,
                keyword);
        return postMapper.toResponseDTOs(qureyPosts);
    }

    public Page<PostResponseDTO> getAllPost(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("publishedAt").descending());

        Page<Post> posts = postRepo.findAll(pageRequest);
        return posts.map(postMapper::toResponseDTO);

    }

    public PostResponseDTO editPost(Long id, String title, String content, Long categoryid, MultipartFile file)
            throws IOException {

        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        if (title != null) {
            post.setTitle(title);
        }
        if (content != null) {
            post.setContent(content);
        }
        if (categoryid != null) {
            Category category = categoryRepo.findById(categoryid)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            post.setCategory(category);
        }
        if (file != null && file.getSize() > 0) {
            String imageUrl = gcsService.uploadFile(file);
            post.setImgUrl(imageUrl);
        }

        post.setUpdatedAt(LocalDateTime.now());
        post.setPublished(true);

        Post savedPost = postRepo.save(post);

        return postMapper.toResponseDTO(savedPost);

    }

}
