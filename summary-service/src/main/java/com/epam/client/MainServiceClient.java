package com.epam.client;

import com.epam.dto.TrainerCreateDTO;
import com.epam.dto.TrainerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("${main.service.baseUrl}")
public interface MainServiceClient {
    @GetMapping("/trainer/exist/by-username")
    Boolean existByUsername(@RequestParam String username);

    @PostMapping("/public/trainer")
    TrainerDTO createTrainer(@RequestBody TrainerCreateDTO dto);

    @PostMapping("/public/trainer/{password:.*}")
    TrainerDTO createTrainerWithPassword(@RequestBody TrainerCreateDTO dto, @PathVariable String password);
}
