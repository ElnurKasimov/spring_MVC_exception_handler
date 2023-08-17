package com.softserve.itacademy.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler (EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException (EntityNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error_page");
        modelAndView.addObject("title", "Error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("localDateTime", LocalDateTime.now());
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
    public ModelAndView handleNullEntityReferenceException (NullEntityReferenceException  e) {
        ModelAndView modelAndView = new ModelAndView("error_page");
        modelAndView.addObject("title", "Error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("localDateTime", LocalDateTime.now());
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


}
