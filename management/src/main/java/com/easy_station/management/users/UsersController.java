package com.easy_station.management.users;

import com.easy_station.management.common.annotation.Role;
import com.easy_station.management.common.enums.UserRoleEnum;
import com.easy_station.management.users.dto.CreateUserDTO;
import com.easy_station.management.users.dto.UserDTO;
import com.easy_station.management.users.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    @Autowired
    private UsersService userService;

    @Role(UserRoleEnum.ADMIN)
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid CreateUserDTO dto) {
        UserDTO userDTO = userService.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userDTO.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(userDTO);
    }
}
