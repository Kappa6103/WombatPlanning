package com.wombatplanning.web.controllers;

import com.wombatplanning.models.entities.Intervention;
import com.wombatplanning.services.InterventionService;
import com.wombatplanning.services.UserService;
import com.wombatplanning.services.WorksiteService;
import com.wombatplanning.services.dto.InterventionDto;
import com.wombatplanning.services.dto.UserDto;
import com.wombatplanning.services.dto.WorksiteDto;
import com.wombatplanning.web.services.WebService;
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
public class Worksite {

    private final static Logger log = LoggerFactory.getLogger(Worksite.class);
    private final UserService userService;
    private final WorksiteService worksiteService;
    private final InterventionService interventionService;
    private final WebService webService;

    @GetMapping("/worksite/list")
    public String worksiteList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        final UserDto userDto = userService.getUserDto(userDetails);
        final List<WorksiteDto> worksiteList = worksiteService.getAllWorksites(userDto);
        final List<InterventionDto> interventionList = interventionService.getAllIntervention(userDto);
        final TreeMap<WorksiteDto, NavigableSet<InterventionDto>> orderedWorksitesAndInterventions = webService
                .joinWorksitesAndInterventions(worksiteList, interventionList);
        model.addAttribute("worksiteList", worksiteList);
        return "worksite/list";
    }
}
