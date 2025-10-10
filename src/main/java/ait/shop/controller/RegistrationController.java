package ait.shop.controller;

import ait.shop.model.dto.UserRegistrationDTO;
import ait.shop.security.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String register(@RequestBody UserRegistrationDTO registrationDTO) {
        userService.register(registrationDTO);
        return "Registration complete. Please check your email";
    }
}
