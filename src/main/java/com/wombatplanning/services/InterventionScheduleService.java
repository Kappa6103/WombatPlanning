package com.wombatplanning.services;

import com.wombatplanning.models.entities.InterventionSchedule;
import com.wombatplanning.repositories.InterventionScheduleRepository;
import com.wombatplanning.services.dto.InterventionScheduleCreationDto;
import com.wombatplanning.services.dto.InterventionScheduleInfoDto;
import com.wombatplanning.services.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@NullMarked
@RequiredArgsConstructor
public class InterventionScheduleService {

    private final static Logger log = LoggerFactory.getLogger(InterventionScheduleService.class);
    private final InterventionScheduleRepository interventionScheduleRepository;
    private final WorksiteService worksiteService;
    private final UserService userService;

    public List<InterventionScheduleInfoDto> getAllScheduleInfo(UserDto userDto, int year) {
        final List<InterventionSchedule> interventionScheduleList = interventionScheduleRepository.findAllByUserIdAndYear(userDto.id(), year);
        final List<InterventionScheduleInfoDto> interventionScheduleCreationDtoList = extractInfo(interventionScheduleList);
        return List.copyOf(interventionScheduleCreationDtoList);
    }

    private List<InterventionScheduleInfoDto> extractInfo(List<InterventionSchedule> interventionScheduleList) {
        final List<InterventionScheduleInfoDto> interventionScheduleInfoDtos = new ArrayList<>(interventionScheduleList.size());
        interventionScheduleList.forEach(
                i -> interventionScheduleInfoDtos.add(
                        new InterventionScheduleInfoDto(i.getUser().getId(), i.getWorksite().getId(),
                        i.getYear(), i.getOccurrenceDone(), i.getOccurrenceRemaining(), i.getOccurrenceSkipped()))
        );
        return interventionScheduleInfoDtos;
    }

    public InterventionScheduleCreationDto getNewScheduleCreationDto(UserDto userDto, Long worksiteId) {
        if (worksiteService.hasWorksiteScheduledInterventions(worksiteId)) {
            throw new IllegalArgumentException("Worksite already has Scheduled interventions");
        }
        return new InterventionScheduleCreationDto(
                null,
                userDto.id(),
                worksiteId,
                LocalDate.now().getYear(),
                null,
                null,
                null
        );
    }

    public void registerInterventionScheduling(InterventionScheduleCreationDto scdto) {
        final InterventionSchedule interventionSchedule = InterventionSchedule.create(
                userService.getUser(scdto.userId()),
                worksiteService.getWorksite(scdto.worksiteId()),
                scdto.year(),
                scdto.occurrenceRemaining(),
                scdto.startingWeek(),
                scdto.endingWeek()
        );
        interventionScheduleRepository.save(interventionSchedule);
    }
}
