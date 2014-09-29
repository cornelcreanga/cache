package com.ccreanga.cache;

public class CacheWriteException extends RuntimeException {

    public CacheWriteException(String message) {
        super(message);
    }

    public CacheWriteException(Throwable cause) {
        super(cause);
    }
}