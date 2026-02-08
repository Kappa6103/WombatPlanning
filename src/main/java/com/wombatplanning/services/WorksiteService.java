package com.wombatplanning.services;

import com.wombatplanning.models.entities.Worksite;
import com.wombatplanning.repositories.WorksiteRepository;
import com.wombatplanning.services.dto.UserDto;
import com.wombatplanning.services.dto.WorksiteDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@NullMarked
@RequiredArgsConstructor
public class WorksiteService {

    private final static Logger log = LoggerFactory.getLogger(WorksiteService.class);
    private final WorksiteRepository worksiteRepository;

    public List<WorksiteDto> getAllWorksites(UserDto userDto) {
        List<Worksite> allWorksites = worksiteRepository.findAllByUserId(userDto.id());
        List<WorksiteDto> worksiteDtos = transfertToDtos(allWorksites);
        Collections.sort(worksiteDtos);
        quickIntegrityCheck(userDto, worksiteDtos);
        return List.copyOf(worksiteDtos);
    }

    private List<WorksiteDto> transfertToDtos(List<Worksite> worksiteList) {
        List<WorksiteDto> worksiteDtos = new ArrayList<>(worksiteList.size());
        worksiteList.forEach(
                w -> worksiteDtos.add(new WorksiteDto(w.getId(), w.getUser().getId(), w.getName(), w.getClient().getId())))
        ;
        return worksiteDtos;
    }

    private void quickIntegrityCheck(UserDto user, List<WorksiteDto> worksiteDtos) {
        for (WorksiteDto w : worksiteDtos) {
            if (!Objects.equals(w.userId(), user.id())) {
                throw new RuntimeException("ERROR SANITY CHECK FAILED");
            }
        }
    }
}
