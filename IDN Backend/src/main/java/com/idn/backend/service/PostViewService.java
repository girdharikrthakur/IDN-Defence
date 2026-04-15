package com.idn.backend.service;

public interface PostViewService {

    public void registerPostView(Long postId, String username, String ip, String sessionId);

}
