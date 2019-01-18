package wl.userservice.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wl.userservice.user.entity.User;
import wl.userservice.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(){

        return "success";
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        System.out.println(user);
       return userService.register(user);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
