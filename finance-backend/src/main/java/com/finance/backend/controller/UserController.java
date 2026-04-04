package com.finance.backend.controller;

import com.finance.backend.model.Role;
import com.finance.backend.model.User;
import com.finance.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users", description = "Accessible by Admin only")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // In a real application, we'd use DTOs for requests to hold separate fields. 
    // Here we use query params for a quick Admin testing endpoint.
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user", description = "Accessible by Admin only")
    public User createUser(@RequestParam String username, @RequestParam String password, @RequestParam Role role) {
        return userService.createUser(username, password, role);
    }
}
