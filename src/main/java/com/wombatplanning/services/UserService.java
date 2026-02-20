package com.wombatplanning.services;

import com.wombatplanning.models.entities.User;
import com.wombatplanning.repositories.UserRepository;
import com.wombatplanning.services.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@NullMarked
@RequiredArgsConstructor
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repo;
    private final WeekService weekService;
    private final PasswordEncoder passwordEncoder;


    public User createUser(String name, String email, String password) {
        User user = User.create(name, email, password);
        weekService.createWeeksFor(user);
        return user;
    }

    public UserDto getUserDto(UserDetails userDetails) throws UsernameNotFoundException {
        log.info("got a request for {}", userDetails);
        String email = userDetails.getUsername();
        Optional<User> optionalUser = repo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.info("couldn't find user in db");
            throw new UsernameNotFoundException("No user found with username: " + email);
        }
        User user = optionalUser.get();
        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        log.info("returning the newly formed record {}", userDto);
        return userDto;
    }

    public User getUser(UserDto userDto) {
        final Optional<User> optUser = repo.findById(userDto.id());
           if (optUser.isEmpty()) {
            log.info("couldn't find user in db");
            throw new UsernameNotFoundException(String.format("No user found with userDto: %s", userDto));
        }
        return optUser.get();
    }
}


