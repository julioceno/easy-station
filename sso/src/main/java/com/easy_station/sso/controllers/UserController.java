package com.easy_station.sso.controllers;

import com.easy_station.sso.domain.user.dto.RegisterDTO;
import com.easy_station.sso.domain.user.User;
import com.easy_station.sso.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
