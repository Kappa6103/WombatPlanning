package com.wombatplanning.web.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record exemple (

    @NotNull(groups = Update.class)
    Long id,

    @NotNull
    Long userId,

    @NotBlank(groups = Create.class)
    String name
            )
{

}
