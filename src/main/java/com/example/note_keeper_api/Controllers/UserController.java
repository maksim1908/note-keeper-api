package com.example.note_keeper_api.Controllers;

import com.example.note_keeper_api.DTO.UserDTO;
import com.example.note_keeper_api.Entities.User;
import com.example.note_keeper_api.Execeptions.UserNotFoundException;
import com.example.note_keeper_api.Execeptions.UsernameAlreadyExistException;
import com.example.note_keeper_api.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;

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
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> all = userService.getAll();
        if(all.isEmpty()) {
            return  ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        try {
            User userByUsername = userService.getUserByUsername(username);
            return new ResponseEntity<>(userByUsername,HttpStatus.FOUND);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return  ResponseEntity.ok("User deleted successfully");

        } catch (UserNotFoundException e) {
           return ResponseEntity.notFound().build();
        }
    }
}
