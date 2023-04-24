package io.github.valtergabriell.mslead.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiRequestException {

    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<ApiException> handleApiRequestException(RequestException e) {
        ApiException exception = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
