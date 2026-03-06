package com.wombatplanning.services;

import com.wombatplanning.models.entities.Client;
import com.wombatplanning.models.entities.User;
import com.wombatplanning.models.entities.Worksite;
import com.wombatplanning.repositories.WorksiteRepository;
import com.wombatplanning.services.dto.UserDto;
import com.wombatplanning.services.dto.WorksiteDto;
import com.wombatplanning.services.exceptions.DuplicateWorksiteException;
import com.wombatplanning.services.exceptions.UserIdMisMatchException;
import com.wombatplanning.services.exceptions.WorksiteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public WorksiteDto getWorksiteDtoById(UserDto userDto, Long id) {
        final Optional<Worksite> optWorksite = worksiteRepository.findById(id);
        if (optWorksite.isEmpty()) {
            throw new IllegalArgumentException(String.format("No worksite with this id %d in DB", id));
        }
        final Worksite worksite = optWorksite.get();
        if (!Objects.equals(userDto.id(), worksite.getUser().getId())) {
            throw new UserIdMisMatchException(
                    String.format("Mismatch: userDto id %d != worksite.user.id %d", userDto.id(), worksite.getUser().getId()));
        }
        final WorksiteDto worksiteDto = new WorksiteDto(worksite.getId(), worksite.getUser().getId(), worksite.getName(), worksite.getClient().getId());
        log.info("returning new worksiteDto {}", worksiteDto);
        return worksiteDto;
    }

    public void updateWorksite(UserDto userDto, WorksiteDto worksiteDto) {
        final User user = userService.getUser(userDto);
        final Client client = clientService.getClient(worksiteDto.clientId());

        final Optional<Worksite> optWorksite = worksiteRepository.findById(worksiteDto.id());
        if (optWorksite.isEmpty()) {
            throw new IllegalArgumentException(String.format("Worksite not found in DB with id %d", worksiteDto.id()));
        }
        final Worksite worksite = optWorksite.get();

        if (Objects.equals(worksiteDto.name(), worksite.getName())) {
            log.info("name already exist for this user's worksite's");
            return;
        }

        final ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("client");
        final Example<Worksite> example = Example.of(Worksite.create(user, client, worksiteDto.name()), matcher);
        if (worksiteRepository.exists(example)) {
            log.info("name already taken by the user for a worksite {}", worksiteDto.name());
            throw new DuplicateWorksiteException(String.format("Worksite name already exists %s", worksiteDto.name()));
        }

        worksite.changeName(worksiteDto.name());
        log.info("saving updated worksite {}", worksite);
        worksiteRepository.save(worksite);
    }

    public void deleteWorksite(UserDto userDto, Long id) {
        final Worksite worksite = getWorksite(id);
        if (!Objects.equals(userDto.id(), worksite.getUser().getId())) {
            throw new UserIdMisMatchException(String.format("Mismatch when deleting worksite userDto.id %d != worksite.id %d", userDto.id(), worksite.getUser().getId()));
        }
        worksiteRepository.delete(worksite);
    }

    public boolean hasWorksiteScheduledInterventions(Long worksiteId) {
        Worksite worksite = getWorksite(worksiteId);
        return !worksite.getInterventions().isEmpty();
    }

    Worksite getWorksite(Long id) {
        final Optional<Worksite> optWorksite = worksiteRepository.findById(id);
        if (optWorksite.isEmpty()) {
            throw new WorksiteNotFoundException(String.format("No worksite found with this id %d", id));
        }
        return optWorksite.get();
    }
}
