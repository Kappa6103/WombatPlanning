package com.wombatplanning;

import com.wombatplanning.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class controllers {

    private final UserRepository userRepository;

    @GetMapping("/index")
    public String index(Model model) {

        return "index";
    };

}
