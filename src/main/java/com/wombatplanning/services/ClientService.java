package com.wombatplanning.services;

import com.wombatplanning.models.entities.Client;
import com.wombatplanning.models.entities.Worksite;
import com.wombatplanning.repositories.ClientRepository;
import com.wombatplanning.services.dto.ClientDto;
import com.wombatplanning.services.dto.UserDto;
import com.wombatplanning.services.dto.WorksiteDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@NullMarked
@RequiredArgsConstructor
public class ClientService {

    private final static Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;


    public List<ClientDto> getAllClients(UserDto userDto) {
        List<Client> allClients = clientRepository.findAllByUserId(userDto.id());
        List<ClientDto> clientDtoList = transferToDtos(userDto.id(), allClients);
        Collections.sort(clientDtoList);
        return List.copyOf(clientDtoList);
    }

    private List<ClientDto> transferToDtos(Long userId, List<Client> clientList) {
        List<ClientDto> clientDtoList = new ArrayList<>(clientList.size());
        clientList.forEach(
                client -> clientDtoList.add(new ClientDto(client.getId(), userId, client.getName()))
        );
        return clientDtoList;
    }
}
