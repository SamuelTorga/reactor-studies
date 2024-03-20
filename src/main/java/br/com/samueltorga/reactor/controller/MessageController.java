package br.com.samueltorga.reactor.controller;

import br.com.samueltorga.reactor.configuration.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("messages")
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    @PostMapping
    public Mono<Void> sendMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend(rabbitMQConfig.getTopicExchangeName(), "foo.bar.baz", message);
        return Mono.empty();
    }

}
