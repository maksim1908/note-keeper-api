package org.example.NoteKeeperApi.Exception.ServiceExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException() {
        super("Note Not Found");
    }

    public NoteNotFoundException(String message) {
        super(message);
    }
}
