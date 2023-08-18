package com.softserve.itacademy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {
//
//    @ExceptionHandler (EntityNotFoundException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ModelAndView handleEntityNotFoundException (HttpServletRequest request, EntityNotFoundException e, ModelAndView modelAndView) {
//        modelAndView.setViewName("error");
//        modelAndView.addObject("title", "Error");
//        modelAndView.addObject("message", e.getMessage());
//        modelAndView.addObject("localDateTime", LocalDateTime.now());
//        modelAndView.addObject("status",  ResponseStatus.class.getName());
//        modelAndView.addObject("type", e.getClass().getName());
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);
//        String stackTrace = sw.toString();
//        String debugPropertyValue = System.getProperty("debug");
//        String debugEnvValue = System.getenv("DEBUG");
//        if( (debugPropertyValue != null && debugPropertyValue.equals("true")) ||
//                (debugEnvValue != null && debugEnvValue.equals("true")) ) {
//            modelAndView.addObject("stackTrace", stackTrace);
//        }
//        return modelAndView;
//    }
//
//    @ExceptionHandler (NullEntityReferenceException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ModelAndView handleNullEntityReferenceException (NullEntityReferenceException  e, HttpServletResponse response, ModelAndView modelAndView) {
//        modelAndView.setViewName("error");
//        modelAndView.addObject("title", "Error");
//        modelAndView.addObject("message", e.getMessage());
//        modelAndView.addObject("localDateTime", LocalDateTime.now());
//        modelAndView.addObject("status", ResponseStatus.class.getName());
//        modelAndView.addObject("type", e.getClass().getName());
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);
//        String stackTrace = sw.toString();
//        String debugPropertyValue = System.getProperty("debug");
//        String debugEnvValue = System.getenv("DEBUG");
//        if( (debugPropertyValue != null && debugPropertyValue.equals("true")) ||
//                (debugEnvValue != null && debugEnvValue.equals("true")) ) {
//            modelAndView.addObject("stackTrace", stackTrace);
//        }
//        return modelAndView;
//    }
//
//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(value= HttpStatus.NOT_FOUND)
//    public ModelAndView handleNoHandlerFoundException (HttpServletRequest request, NoHandlerFoundException  e, ModelAndView modelAndView) {
//         modelAndView.setViewName("404");
//        modelAndView.setStatus(HttpStatus.NOT_FOUND);
//        return modelAndView;
//    }
//
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ModelAndView handleOtherExceptions(HttpServletRequest request, Exception e, ModelAndView modelAndView) {
//        modelAndView.addObject("title", "Server Error");
//        modelAndView.setViewName("500");
//        modelAndView.addObject("message", e.getMessage());
//        modelAndView.addObject("localDateTime", LocalDateTime.now());
//        modelAndView.addObject("status", "INTERNAL_SERVER_ERROR");
//        modelAndView.addObject("type", e.getClass().getName());
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);
//        String stackTrace = sw.toString();
//        String debugPropertyValue = System.getProperty("debug");
//        String debugEnvValue = System.getenv("DEBUG");
//        if( (debugPropertyValue != null && debugPropertyValue.equals("true")) ||
//                (debugEnvValue != null && debugEnvValue.equals("true")) ) {
//            modelAndView.addObject("stackTrace", stackTrace);
//        }
//        return modelAndView;
//    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(HttpServletRequest request, EntityNotFoundException e) {
        return getModelAndView(request, HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(NullEntityReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNullEntityReferenceException(HttpServletRequest request, NullEntityReferenceException e) {
        return getModelAndView(request, HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleOtherExceptions(HttpServletRequest request, Exception e) {
        return getModelAndView(request, HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    private ModelAndView getModelAndView(HttpServletRequest request, HttpStatus httpStatus, Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("title", "Server Error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("localDateTime", LocalDateTime.now());
        modelAndView.addObject("status", httpStatus.value() + " / " + httpStatus.getReasonPhrase() );
        modelAndView.addObject("type", e.getClass().getName());
        return modelAndView;
    }

}
