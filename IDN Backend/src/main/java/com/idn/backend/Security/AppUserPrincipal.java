package com.idn.backend.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.idn.backend.entity.AppUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AppUserPrincipal implements UserDetails {

    private final AppUser appUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(appUser.getRole().name()));
    }

    public String getUserName() {
        return appUser.getUserName();
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();

    }

    @Override
    public String getUsername() {
        return appUser.getEmail();

    }

    @Override
    public boolean isAccountNonLocked() {
        return !appUser.isLocked();
    }

    @Override
    public boolean isEnabled() {
        return appUser.isEnabled();
    }

    public Long getUserId() {
        return appUser.getId();
    }

    public String getProvider() {
        return appUser.getProvider();
    }

}
