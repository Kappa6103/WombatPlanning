package com.wombatplanning.services;

import com.wombatplanning.models.entities.Client;
import com.wombatplanning.models.entities.User;
import com.wombatplanning.models.entities.Worksite;
import com.wombatplanning.repositories.ClientRepository;
import com.wombatplanning.repositories.UserRepository;
import com.wombatplanning.services.dto.ClientDto;
import com.wombatplanning.services.dto.UserDto;
import com.wombatplanning.services.exceptions.DuplicateClientException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
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
            throw new UsernameNotFoundException(String.format("No user found with userDto: %s", userDto));
        }
        User user = optUser.get();
        final Client client = Client.create(user, clientDto.name());
        Example<Client> example = Example.of(client);
        if (clientRepository.exists(example)) {
            log.info("the Client already exist {}", client);
            throw new DuplicateClientException(String.format("Client name already exists %s", userDto.name()));
        }
        log.info("saving client{}", client);
        clientRepository.save(client);
    }
}
