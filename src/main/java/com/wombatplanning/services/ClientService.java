package com.wombatplanning.services;

import com.wombatplanning.models.entities.Client;
import com.wombatplanning.models.entities.User;
import com.wombatplanning.models.entities.Worksite;
import com.wombatplanning.repositories.ClientRepository;
import com.wombatplanning.repositories.UserRepository;
import com.wombatplanning.services.dto.ClientDto;
import com.wombatplanning.services.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@NullMarked
@RequiredArgsConstructor
public class ClientService {

    private final static Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;


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

    public ClientDto getNewClientDto(UserDto userDto) {
        return new ClientDto(null, userDto.id(), null);

    }

    public void createClient(UserDto userDto, ClientDto clientDto) {
        Optional<User> optUser = userRepository.findById(userDto.id());
        if (optUser.isEmpty()) {
            log.info("couldn't find user in db");
            throw new UsernameNotFoundException("No user found with userDto: {}" + userDto);
        }
        User user = optUser.get();
        Client client = Client.create(user, clientDto.name());
        clientRepository.save(client);
    }
}
