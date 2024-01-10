package com.epam.service;

import com.epam.client.MainServiceClient;
import com.epam.dto.TrainerCreateDTO;
import com.epam.dto.TrainerDTO;
import com.epam.dto.TrainerWorkloadDTO;
import com.epam.entity.TrainerWorkload;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
class WorkloadServiceTest {
    @Autowired
    private WorkloadService workloadService;
    @Autowired
    private MainServiceClient mainServiceClient;

    @Test
    void workload() {
        TrainerWorkloadDTO dto = new TrainerWorkloadDTO("NewAdd", "FirstName", "LastName", true, LocalDate.now(), 20, "ADD");
        TrainerWorkload workload = workloadService.workload(dto);
        assertNotNull(workload);
        assertEquals("NewAdd", workload.getUsername());
        assertEquals("FirstName", workload.getFirstName());
        assertEquals("LastName", workload.getLastName());
        assertTrue(workload.isActive());
        assertEquals(LocalDate.now(), workload.getDate());
        assertEquals(20, workload.getDuration());
        log.info("Workload created: {}", workload);
    }

    @Test
    void getWorkloads() {
        List<TrainerWorkload> workloads = workloadService.getWorkloads();
        assertNotNull(workloads);
        log.info("Workloads: {}", workloads);
    }

    @Test
    void getWorkloadsByUsername() throws UserPrincipalNotFoundException {
        TrainerDTO dto = mainServiceClient.createTrainer(new TrainerCreateDTO("Workloads", "Get", 1));
        workloadService.workload(new TrainerWorkloadDTO(dto.username(), dto.firstName(), dto.lastName(), dto.active(), LocalDate.now(), 20, "ADD"));
        List<TrainerWorkload> workloads = workloadService.getWorkloadsByUsername("getWorkloads");
        assertNotNull(workloads);
        log.info("Workloads: {}", workloads);
    }

    @Test
    void getWorkloadsByUsernameThrowsException() {
        assertThrows(RuntimeException.class, () -> workloadService.workload(new TrainerWorkloadDTO("noUsername",
                "Workload",
                "Get",
                true,
                LocalDate.now(),
                20,
                "ADD")));
        assertThrows(UserPrincipalNotFoundException.class, () -> workloadService.getWorkloadsByUsername("noUsername"));
    }
}