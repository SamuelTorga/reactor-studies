package br.com.samueltorga.reactor.exception;

public class InfraException extends RuntimeException {
    public InfraException(Exception e) {
        super(e);
    }
}
