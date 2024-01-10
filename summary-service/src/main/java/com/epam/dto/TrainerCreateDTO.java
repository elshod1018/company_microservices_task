package com.epam.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TrainerCreateDTO(
        @NotNull @Size(min = 3, max = 50, message = "Firstname must be between {min} and {max}") String firstName,
        @NotNull @Size(min = 3, max = 50, message = "Lastname must be between {min} and {max}") String lastName,
        @NotNull Integer specializationId) {
}
