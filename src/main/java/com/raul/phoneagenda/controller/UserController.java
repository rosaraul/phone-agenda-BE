package com.raul.phoneagenda.controller;

import com.raul.phoneagenda.dto.UserDTO;
import com.raul.phoneagenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/view_users")
    public ResponseEntity<List<UserDTO>> seeAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.deleteUserById(id),HttpStatus.OK);

    }
    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.OK);
    }
    @GetMapping("/view_users/id/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) throws Exception {
        return  new ResponseEntity<>(userService.findUserById(id),HttpStatus.OK);
    }
    @GetMapping("/view_users/name/{name}")
    public ResponseEntity<List<UserDTO>> findUserByName(@PathVariable String name) throws Exception {
        return  new ResponseEntity<>(userService.findUserByName(name),HttpStatus.OK);
    }

}
