package com.idn.backend.dto.request;

public record OAuthCompleteRequest(
                String token,
                String username,
                String password) {

}
