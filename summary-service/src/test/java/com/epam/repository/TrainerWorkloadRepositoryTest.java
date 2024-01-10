package com.epam.repository;

import com.epam.entity.TrainerWorkload;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@SpringBootTest
@AutoConfigureDataMongo
@ActiveProfiles("test")
class TrainerWorkloadRepositoryTest {
    @Autowired
    private TrainerWorkloadRepository trainerWorkloadRepository;

    @Test
    void createTest() {
        trainerWorkloadRepository.save(new TrainerWorkload(new ObjectId().toString(),
                "Repo.Test",
                "Repo",
                "Test",
                true,
                LocalDate.now(),
                10));
        log.info("Trainer workload created");
    }

    @Test
    void findAllByUsername() {
        List<TrainerWorkload> allByUsername = trainerWorkloadRepository.findAllByUsername("Repo.Test");
        log.info("All trainer workloads by username : {}", allByUsername);
    }

    @Test
    void findFirstByUsername() {
        TrainerWorkload firstByUsername = trainerWorkloadRepository.findFirstByUsername("Repo.Test", 1);
        log.info("Trainer workload by username: {}", firstByUsername);
    }
}