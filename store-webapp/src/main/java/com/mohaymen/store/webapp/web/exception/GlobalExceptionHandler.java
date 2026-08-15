package com.mohaymen.store.webapp.web.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Author: Mehran
 * Created on: 8/8/26
 */


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(
            RuntimeException exception,
            Model model) {

        model.addAttribute("errorMessage", exception.getMessage());

        return "error";
    }
}