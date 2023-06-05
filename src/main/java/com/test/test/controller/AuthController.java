package com.test.test.controller;


import com.test.test.constant.PathConstant;
import com.test.test.dto.LoginDTO;
import com.test.test.dto.UserDTO;
import com.test.test.model.User;
import com.test.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(PathConstant.PREFIX_V1)
public class AuthController {


    @Autowired
    private UserService service;


    @PostMapping(value = PathConstant.SIGN_UP)
    public ResponseEntity<User> createUser(@RequestBody UserDTO dto) {
        User user = service.createUser(dto);
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = PathConstant.SIGN_IN)
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO dto) {
        String sessionId = service.loginUser(dto);
        if (sessionId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Cred");
        }
        return ResponseEntity.ok(sessionId);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getByUserId(@PathVariable Long id){
        User user = service.getUserById(id);
        if (Objects.isNull(user)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


}
