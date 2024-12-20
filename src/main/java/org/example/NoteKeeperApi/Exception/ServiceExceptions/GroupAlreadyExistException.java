package org.example.NoteKeeperApi.Exception.ServiceExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GroupAlreadyExistException extends RuntimeException {
    public GroupAlreadyExistException() {
        super("Group with this title already exists");
    }

    public GroupAlreadyExistException(Long id) {
        super(String.format("Group with id %s already exists", id));
    }
}
