package com.company.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TraineeCreateDTO(
        String username,
        String password,
        boolean active,
        @NotNull @Size(min = 3, max = 50, message = "First name must be between {min} and {max}") String firstName,
        @NotNull @Size(min = 3, max = 50, message = "Last name must be between {min} and {max}") String lastName,
        @Past(message = "Birth date must be past") LocalDate birthDate,
        String address) {

}
