package com.epam.ex_handlers.handlers;

import com.epam.dto.AppErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppErrorDTO handleUnknownExceptions(Exception e, HttpServletRequest request) {
        log.error("Unknown exception occurred message = '{}'", e.getMessage());
        return new AppErrorDTO(request.getRequestURI(), e.getMessage(), 500);
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppErrorDTO handleRuntimeExceptions(RuntimeException e, HttpServletRequest request) {
        log.error("Runtime exception occurred  message = '{}'", e.getMessage());
        return new AppErrorDTO(request.getRequestURI(), e.getMessage(), 404);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String errorMessage = "Input is not valid";
        Map<String, List<String>> errorBody = getStringListMap(e);
        String errorPath = request.getRequestURI();
        log.error("Exception occurred  message = '{}'", e.getMessage());
        return new AppErrorDTO(errorPath, errorMessage, errorBody, 400);
    }

    private static Map<String, List<String>> getStringListMap(MethodArgumentNotValidException e) {
        Map<String, List<String>> errorBody = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorBody.compute(field, (s, values) -> {
                if (!Objects.isNull(values)) {
                    values.add(message);
                } else {
                    values = new ArrayList<>(Collections.singleton(message));
                }
                return values;
            });
        }
        return errorBody;
    }
}
