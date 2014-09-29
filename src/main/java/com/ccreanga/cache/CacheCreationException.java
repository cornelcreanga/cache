package com.ccreanga.cache;


public class CacheCreationException extends RuntimeException {

    public CacheCreationException(String message) {
        super(message);
    }

    public CacheCreationException(Throwable cause) {
        super(cause);
    }
}
