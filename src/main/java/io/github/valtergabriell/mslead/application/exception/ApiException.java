package io.github.valtergabriell.mslead.application.exception;

import org.springframework.http.HttpStatus;

public class ApiException {
    private String message;
    private HttpStatus code;

    public ApiException(String message, HttpStatus code) {
        this.message = message;
        this.code = code;
    }
}
