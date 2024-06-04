package com.easy_station.sso.users;

import com.easy_station.sso.auth.services.ValidateTokenService;
import com.easy_station.sso.users.dto.CreateUserDTO;
import com.easy_station.sso.users.dto.UpdatePasswordDTO;
import com.easy_station.sso.users.dto.UpdateUserDTO;
import com.easy_station.sso.users.dto.UserReturnDTO;
import com.easy_station.sso.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ValidateTokenService validateTokenService;

    @PostMapping
    public ResponseEntity<UserReturnDTO> create(@RequestBody CreateUserDTO dto) {
        UserReturnDTO user = this.userService.create(dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserReturnDTO>> findAll() {
        List<UserReturnDTO> users = this.userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReturnDTO> findOne(@PathVariable String id) {
        UserReturnDTO user = this.userService.findOne(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<UserReturnDTO> updatePassword(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody UpdatePasswordDTO dto
    ) {
        String token = bearerToken.replace("Bearer ", "");
        String decodedToken = validateTokenService.run(token);
        UserReturnDTO user = this.userService.updatePassword(decodedToken, dto);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserReturnDTO> update(@PathVariable String id, @RequestBody UpdateUserDTO dto) {
        UserReturnDTO user = this.userService.update(id, dto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
