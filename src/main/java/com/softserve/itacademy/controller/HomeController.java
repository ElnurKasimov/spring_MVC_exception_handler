package com.softserve.itacademy.controller;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class HomeController {
    private final UserService userService;
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "home"})
    public String home(Model model) {
        model.addAttribute("users", userService.getAll());
        return "home";
    }

    @GetMapping("/sise")
    public void simulateInternalServerError() {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Simulated server error");
    }

    @GetMapping("/snere")
    public void simulateNullEntityReferenceException() {
        throw new NullEntityReferenceException("Simulated create empty user object");
    }

}
