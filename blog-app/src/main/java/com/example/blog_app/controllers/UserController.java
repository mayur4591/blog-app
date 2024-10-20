package com.example.blog_app.controllers;

import com.example.blog_app.payloads.UserDto;
import com.example.blog_app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    private ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUserDto = userService.createUser(userDto);
        return  new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    private ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
       UserDto updateduser = userService.updateUser(userDto,userId);
       return ResponseEntity.ok(updateduser);
    }

    @DeleteMapping("/{userId}")
    private void deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/")
    private ResponseEntity<List<UserDto>> getUsers()
    {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{userId}")
    private ResponseEntity<UserDto> getSingleUser(@PathVariable int userId)
    {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

}
