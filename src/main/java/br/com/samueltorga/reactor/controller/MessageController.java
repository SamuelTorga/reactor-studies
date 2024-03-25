package br.com.samueltorga.reactor.controller;

import br.com.samueltorga.reactor.configuration.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("messages")
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
    private final SimpleMessageListenerContainer setupMessageListener;

    @PostMapping
    public Mono<ResponseEntity<Void>> sendMessage(@RequestBody String message) {
        return Mono.fromCallable(() -> {
            rabbitTemplate.convertAndSend(rabbitMQConfig.getTopicExchangeName(), "foo.bar.baz", "%s %s".formatted(message, ZonedDateTime.now()));
            return ResponseEntity.accepted().build();
        });
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> receiveMessage() {
        return Flux.create(emitter -> {
            setupMessageListener.setupMessageListener(m -> {
                String payload = new String(m.getBody());
                emitter.next(payload);
            });
            emitter.onRequest(v -> setupMessageListener.start());
            emitter.onDispose(setupMessageListener::stop);
        });
    }

}
