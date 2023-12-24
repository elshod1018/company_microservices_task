package com.epam.repository;

import com.epam.entity.TrainerWorkload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface TrainerWorkloadRepository extends JpaRepository<TrainerWorkload, Integer> {
    @Modifying
    @Transactional
    @Query("delete from TrainerWorkload t where t.username = ?1 and t.date = ?2")
    void deleteByUsernameAndDate(String username, LocalDate date);
}