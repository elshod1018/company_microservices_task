package com.company.rabbitmq.consumer;

import com.company.dto.UpdatePasswordDTO;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.update-user.queue.name}")
    public void consumeMessage(UpdatePasswordDTO dto) {
        log.info("Message received from queue: {}", dto.toString());
        userService.updatePassword(dto);
    }
}
