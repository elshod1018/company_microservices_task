package com.company.dto;

import java.time.LocalDate;

public record TraineeDTO(Integer id, String username, String password, String firstName, String lastName,
                         boolean active, LocalDate birthDate, String address) {
}
