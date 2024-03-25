package br.com.samueltorga.reactor.handler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Getter
@Component
@Slf4j
public class Receiver {

    @SuppressWarnings("unused")
    public void receiveMessage(String message) {
        log.trace("Received <{}>", message);
    }

}
