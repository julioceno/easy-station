package com.easy_station.sso.controllers;

import com.easy_station.sso.dto.user.RegisterDTO;
import com.easy_station.sso.domain.user.User;
import com.easy_station.sso.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody RegisterDTO dto) {
        User user = this.userService.create(dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = this.userService.findAll();
        return ResponseEntity.ok(users);
    }
}
