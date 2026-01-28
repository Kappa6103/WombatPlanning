package com.wombatplanning.web.controllers;

import com.wombatplanning.services.ClientService;
import com.wombatplanning.services.dto.ClientDto;
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


@NullMarked
@Controller
@RequiredArgsConstructor
public class Client {

    private final static Logger log = LoggerFactory.getLogger(Client.class);
    private final ClientService clientService;

    @GetMapping("/client/list")
    public String clientList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<ClientDto> clientList = clientService.getAllClients(userDetails);
        model.addAttribute("clientList", clientList);
        return "client/list";
    }


}
