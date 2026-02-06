package com.wombatplanning.services.dto;

public record InterventionDto(
        Long id,
        Long userId,
        Long worksiteId,
        Integer year) {
}
