package com.wombatplanning.web.controllers;

import com.wombatplanning.services.InterventionScheduleService;
import com.wombatplanning.services.UserService;
import com.wombatplanning.services.dto.InterventionScheduleCreationDto;
import com.wombatplanning.services.dto.UserDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@NullMarked
@Controller
@RequiredArgsConstructor
public class InterventionSchedule {

    private final static Logger log = LoggerFactory.getLogger(InterventionSchedule.class);
    private final InterventionScheduleService interventionScheduleService;
    private final UserService userService;

    @GetMapping("/intervention/create/{worksiteId}")
    public String schedulingForm(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long worksiteId, Model model) {
        final UserDto userDto = userService.getUserDto(userDetails);
        final InterventionScheduleCreationDto scheduleCreationDto = interventionScheduleService.getNewScheduleCreationDto(userDto, worksiteId);
        model.addAttribute("scheduleCreation", scheduleCreationDto);
        return "intervention/create";
    }

    @PostMapping("/intervention/create")
    public String validateSchedulingForm(@AuthenticationPrincipal UserDetails userDetails,
                                         @ModelAttribute(name = "scheduleCreation") @Validated(Create.class) InterventionScheduleCreationDto scdto,
                                         BindingResult result) {
        if (result.hasErrors()) {
            log.info("the schedule creation form has error(s)");
            return "intervention/create";
        }
        final UserDto userDto = userService.getUserDto(userDetails);
        if (scdto.userId() == null || !Objects.equals(scdto.userId(), userDto.id())) {
            log.error("Intervention schedule form corrupted");
            return "redirect:/worksite/list";
        }
        try {
            interventionScheduleService.registerInterventionScheduling(scdto);
            return "redirect:/worksite/list";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
