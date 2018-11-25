package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SettingExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<SettingErrorResponse> exceptionHandler(SettingNotFoundException e) {

        SettingErrorResponse response = new SettingErrorResponse(HttpStatus.NOT_FOUND.value(),
                e.getMessage(), System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
