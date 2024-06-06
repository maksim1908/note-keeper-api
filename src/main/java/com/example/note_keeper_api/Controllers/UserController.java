package com.example.note_keeper_api.Controllers;

import com.example.note_keeper_api.Entities.User;
import com.example.note_keeper_api.Execeptions.UsernameAlreadyExistException;
import com.example.note_keeper_api.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.save(user);
            return  ResponseEntity.ok("User registered successfully");
        } catch (UsernameAlreadyExistException e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> all = userService.getAll();
        if(all.isEmpty()) {
            return  ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

}
