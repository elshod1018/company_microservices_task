package com.epam.repository;

import com.epam.entity.TrainerWorkload;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainerWorkloadRepository extends MongoRepository<TrainerWorkload, String> {
    @Modifying
    @Transactional
    @Query("{ 'username' : ?0, 'date' : ?1 }")
    void deleteByUsernameAndDate(String username, LocalDate date);

    @Query("{ 'username' : ?0 }")
    List<TrainerWorkload> findAllByUsername(String username);

    @Aggregation(pipeline = {"{ '$match' : { 'username' : ?0, 'active' : true } }", "{ '$limit' : ?1 }"})
    TrainerWorkload findFirstByUsername(String username, int limit);

}