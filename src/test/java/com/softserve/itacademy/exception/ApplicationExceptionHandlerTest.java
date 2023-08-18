package com.softserve.itacademy.exception;

import com.softserve.itacademy.service.*;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApplicationExceptionHandler.class)
class ApplicationExceptionHandlerTest {
    private ApplicationExceptionHandler myHandler;
    @Getter
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;
    @MockBean
    private ToDoService toDoService;
    @MockBean
    private TaskService taskService;
    @MockBean
    private StateService stateService;


    @Test
    @DisplayName("Test that 404 exception is handled")
    public void handle404Test () throws Exception {
        //given
        myHandler = new ApplicationExceptionHandler();
        NoHandlerFoundException e = new NoHandlerFoundException("GET", "/home/any", null);
        //when
        String actual = myHandler.handleNotFoundException(e);
        //then
        assertEquals("404", actual);
        mockMvc.perform(MockMvcRequestBuilders.get("/home/any"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("Test that 500 exception is handled")
    public void handle500Test () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/simulate-error"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

}