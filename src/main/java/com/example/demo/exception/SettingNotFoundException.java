package com.example.demo.exception;

public class SettingNotFoundException extends RuntimeException {
    public SettingNotFoundException() {
        super();
    }

    public SettingNotFoundException(String message) {
        super(message);
    }

    public SettingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
