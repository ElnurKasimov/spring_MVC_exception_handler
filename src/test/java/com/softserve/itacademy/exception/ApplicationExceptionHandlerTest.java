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
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class ApplicationExceptionHandlerTest {
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
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
                //      .andExpect(MockMvcResultMatchers.view().name("404"));

    }
}