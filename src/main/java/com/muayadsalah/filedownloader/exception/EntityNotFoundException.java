package com.muayadsalah.filedownloader.exception;

/**
 * Error that will be thrown when an entity or resource is not found
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class entityClass, String id) {
        super("Could not find " + entityClass.getSimpleName() + " with id: " + id);
    }
}