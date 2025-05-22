package com.project.flightmanagementsystem.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
    @NotEmpty(message = "{validation.user.name.required}")
    String username,

    @NotEmpty(message = "{validation.user.password.required}")
    String password
) {}
