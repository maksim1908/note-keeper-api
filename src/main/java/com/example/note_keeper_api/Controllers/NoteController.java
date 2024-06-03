package com.example.note_keeper_api.Controllers;

import com.example.note_keeper_api.Entities.NoteEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NoteController {

    @GetMapping("/notes")
    public ResponseEntity getAll(){
        return new ResponseEntity<>(new NoteEntity(2,"sdsds","22"),HttpStatus.CREATED);
    }
    ///////////////

    @GetMapping("/notes/{id}")
    public ResponseEntity getById(@PathVariable("id") int id){
        return new ResponseEntity<>(new NoteEntity(222,"fdfddddddd","fddfdfdfdfdfdfdfdfdfdf"), HttpStatus.OK);
    }
}
