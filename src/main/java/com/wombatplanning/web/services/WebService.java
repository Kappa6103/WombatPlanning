package com.wombatplanning.web.services;

import com.wombatplanning.services.dto.ClientDto;
import com.wombatplanning.services.dto.Identifiable;
import com.wombatplanning.services.dto.InterventionDto;
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
public class WebService {

    private final static Logger log = LoggerFactory.getLogger(WebService.class);

    //TODO  should return an immutable treeMap
    public TreeMap<ClientDto, NavigableSet<WorksiteDto>> joinClientsAndWorksites(
            List<ClientDto> clientList, List<WorksiteDto> worksiteList) {
        TreeMap<ClientDto, NavigableSet<WorksiteDto>> treeMap = new TreeMap<>();

        Map<Long, NavigableSet<WorksiteDto>> map = new HashMap<>();

        for (WorksiteDto w : worksiteList) {
            if (map.containsKey(w.clientId())) {
                if (!map.get(w.clientId()).add(w)) { // wtf is this doing ?
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


    public TreeMap<WorksiteDto, NavigableSet<InterventionDto>> joinWorksitesAndInterventions(
            List<WorksiteDto> worksiteList, List<InterventionDto> interventionList) {
        TreeMap<WorksiteDto, NavigableSet<InterventionDto>> treeMap = new TreeMap<>();

        Map<Long, NavigableSet<InterventionDto>> map = new HashMap<>();

        //can be simplified with map.computeIfAbsent(worksiteId, k -> new TreeSet<>()).add(i);
        for (InterventionDto i : interventionList) {
            Long worksiteId = i.worksiteId();
            if (map.containsKey(worksiteId)) {
                map.get(worksiteId).add(i);
            } else {
                NavigableSet<InterventionDto> nvSet = new TreeSet<>();
                nvSet.add(i);
                map.put(worksiteId,nvSet);
            }
        }

        for (WorksiteDto w : worksiteList) {
            if (treeMap.containsKey(w)) {
                log.error("WORKSITE NOT UNIQUE");
            } else {
                treeMap.put(w, map.getOrDefault(w.id(), Collections.emptyNavigableSet()));
            }
        }

        return treeMap;
    }

    //TreeMap<WorksiteDto, NavigableSet<InterventionDto>> coucou = treeMapMaker(worksiteList, map);
    private <K extends Identifiable<ID> & Comparable<K>, ID, V> TreeMap<K, NavigableSet<V>> treeMapMaker(List<K> keys, Map<ID, NavigableSet<V>> map) {
        TreeMap<K, NavigableSet<V>> treeMap = new TreeMap<>();
        for (K key : keys) {
            if (treeMap.containsKey(key)) {
                log.error("KEY NOT UNIQUE {}", key);
            } else {
                treeMap.put(key, map.getOrDefault(key.id(), Collections.emptyNavigableSet()));
            }
        }
        return treeMap;
    }

}
