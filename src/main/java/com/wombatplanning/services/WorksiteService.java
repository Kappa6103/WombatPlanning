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
import java.util.List;

@Service
@NullMarked
@RequiredArgsConstructor
public class WorksiteService {

    private final static Logger log = LoggerFactory.getLogger(WorksiteService.class);
    private final WorksiteRepository worksiteRepository;

    public List<Worksite> getAllWorksites(UserDto userDto) {
        List<Worksite> allWorksites = worksiteRepository.findAllByUserId(userDto.id());
        List<WorksiteDto> worksiteDtos = transfertToDtos(userDto.id(), allWorksites);

    }

    private List<WorksiteDto> transfertToDtos(Long userId, List<Worksite> worksiteList) {
        List<WorksiteDto> worksiteDtos = new ArrayList<>(worksiteList.size());
        worksiteList.forEach(
                w -> worksiteDtos.add(new WorksiteDto(w.getId(), userId, w.getName(), w.getClientId())))
        ;
    }
}
