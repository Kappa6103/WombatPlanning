package com.wombatplanning.services;

import com.wombatplanning.models.entities.User;
import com.wombatplanning.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService {

    private final UserRepository userRepository;
    private final WeekService weekService;


    public User createUser(String name, String email, String password) {
        User user = User.create(name, email, password);
        weekService.createWeeksFor(user);
        return user;

    }

}
