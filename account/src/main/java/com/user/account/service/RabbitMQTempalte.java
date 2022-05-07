package com.user.account.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.account.dto.ShareDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableRabbit
@RequiredArgsConstructor
public class RabbitMQTempalte {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @RabbitListener(queues = "${share.queue}")
    public String receiveMessage(Message message) {
        var shares = objectMapper.readValue
                (message.getBody(), new TypeReference<List<ShareDto>>() {
        });

        shares.forEach(shareDto -> log.info("get Share {}", shareDto.toString()));
        log.info("якобы одбираются акции по уровню рисков");
        Thread.sleep(1000);
        return objectMapper.writeValueAsString(shares);
    }

}
