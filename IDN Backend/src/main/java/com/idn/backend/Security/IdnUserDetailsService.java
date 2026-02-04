package com.idn.backend.Security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.idn.backend.Model.Users;
import com.idn.backend.Repo.UsersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdnUserDetailsService implements UserDetailsService {

    private final UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("details not found of the " + username));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));

        return new User(user.getUserName(), user.getPwd(), authorities);
    }

}
