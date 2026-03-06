package com.wombatplanning.services.dto;

public record InterventionScheduleInfoDto(
        Long userId,
        Long worksiteId,
        Integer year,
        Integer occurrenceDone,
        Integer occurrenceRemaining,
        Integer occurrenceSkipped
) {
}
