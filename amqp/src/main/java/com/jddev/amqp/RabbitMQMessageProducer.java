package com.jddev.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitMQMessageProducer {

    private final AmqpTemplate amqpTemplate;

    public void publish(Object paylod, String exchange, String routingKey){
        log.info("Publishing to {} using routingKey {}. Payload: {}", exchange, routingKey, paylod);
        amqpTemplate.convertAndSend(exchange, routingKey, paylod);
        log.info("Published to {} using routingKey {}. Payload: {}", exchange, routingKey, paylod);
    }
}
