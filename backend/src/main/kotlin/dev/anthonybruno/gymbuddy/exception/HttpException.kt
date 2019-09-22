package dev.anthonybruno.gymbuddy.exception;

import java.util.Map;

public class HttpException extends RuntimeException {

    private final int statusCode;
    private final String message;

    public HttpException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Map<String, Object> serialise() {
        return Map.of("message", message, "statusCode", statusCode);
    }
}
