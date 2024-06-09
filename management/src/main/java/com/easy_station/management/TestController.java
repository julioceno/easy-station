package com.easy_station.management;

import br.com.easy_station.sso.User;
import com.easy_station.management.grpc.SSOClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController(value = "/")
public class TestController {
    @Autowired
    public SSOClient ssoClient;

    @GetMapping(value = "/{id}")
    public ResponseEntity test(@PathVariable String id) {
        User user = ssoClient.findById(id);
        return ResponseEntity.ok(user.getEmail());
    }
}