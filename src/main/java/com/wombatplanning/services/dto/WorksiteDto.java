package com.wombatplanning.services.dto;

public record WorksiteDto(Long id, Long userId, String name, Long clientId) implements Comparable<WorksiteDto {

    @Override
    public int compareTo(WorksiteDto o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.name, o.name());
    }
}
