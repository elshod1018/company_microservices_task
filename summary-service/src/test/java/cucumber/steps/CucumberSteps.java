package cucumber.steps;

import com.epam.SummaryServiceApplication;
import com.epam.client.MainServiceClient;
import com.epam.dto.TrainerCreateDTO;
import com.epam.dto.TrainerWorkloadDTO;
import com.epam.dto.TrainerWorkloadUpdateDTO;
import com.epam.entity.TrainerWorkload;
import com.epam.service.WorkloadService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@CucumberContextConfiguration
@ContextConfiguration(classes = {SummaryServiceApplication.class})
public class CucumberSteps {
    @Autowired
    private WorkloadService workloadService;
    @Autowired
    private MainServiceClient mainServiceClient;

    @Given("Workload Trainer")
    public void workloadTrainer() {
        TrainerCreateDTO dto = new TrainerCreateDTO("Workload", "Trainer", 1);
        mainServiceClient.createTrainerWithPassword(dto, "12345678");
    }

    @When("Add to workload")
    public void addToWorkload() throws Exception {
        TrainerWorkloadDTO dto = new TrainerWorkloadDTO("Workload.Trainer", "Workload", "Trainer", true, LocalDate.now(), 20, "ADD");
        workloadService.workload(dto);
    }

    @Then("Workload should be returned")
    public void workloadShouldBeReturned() throws Exception {
        List<TrainerWorkload> workloads = workloadService.getWorkloadsByUsername("Workload.Trainer");
        assertThat(workloads).isNotEmpty();
    }

    @Given("Workload Update Trainer")
    public void createTrainerUpdate() {
        TrainerCreateDTO dto = new TrainerCreateDTO("Update", "Workload", 1);
        mainServiceClient.createTrainerWithPassword(dto, "12345678");
        TrainerWorkloadDTO workloadDTO = new TrainerWorkloadDTO("Update.Workload", "Update", "Workload", true, LocalDate.now(), 20, "ADD");
        workloadService.workload(workloadDTO);
    }

    @When("Update workload")
    public void updateWorkload() throws Exception {
        TrainerWorkloadUpdateDTO dto = new TrainerWorkloadUpdateDTO("Update", "Workload", true, LocalDate.now(), 10);
        TrainerWorkload update = workloadService.update("Update.Workload", dto);
        assertThat(update).isNotNull();
    }

    @Then("Updated Workload should be returned")
    public void workloadShouldBeUpdated() throws Exception {
        List<TrainerWorkload> workloads = workloadService.getWorkloadsByUsername("Update.Workload");
        assertThat(workloads).isNotEmpty();
        assertThat(workloads.get(0).getDuration()).isEqualTo(10);
    }
}
