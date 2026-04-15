package com.idn.backend.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.idn.backend.entity.Post;
import com.idn.backend.entity.PostView;
import com.idn.backend.repo.PostRepo;
import com.idn.backend.repo.PostViewRepo;
import com.idn.backend.service.PostViewService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostViewServiceImpl implements PostViewService {

    private final PostViewRepo postViewRepo;
    private final PostRepo postRepo;

    @Transactional
    @Override
    public void registerPostView(Long postId, String username, String ip, String sessionId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        PostView view = new PostView();
        view.setPost(post);
        view.setUsername(username); // null if anonymous
        view.setIpAddress(ip);
        view.setSessionId(sessionId);
        view.setViewDate(LocalDate.now());

        postViewRepo.save(view);
    }

}
