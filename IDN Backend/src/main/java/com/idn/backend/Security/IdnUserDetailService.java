package com.idn.backend.security;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.idn.backend.entity.AppUser;
import com.idn.backend.repo.AppUserRepo;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class IdnUserDetailService implements UserDetailsService {

    private final AppUserRepo appUserRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(appUser.getRole().name()));
        return new User(
                appUser.getEmail(),
                appUser.getPassword(),
                authorities);
    }

}
