package com.system.exceptionhandling;

public class EntityNotSetNotActiveException extends RuntimeException {
    public EntityNotSetNotActiveException(String message) {
        super(message);
    }
}
