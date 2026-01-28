package com.wombatplanning.services.dto;


import com.wombatplanning.models.entities.Worksite;

import java.util.List;

public record ClientDto(Long id, Long userId, String name, List<Worksite> worksites) implements Comparable <ClientDto> {

    @Override
    public int compareTo(ClientDto o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.name, o.name());
    }

}
