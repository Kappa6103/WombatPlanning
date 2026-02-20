package com.wombatplanning.services;

import com.wombatplanning.models.entities.Client;
import com.wombatplanning.models.entities.User;
import com.wombatplanning.models.entities.Worksite;
import com.wombatplanning.repositories.WorksiteRepository;
import com.wombatplanning.services.dto.UserDto;
import com.wombatplanning.services.dto.WorksiteDto;
import com.wombatplanning.services.exceptions.DuplicateWorksiteException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
    private final UserService userService;
    private final ClientService clientService;

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

    public WorksiteDto getNewWorksiteDto(UserDto userDto, Long clientId) {
        return new WorksiteDto(null, userDto.id(), null, clientId);
    }

    public void createWorksite(UserDto userDto, WorksiteDto worksiteDto) {
        final User user = userService.getUser(userDto);
        final Client client = clientService.getClient(worksiteDto.clientId());
        final Worksite worksite = Worksite.create(user, client, worksiteDto.name());
        final ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("client");
        final Example<Worksite> example = Example.of(worksite, matcher);
        if (worksiteRepository.exists(example)) {
            log.info("the worksite already exist {}", worksite);
            throw new DuplicateWorksiteException(String.format("Worksite name already exists %s", worksiteDto.name()));
        }
        log.info("saving created worksite {}", worksite);
        worksiteRepository.save(worksite);
    }
}
