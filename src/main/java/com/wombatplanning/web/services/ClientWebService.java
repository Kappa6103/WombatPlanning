package com.wombatplanning.web.services;

import com.wombatplanning.services.dto.ClientDto;
import com.wombatplanning.services.dto.WorksiteDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@NullMarked
@RequiredArgsConstructor
public class ClientWebService {

    public TreeMap<ClientDto, NavigableSet<WorksiteDto>> joinClientsAndWorksites(List<ClientDto> clientList, List<WorksiteDto> worksiteList) {
        TreeMap<ClientDto, NavigableSet<WorksiteDto>> treeMap = new TreeMap<>();

        Map<Long, NavigableSet<WorksiteDto>> map = new HashMap<>();

        for (WorksiteDto w : worksiteList) {
            if (map.containsKey(w.clientId())) {
                if (!map.get(w.clientId()).add(w)) {
                    throw new RuntimeException("One worksite broke uniqueness");
                }
            } else {
                NavigableSet<WorksiteDto> nvSet = new TreeSet<>();
                nvSet.add(w);
                map.put(w.clientId(), nvSet);
            }
        }

        for (ClientDto c : clientList) {
            if (treeMap.containsKey(c)) {
                throw new RuntimeException("One client broke uniqueness");
            } else {
                treeMap.put(c, map.get(c.id()));
            }
        }

        return treeMap;
    }
}
