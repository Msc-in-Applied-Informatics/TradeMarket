package com.eshop.Controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizeController {

    private final AuthenticationManager authenticationManager = null;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        UserDetails user = (UserDetails) authentication.getPrincipal();

        return "Login successful as: " + user.getUsername();
    }

    @GetMapping("/user/test")
    public String userTest() {
        return "USER or ADMIN allowed";
    }

    @GetMapping("/admin/test")
    public String adminTest() {
        return "Only ADMIN allowed";
    }
}
