package com.wombatplanning.services;

import com.wombatplanning.models.entities.Client;
import com.wombatplanning.models.entities.User;
import com.wombatplanning.repositories.ClientRepository;
import com.wombatplanning.repositories.UserRepository;
import com.wombatplanning.services.dto.ClientDto;
import com.wombatplanning.services.dto.UserDto;
import com.wombatplanning.services.exceptions.DuplicateClientException;
import com.wombatplanning.services.exceptions.UserIdMisMatchException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@NullMarked
@RequiredArgsConstructor
public class ClientService {

    private final static Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;


    public List<ClientDto> getAllClients(UserDto userDto) {
        final List<Client> allClients = clientRepository.findAllByUserId(userDto.id());
        final List<ClientDto> clientDtoList = transferToDtos(userDto.id(), allClients);
        Collections.sort(clientDtoList);
        return List.copyOf(clientDtoList);
    }

    private List<ClientDto> transferToDtos(Long userId, List<Client> clientList) {
        final List<ClientDto> clientDtoList = new ArrayList<>(clientList.size());
        clientList.forEach(
                client -> clientDtoList.add(new ClientDto(client.getId(), userId, client.getName()))
        );
        return clientDtoList;
    }

    public ClientDto getNewClientDto(UserDto userDto) {
        return new ClientDto(null, userDto.id(), null);

    }

    public void createClient(UserDto userDto, ClientDto clientDto) {
        final Optional<User> optUser = userRepository.findById(userDto.id());
        if (optUser.isEmpty()) {
            log.info("couldn't find user in db");
            throw new UsernameNotFoundException(String.format("No user found with userDto: %s", userDto));
        }
        final User user = optUser.get();
        final Client client = Client.create(user, clientDto.name());
        final Example<Client> example = Example.of(client);
        if (clientRepository.exists(example)) {
            log.info("the Client already exist {}", client);
            throw new DuplicateClientException(String.format("Client name already exists %s", clientDto.name()));
        }
        log.info("saving created client {}", client);
        clientRepository.save(client);
    }

    public ClientDto getClientDtoByClientId(UserDto userDto, Long id) {
        final Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isEmpty()) {
            throw new IllegalArgumentException(String.format("No client with this id %d in DB", id));
        }
        final Client client = clientOptional.get();
        if (!Objects.equals(userDto.id(), client.getUser().getId())) {
            throw new UserIdMisMatchException(
                    String.format("Mismatch: userDto id %d != client.user.id %d",userDto.id(), client.getUser().getId()));
        }
        final ClientDto clientDto = new ClientDto(client.getId(), userDto.id(), client.getName());
        log.info("returning new clientDto {}", clientDto);
        return clientDto;
    }

    public void updateClient(UserDto userDto, ClientDto clientDto) {
        final Optional<User> optUser = userRepository.findById(userDto.id());
        if (optUser.isEmpty()) {
            log.info("couldn't find user in db");
            throw new UsernameNotFoundException(String.format("No user found with userDto: %s", userDto));
        }
        final User user = optUser.get();

        final Optional<Client> optClient = clientRepository.findById(clientDto.id());
        if (optClient.isEmpty()) {
            throw new IllegalArgumentException(String.format("Client not found in DB with id %d", clientDto.id()));
        }
        final Client client = optClient.get();

        if (Objects.equals(client.getName(), clientDto.name())) {
            log.info("name already exist for this user's client's");
            return;
        }

        final Example<Client> exampleIsNameTaken = Example.of(Client.create(user, clientDto.name()));
        if (clientRepository.exists(exampleIsNameTaken)) {
            log.info("name already taken by the user for a client {}", clientDto.name());
            throw new DuplicateClientException(String.format("Client name already exists %s", clientDto.name()));
        }

        client.changeName(clientDto.name());
        log.info("saving updated client {}", client);
        clientRepository.save(client);
    }

    public void deleteClient(UserDto userDto, Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new IllegalArgumentException(String.format("Client not found in DB %d", clientId));
        }
        final Optional<Client> optClient = clientRepository.findById(clientId);
        if (optClient.isEmpty()) {
            throw new IllegalArgumentException(String.format("Client not found in DB with id %d", clientId));
        }
        final Client client = optClient.get();

        if (!Objects.equals(userDto.id(), client.getUser().getId())) {
            throw new UserIdMisMatchException(String.format("Mismatch when deleting client userDto.id %d != client.id %d", userDto.id(), client.getId()));
        }

        clientRepository.delete(client);
    }
}
