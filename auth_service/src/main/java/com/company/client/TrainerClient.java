package com.company.client;

import com.company.dto.TrainerCreateDTO;
import com.company.dto.TrainerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("${main.service.trainerUrl}")
public interface TrainerClient {
    @PostMapping("")
    TrainerDTO create(@RequestBody TrainerCreateDTO dto);
}
