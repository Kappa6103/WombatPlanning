package com.wombatplanning.web.controllers;

import com.wombatplanning.services.ClientService;
import com.wombatplanning.services.UserService;
import com.wombatplanning.services.WorksiteService;
import com.wombatplanning.services.dto.ClientDto;
import com.wombatplanning.services.dto.UserDto;
import com.wombatplanning.services.dto.WorksiteDto;
import com.wombatplanning.services.exceptions.DuplicateClientException;
import com.wombatplanning.web.services.ClientWebService;
import com.wombatplanning.web.validation.Create;
import com.wombatplanning.web.validation.Update;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeMap;


@NullMarked
@Controller
@RequiredArgsConstructor
public class Client {

    private final static Logger log = LoggerFactory.getLogger(Client.class);
    private final UserService userService;
    private final ClientService clientService;
    private final WorksiteService worksiteService;
    private final ClientWebService clientWebService;

    @GetMapping("/client/list")
    public String clientList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        final UserDto userDto = userService.getUserDto(userDetails);
        final List<ClientDto> clientList = clientService.getAllClients(userDto);
        final List<WorksiteDto> worksiteList = worksiteService.getAllWorksites(userDto);
        TreeMap<ClientDto, NavigableSet<WorksiteDto>> orderedClientsAndWorksites = clientWebService
                .joinClientsAndWorksites(clientList, worksiteList);
        model.addAttribute("clientsAndWorksites", orderedClientsAndWorksites);
        return "client/list";
    }

    @GetMapping("/client/create")
    public String clientCreationForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        final UserDto userDto = userService.getUserDto(userDetails);
        final ClientDto newClientDto = clientService.getNewClientDto(userDto);
        log.info("sending a new client dto to the from {}", newClientDto);
        model.addAttribute("client", newClientDto);
        return "client/create";
    }

    @PostMapping("/client/create")
    public String clientCreationValidate(@AuthenticationPrincipal UserDetails userDetails,
                                 @ModelAttribute("client") @Validated(Create.class) ClientDto clientDto,
                                 BindingResult result) {
        final UserDto userDto = userService.getUserDto(userDetails);
        log.info("received a new client form {}", clientDto);
        if(clientDto.userId() == null || !Objects.equals(clientDto.userId(), userDto.id())) {
            log.error("form submission corrupted clientdto.userId {} and userdto.id {} not equal", clientDto.userId(), userDto.id());
            return "redirect:/client/create";
        }
        if(result.hasErrors()) {
            log.info("the client creation form has errors");
            return "client/create";
        }

        try {
            clientService.createClient(userDto, clientDto);
            return "redirect:/client/list";
        } catch (DuplicateClientException dce) {
            result.rejectValue("name", "client.name.duplicate", "Client name already exists");
            return "client/create";
        }
    }

    @GetMapping("/client/update/{id}")
    public String clientUpdateForm(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, Model model) {
        final UserDto userDto = userService.getUserDto(userDetails);
        final ClientDto clientDto = clientService.getClientDtoByClientId(userDto, id);
        model.addAttribute("client", clientDto);
        return "client/update";
    }

    @PostMapping("/client/update")
    public String clientUpdateValidate(@AuthenticationPrincipal UserDetails userDetails,
                                       @ModelAttribute("client") @Validated(Update.class) ClientDto clientDto,
                                       BindingResult result) {
        log.info("receiving clientDto {}", clientDto);
        final UserDto userDto = userService.getUserDto(userDetails);
        if (clientDto.userId() == null || !Objects.equals(clientDto.userId(), userDto.id())) {
            log.error("form submission corrupted clientdto.userId {} and userdto.id {} not equal", clientDto.userId(), userDto.id());
            return "redirect:/client/list";
        }
        if (result.hasErrors()) {
            log.info("the client update form has errors");
            return "client/update";
        }

        try {
            clientService.updateClient(userDto, clientDto);
            return "redirect:/client/list";
        } catch (DuplicateClientException dce) {
            result.rejectValue("name", "client.name.duplicate", "Client name already exists");
            return "client/update";
        }
    }

    @PostMapping("/client/delete/{id}")
    public String clientDelete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        final UserDto userDto = userService.getUserDto(userDetails);
        clientService.deleteClient(userDto, id);
        return "redirect:/client/list";
    }

}
