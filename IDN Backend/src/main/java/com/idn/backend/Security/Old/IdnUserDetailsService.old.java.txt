package com.idn.backend.Security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.idn.backend.Model.AppUser;
import com.idn.backend.Repo.AppUserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdnUserDetailsService implements UserDetailsService {

    private final AppUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("details not found of the " + username));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(appUser.getRole().name()));

        return new User(
                appUser.getEmail(), // username for auth
                appUser.getPassword(),
                authorities);
    }

}
