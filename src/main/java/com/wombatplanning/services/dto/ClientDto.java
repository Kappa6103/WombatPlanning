package com.wombatplanning.services.dto;

public record ClientDto(Long id, Long userId, String name) implements Comparable <ClientDto> {

    @Override
    public int compareTo(ClientDto o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.name, o.name());
    }

}
