package br.com.samueltorga.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.logging.Level;

@Slf4j
class ReactorStudiesApplicationTests {

    @Test
    void createMono() {
        String name = "Samuel";
        Mono<String> mono = Mono.just(name);

        log.info("Mono: {}", mono);
        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();
    }

    @Test
    void monoSubscribeConsumer() {
        String name = "Samuel";
        Mono<String> mono = Mono.just(name).log(log.getName(), Level.FINE);

        mono.subscribe(s -> log.info("Value: {}", s));

        log.info("Mono: {}", mono);
        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();
    }

}
