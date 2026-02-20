package com.wombatplanning.services.dto;

import com.wombatplanning.models.constraints.ColumnConstraints;
import com.wombatplanning.web.validation.Create;
import com.wombatplanning.web.validation.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WorksiteDto(

        @NotNull(groups = Update.class)
        Long id,

        @NotNull(groups = {Create.class, Update.class})
        Long userId,

        @Size(groups = {Create.class, Update.class}, max = ColumnConstraints.WORKSITE_NAME_FIELD_MAX_LENGTH)
        @NotBlank(groups = {Create.class, Update.class})
        String name,

        @NotNull(groups = {Create.class, Update.class})
        Long clientId

) implements Comparable<WorksiteDto>, Identifiable<Long> {

    @Override
    public int compareTo(WorksiteDto o) {
        return this.name.compareTo(o.name());
    }

}
