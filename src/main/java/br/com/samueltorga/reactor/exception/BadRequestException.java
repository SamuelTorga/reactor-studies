package br.com.samueltorga.reactor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class BadRequestException extends ErrorResponseException {
    public BadRequestException(Exception e) {
        super(HttpStatus.BAD_REQUEST, getProblemDetail(e), e);
    }

    private static ProblemDetail getProblemDetail(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

}
