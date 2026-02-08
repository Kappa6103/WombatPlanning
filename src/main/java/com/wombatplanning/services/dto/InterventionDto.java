package com.wombatplanning.services.dto;

public record InterventionDto(
        Long id,
        Long userId,
        Long worksiteId,
        Integer year,
        Integer occurrence
        ) implements Comparable<InterventionDto> {

    @Override
    public int compareTo(InterventionDto o) {
        return Integer.compare(this.occurrence, o.occurrence());
    }

}
