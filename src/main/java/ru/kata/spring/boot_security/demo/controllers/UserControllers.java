package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServicelmpl;

@Controller
@RequestMapping("/user")
public class UserControllers {

    private final UserServicelmpl userService;

    @Autowired
    public UserControllers(UserServicelmpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "user/user";
    }

}
