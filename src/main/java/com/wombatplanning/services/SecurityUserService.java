package com.wombatplanning.services;

import com.wombatplanning.models.entities.User;
import com.wombatplanning.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@NullMarked
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

    private final static Logger log = LoggerFactory.getLogger(SecurityUserService.class);
    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("got a request for {}", username);
        Optional<User> optionalUser = repo.findByEmail(username);

        if (optionalUser.isEmpty()) {
            log.warn("couldn't find user in db");
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities()
        );
    }

    private static Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

}


