package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.service.UserServicelmpl;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final UserServicelmpl userService;

    @Autowired
    public AdminRestController(UserServicelmpl userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<UserDTO> showUser(@PathVariable(value = "id") int id){
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("/create")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return ResponseEntity.ok().body(userDTO);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> removeUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/authorizedUser")
    public ResponseEntity<UserDTO> getAuthorizedUser(){
        return ResponseEntity.ok().body(userService.getAuthorizedUser());
    }
}
