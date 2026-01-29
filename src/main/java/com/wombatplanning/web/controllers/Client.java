package com.wombatplanning.web.controllers;

import com.wombatplanning.services.ClientService;
import com.wombatplanning.services.UserService;
import com.wombatplanning.services.WorksiteService;
import com.wombatplanning.services.dto.ClientDto;
import com.wombatplanning.services.dto.UserDto;
import com.wombatplanning.services.dto.WorksiteDto;
import com.wombatplanning.web.services.ClientWebService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.NavigableSet;
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
        UserDto userDto = userService.getUserDto(userDetails);
        List<ClientDto> clientList = clientService.getAllClients(userDto);
        List<WorksiteDto> worksiteList = worksiteService.getAllWorksites(userDto);
        TreeMap<ClientDto, NavigableSet<WorksiteDto>> orderedClientsandWorksites = clientWebService
                .joinClientsAndWorksites(clientList, worksiteList);
        model.addAttribute("clientList", orderedClientsandWorksites);
        return "client/list";
    }


}
