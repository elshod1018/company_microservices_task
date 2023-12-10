package com.epam.dto;

import java.time.LocalDate;

public record TrainerWorkloadDTO(String username, String firstName, String lastName, boolean active,
                                 LocalDate date, Integer duration, String actionType) {
}
