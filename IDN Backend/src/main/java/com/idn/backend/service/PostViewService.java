package com.idn.backend.services;

public interface PostViewService {

    public void registerPostView(Long postId, String username, String ip, String sessionId);

}
