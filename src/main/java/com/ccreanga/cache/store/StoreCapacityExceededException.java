package com.ccreanga.cache.store;

public class StoreCapacityExceededException extends RuntimeException {

    public StoreCapacityExceededException(String message) {
        super(message);
    }

    public StoreCapacityExceededException(Throwable cause) {
        super(cause);
    }
}
