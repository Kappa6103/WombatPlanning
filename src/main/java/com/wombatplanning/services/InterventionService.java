package com.wombatplanning.services;

import com.wombatplanning.models.entities.Intervention;
import com.wombatplanning.repositories.InterventionRepository;
import com.wombatplanning.services.dto.InterventionDto;
import com.wombatplanning.services.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@NullMarked
@RequiredArgsConstructor
public class InterventionService {

    private final static Logger log = LoggerFactory.getLogger(InterventionService.class);
    private final InterventionRepository interventionRepository;

    public List<InterventionDto> getAllIntervention(UserDto userDto) {
        final List<Intervention> interventionList = interventionRepository.findAllByUserId(userDto.id());
        final List<InterventionDto> interventionDtoList = transferToDtos(interventionList);
        return List.copyOf(interventionDtoList);
    }

    private List<InterventionDto> transferToDtos(List<Intervention> interventionList) {
        final List<InterventionDto> interventionDtos = new ArrayList<>(interventionList.size());
        interventionList.forEach(
                i -> interventionDtos.add(new InterventionDto(i.getId(), i.getUserId(), i.getWorksiteId(), i.getYear()))
        );
        return interventionDtos;
    }

}
