package com.idn.backend.Services;

import org.springframework.stereotype.Service;

import com.idn.backend.Model.Post;
import com.idn.backend.Repo.PostRepo;

@Service
public class PostService {

    private final PostRepo postRepository;

    public PostService(PostRepo postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }
}
