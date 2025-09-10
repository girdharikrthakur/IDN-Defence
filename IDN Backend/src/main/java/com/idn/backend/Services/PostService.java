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
import com.idn.backend.Repo.PostRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepo postRepository;
    private final PostMapper postMapper;
    private final GCSService gcsService;

    public Post save(Post post) {
        return postRepository.save(post);
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
        Post savedPost = postRepository.save(post);
        return (savedPost);

    }

    public Post findAllPostsById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }

    public List<PostResponseDTO> search(String keyword) {

        List<Post> qureyPosts = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword,
                keyword);
        return postMapper.toResponseDTOs(qureyPosts);
    }

    public Page<PostResponseDTO> getAllPost(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("publishedAt").descending());

        Page<Post> posts = postRepository.findAll(pageRequest);
        return posts.map(postMapper::toResponseDTO);

    }

}
