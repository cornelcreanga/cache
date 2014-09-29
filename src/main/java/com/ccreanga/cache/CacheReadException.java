package com.ccreanga.cache;

public class CacheReadException extends RuntimeException {

    public CacheReadException(String message) {
        super(message);
    }

    public CacheReadException(Throwable cause) {
        super(cause);
    }
}
