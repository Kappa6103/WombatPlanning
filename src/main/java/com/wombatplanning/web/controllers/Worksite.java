package com.wombatplanning.web.controllers;

import com.wombatplanning.models.entities.Intervention;
import com.wombatplanning.services.InterventionService;
import com.wombatplanning.services.UserService;
import com.wombatplanning.services.WorksiteService;
import com.wombatplanning.services.dto.InterventionDto;
import com.wombatplanning.services.dto.UserDto;
import com.wombatplanning.services.dto.WorksiteDto;
import com.wombatplanning.web.services.WebService;
import com.wombatplanning.web.validation.Create;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.NavigableSet;
import java.util.Objects;
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
        model.addAttribute("worksitesAndInterventions", worksiteList);
        return "worksite/list";
    }

    @GetMapping("/worksite/create/{clientId}")
    public String worksiteCreationForm(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long clientId, Model model) {
        final UserDto userDto = userService.getUserDto(userDetails);
        final WorksiteDto newWorksiteDto = worksiteService.getNewWorksiteDto(userDto, clientId);
        log.info("sending a new worksite dto to the form {}", newWorksiteDto);
        model.addAttribute("worksite", newWorksiteDto);
        return "worksite/create";
    }

    @PostMapping("/worksite/create")
    public String worksiteCreationValidate(@AuthenticationPrincipal UserDetails userDetails,
                                           @ModelAttribute("worksite") @Validated(Create.class) WorksiteDto worksiteDto,
                                           BindingResult result) {
        final UserDto userDto = userService.getUserDto(userDetails);
        log.info("received a new worksite form {}", worksiteDto);
        if (worksiteDto.userId() == null || !Objects.equals(worksiteDto.userId(), userDto.id())) {
            log.error("form submission corrupted worksite.userId {} and userdto.id {} not equal", worksiteDto.userId(), userDto.id());
            return "redirect:/worksite/create";
        }
        if (result.hasErrors()) {
            log.info("the worksite creation form has error(s)");
            return "worksite/create";
        }
        worksiteService.createWorksite(userDto, worksiteDto);

    }

































}
