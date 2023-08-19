package com.softserve.itacademy.exception;

import com.softserve.itacademy.controller.UserController;
import com.softserve.itacademy.service.*;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        mockMvc.perform(MockMvcRequestBuilders.get("/home/any"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.view().name("404"));
    }

    @Test
    @DisplayName("Test that 500 exception is handled")
    public void handle500Test () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/simulate-error"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

//    @Test
//    void shouldReturnNotFoundError() throws Exception {
//        // given
//        String errorMessage = "User with id: 9 does not exist";
//        when(userService.readById(9)).thenThrow(new EntityNotFoundException(errorMessage));
//        // when
//        mockMvc.perform(get("/users/9/read")
//                )
//                // then
//                .andExpect(status().isNotFound())
//                .andExpect(model().attribute("title",  "Error"))
//                .andExpect(model().attribute("message", is(errorMessage)))
//                .andExpect(view().name("error"));
//        verify(userService).readById(9);
//    }


//    @Test
//    void shouldReturnNullEntityReferenceError() throws Exception {
//        // given
//        String errorMessage = "Cannot create empty user object";
//        when(userService.create(any())).thenThrow(new NullEntityReferenceException(errorMessage));
//        // when
//        mockMvc.perform(post("/users/create")
//                )
//                // then
//                .andExpect(status().isNotFound())
//                .andExpect(model().attribute("title",  "Error"))
//                .andExpect(model().attribute("message", is(errorMessage)))
//                .andExpect(view().name("error"));
//        verify(userService).create(any());
//    }


    @Test
    void shouldReturnNotFoundError() throws Exception {

        // given
        String errorMessage = "something not found";
        when(userService.readById(9)).thenThrow(new EntityNotFoundException(errorMessage));

        // when
        mockMvc.perform(get("/users/9/read")
                )

                // then
                .andExpect(status().isNotFound())
                // .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("code", "404 / Not Found"))
                .andExpect(model().attribute("message", is(errorMessage)))
                .andExpect(view().name("error"));
        verify(userService).readById(9);
    }

    @Test
    void shouldReturnNullEntityReferenceError() throws Exception {

        // given
        String errorMessage = "this looks like a zero...";
        when(userService.create(any())).thenThrow(new NullEntityReferenceException(errorMessage));

        // when
        mockMvc.perform(post("/users/create")
                )

                // then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("code", "400 / Bad Request"))
                .andExpect(model().attribute("message", is(errorMessage)))
                .andExpect(view().name("error"));
        verify(userService).create(any());
    }

    @Test
    void shouldReturnServerError() throws Exception {

        // given
        String errorMessage = "WTF!?";
        when(userService.getAll()).thenThrow(new RuntimeException(errorMessage));

        // when
        mockMvc.perform(get("/users/all")
                )

                // then
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("code", "500 / Internal Server Error"))
                .andExpect(model().attribute("message", is(errorMessage)))
                .andExpect(view().name("bad-error"));
        verify(userService).getAll();
    }
}




