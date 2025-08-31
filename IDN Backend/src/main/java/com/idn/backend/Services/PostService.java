package com.idn.backend.Services;

<<<<<<< HEAD
import org.springframework.stereotype.Service;

=======
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idn.backend.Model.Draft;
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0
import com.idn.backend.Model.Post;
import com.idn.backend.Repo.PostRepo;

@Service
public class PostService {

<<<<<<< HEAD
    private final PostRepo postRepository;

    public PostService(PostRepo postRepository) {
        this.postRepository = postRepository;
    }
=======
    @Autowired
    private PostRepo postRepository;
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0

    public Post save(Post post) {
        return postRepository.save(post);
    }
<<<<<<< HEAD
=======

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
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0
}
