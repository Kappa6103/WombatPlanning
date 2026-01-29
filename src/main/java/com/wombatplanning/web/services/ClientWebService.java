package com.wombatplanning.web.services;

import com.wombatplanning.services.dto.ClientDto;
import com.wombatplanning.services.dto.WorksiteDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@NullMarked
@RequiredArgsConstructor
public class ClientWebService {

    private final static Logger log = LoggerFactory.getLogger(ClientWebService.class);

    public TreeMap<ClientDto, NavigableSet<WorksiteDto>> joinClientsAndWorksites(List<ClientDto> clientList, List<WorksiteDto> worksiteList) {
        TreeMap<ClientDto, NavigableSet<WorksiteDto>> treeMap = new TreeMap<>();

        Map<Long, NavigableSet<WorksiteDto>> map = new HashMap<>();

        for (WorksiteDto w : worksiteList) {
            if (map.containsKey(w.clientId())) {
                if (!map.get(w.clientId()).add(w)) {
                    log.error("WORKSITE NOT UNIQUE {}", w);
                }
            } else {
                NavigableSet<WorksiteDto> nvSet = new TreeSet<>();
                nvSet.add(w);
                map.put(w.clientId(), nvSet);
            }
        }

        for (ClientDto c : clientList) {
            log.info("clientdto c is {}", c);
            if (treeMap.containsKey(c)) {
                log.error("CLIENT NOT UNIQUE {}", c);
            } else {
                log.info("clientDto is put in map {}", c);
                treeMap.put(c, map.getOrDefault(c.id(), Collections.emptyNavigableSet()));
            }
        }
//        return Collections.unmodifiableNavigableMap(treeMap);
        return treeMap;
    }
}
