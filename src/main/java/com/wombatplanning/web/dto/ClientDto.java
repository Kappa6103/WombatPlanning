package com.wombatplanning.web.dto;

import com.wombatplanning.web.validation.Create;
import com.wombatplanning.web.validation.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientDto(

        @NotNull(groups = Update.class)
        Long id,

        @NotNull
        Long userId,

        @NotBlank(groups = Create.class)
        String name
) {
}
