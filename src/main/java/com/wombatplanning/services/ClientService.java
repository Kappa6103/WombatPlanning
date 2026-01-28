package com.wombatplanning.services;

import com.wombatplanning.models.entities.Client;
import com.wombatplanning.repositories.ClientRepository;
import com.wombatplanning.services.dto.ClientDto;
import com.wombatplanning.services.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@NullMarked
@RequiredArgsConstructor
public class ClientService {

    private final static Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;
    private final UserService userService;


    public Set<ClientDto> getClientSet(UserDetails userDetails) {
        UserDto userDto = userService.getUserDto(userDetails);
        List<Client> allByUserId = clientRepository.findAllByUserId(userDto.id());
        //TODO MAP TO CLIENT DTO

    }

    private Set<ClientDto>
}
