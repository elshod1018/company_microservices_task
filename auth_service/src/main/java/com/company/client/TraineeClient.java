package com.company.client;

import com.company.dto.TraineeCreateDTO;
import com.company.dto.TraineeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("${main.service.traineeUrl}")
public interface TraineeClient {
    @PostMapping("")
    TraineeDTO create(@RequestBody TraineeCreateDTO dto);
}
