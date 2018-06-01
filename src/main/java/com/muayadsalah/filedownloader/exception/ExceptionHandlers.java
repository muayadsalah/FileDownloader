package com.muayadsalah.filedownloader.exception;

import com.muayadsalah.filedownloader.model.ExceptionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Muayad Salah
 */
@ControllerAdvice
public class ExceptionHandlers {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    protected ExceptionHolder entityNotFound(EntityNotFoundException entityNotFoundException) {
        log.error("Handled entity not found exception", entityNotFoundException);
        return new ExceptionHolder(
                HttpStatus.NOT_FOUND.value(),
                "EntityNotFoundException",
                entityNotFoundException.getLocalizedMessage());
    }
}
