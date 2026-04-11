package com.idn.backend.security;

import org.springframework.context.annotation.Configuration;

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
        public UserDetails loadUserByUsername(String username) {
                AppUser user = appUserRepo.findByEmail(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                return new AppUserPrincipal(user);
        }

}
