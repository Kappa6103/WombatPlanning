package com.wombatplanning.web.controllers;

import com.wombatplanning.services.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@NullMarked
@Controller
@RequiredArgsConstructor
public class Home {

    private final static Logger log = LoggerFactory.getLogger(Home.class);
    private final UserService userService;

    @GetMapping("/home")
    public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("userDto", userService.getUserDto(userDetails));
        return "home";
    }

}
