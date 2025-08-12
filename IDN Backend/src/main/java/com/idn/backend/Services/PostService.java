package com.idn.backend.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idn.backend.Model.Draft;
import com.idn.backend.Model.Post;
import com.idn.backend.Repo.PostRepo;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public List<Post> getPost(int id) {

        throw new UnsupportedOperationException("Unimplemented method 'getPost'");
    }

    public Post savePost(Post post) {
                Draft draft = new Draft();
                draft.setTitle(title);
                draft.setContent(content);
                draft.setImageUrl(imageUrl);
                draft.setCreatedAt(LocalDateTime.now());
                draft.setUpdatedAt(LocalDateTime.now());
    }
}
