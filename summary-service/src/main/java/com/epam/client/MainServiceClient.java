package com.epam.client;

import com.epam.dto.TrainerCreateDTO;
import com.epam.dto.TrainerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("${main.service.baseUrl}")
public interface MainServiceClient {
    @GetMapping("/trainer/exist/by-username")
    Boolean existByUsername(@RequestParam String username);

    @PostMapping("/main/public/trainer")
    TrainerDTO createTrainer(@RequestBody TrainerCreateDTO dto);
}
