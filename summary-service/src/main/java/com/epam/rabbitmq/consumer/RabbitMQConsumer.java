package com.epam.rabbitmq.consumer;

import com.epam.dto.TrainerWorkloadDTO;
import com.epam.entity.TrainerWorkload;
import com.epam.service.WorkloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private final WorkloadService workloadService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeMessage(TrainerWorkloadDTO dto) {
        log.info("Message received from queue: {}", dto.toString());
        TrainerWorkload workload = workloadService.workload(dto);
    }
}
