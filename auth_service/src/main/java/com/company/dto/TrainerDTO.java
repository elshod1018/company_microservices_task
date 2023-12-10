package com.company.dto;

public record TrainerDTO(
        Integer id,
        String username,
        String password,
        String firstName,
        String lastName,
        Integer specializationId,
        boolean active) {
}
