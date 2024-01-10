package com.epam.dto;

import java.time.LocalDate;

public record TrainerWorkloadUpdateDTO(String firstName, String lastName, boolean active,
                                       LocalDate date, Integer duration) {
}
