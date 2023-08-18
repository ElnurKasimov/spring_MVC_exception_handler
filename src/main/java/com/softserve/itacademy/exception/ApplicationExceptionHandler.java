package com.softserve.itacademy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler (EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException (EntityNotFoundException e, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("title", "Error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("localDateTime", LocalDateTime.now());
        modelAndView.addObject("status", response.getStatus());
        modelAndView.addObject("type", e.getClass().getName());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        String debugPropertyValue = System.getProperty("debug");
        String debugEnvValue = System.getenv("DEBUG");
        if( (debugPropertyValue != null && debugPropertyValue.equals("true")) ||
                (debugEnvValue != null && debugEnvValue.equals("true")) ) {
            modelAndView.addObject("stackTrace", stackTrace);
        }
        return modelAndView;
    }

    @ExceptionHandler (NullEntityReferenceException.class)
    public ModelAndView handleNullEntityReferenceException (NullEntityReferenceException  e, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("title", "Error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("localDateTime", LocalDateTime.now());
        modelAndView.addObject("status", response.getStatus());
        modelAndView.addObject("type", e.getClass().getName());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        String debugPropertyValue = System.getProperty("debug");
        String debugEnvValue = System.getenv("DEBUG");
        if( (debugPropertyValue != null && debugPropertyValue.equals("true")) ||
                (debugEnvValue != null && debugEnvValue.equals("true")) ) {
            modelAndView.addObject("stackTrace", stackTrace);
        }
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException (Exception  e) {
        return new ModelAndView("404");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleServerErrorException (Exception  e) {
        return new ModelAndView("500");
    }

}
