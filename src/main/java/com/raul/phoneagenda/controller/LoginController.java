package com.raul.phoneagenda.controller;

import com.raul.phoneagenda.dto.CredentialsDTO;
import com.raul.phoneagenda.dto.UserDTO;
import com.raul.phoneagenda.exception.CustomException;
import com.raul.phoneagenda.model.User;
import com.raul.phoneagenda.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/login")
public class LoginController {
    private LoginService loginService;
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    @PostMapping
    public ResponseEntity login(@RequestBody CredentialsDTO credentialsDto) {
        try {
            UserDTO user = loginService.loginUser(credentialsDto);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}