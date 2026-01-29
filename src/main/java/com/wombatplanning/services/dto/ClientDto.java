package com.wombatplanning.services.dto;

import com.wombatplanning.models.constraints.ColumnConstraints;
import com.wombatplanning.web.validation.Create;
import com.wombatplanning.web.validation.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClientDto(

        @NotNull(groups = Update.class)
        Long id,

        @NotNull(groups = {Create.class, Update.class})
        Long userId,

        @Size(groups = {Create.class, Update.class}, max = ColumnConstraints.CLIENT_NAME_FIELD_MAX_LENGTH)
        @NotBlank(groups = {Create.class, Update.class})
        String name

) implements Comparable <ClientDto> {

    @Override
    public int compareTo(ClientDto o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.name, o.name());
    }

}
