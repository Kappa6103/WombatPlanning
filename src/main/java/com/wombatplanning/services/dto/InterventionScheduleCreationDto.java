package com.wombatplanning.services.dto;

import com.wombatplanning.models.constraints.ColumnConstraints;
import com.wombatplanning.web.validation.Create;
import com.wombatplanning.web.validation.Update;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InterventionScheduleCreationDto(

        @NotNull(groups = Update.class)
        Long id,

        @NotNull(groups = {Create.class, Update.class})
        Long userId,

        @NotNull(groups = {Create.class, Update.class})
        Long worksiteId,

        @NotNull(groups = {Create.class, Update.class})
        @Min(groups = {Create.class, Update.class}, value = ColumnConstraints.YEAR_MIN)
        @Max(groups = {Create.class, Update.class}, value = ColumnConstraints.YEAR_MAX)
        Integer year,

        @NotNull(groups = {Create.class, Update.class})
        @Min(groups = {Create.class, Update.class}, value = ColumnConstraints.OCCURRENCE_MIN)
        @Max(groups = {Create.class, Update.class}, value = ColumnConstraints.OCCURRENCE_MAX)
        Integer occurrenceRemaining,

        @NotNull(groups = {Create.class, Update.class})
        @Min(groups = {Create.class, Update.class}, value = ColumnConstraints.WEEK_MIN)
        @Max(groups = {Create.class, Update.class}, value = ColumnConstraints.WEEK_MAX)
        Integer startingWeek,

        @NotNull(groups = {Create.class, Update.class})
        @Min(groups = {Create.class, Update.class}, value = ColumnConstraints.WEEK_MIN)
        @Max(groups = {Create.class, Update.class}, value = ColumnConstraints.WEEK_MAX)
        Integer endingWeek

        ) {

//    @Override
//    public int compareTo(InterventionDto o) {
//        return Integer.compare(this.occurrenceRemaining, o.occurrenceRemaining());
//    }

}
