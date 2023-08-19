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

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView handleNoHandlerFoundException (HttpServletRequest request, NoHandlerFoundException  e, ModelAndView modelAndView) {
        modelAndView = new ModelAndView("404");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNotFoundException(HttpServletRequest request, EntityNotFoundException e) {
        return getModelAndView(request, HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(NullEntityReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNullEntityReferenceException(HttpServletRequest request, NullEntityReferenceException e) {
        return getModelAndView(request, HttpStatus.BAD_REQUEST, e);
    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ModelAndView handleOtherExceptions(HttpServletRequest request, Exception e) {
//        return getModelAndView(request, HttpStatus.INTERNAL_SERVER_ERROR, e);
//    }
//
    private ModelAndView getModelAndView(HttpServletRequest request, HttpStatus httpStatus, Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("title", "Error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("localDateTime", LocalDateTime.now());
        modelAndView.addObject("status", httpStatus.value() + " / " + httpStatus.getReasonPhrase() );
        modelAndView.addObject("type", e.getClass().getName());
        return modelAndView;
    }

}
