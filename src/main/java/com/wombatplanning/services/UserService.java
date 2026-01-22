package com.wombatplanning.services;

import com.wombatplanning.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService {

    private final UserRepository userRepository;

}
