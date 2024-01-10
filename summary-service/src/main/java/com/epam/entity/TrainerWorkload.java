package com.epam.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "trainer_workload")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerWorkload {
    @Id
    private String id;
    @Size(min = 2, max = 50, message = "Username must be between 2 and 30 characters")
    private String username;

    @Field(name = "first_name")
    @Indexed
    @NotNull(message = "First name cannot be null")
    private String firstName;

    @Field(name = "last_name")
    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @Field(name = "is_active")
    private boolean active;

    private LocalDate date;

    private Integer duration;

}
