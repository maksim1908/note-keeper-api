package org.example.NoteKeeperApi.Service;

import org.example.NoteKeeperApi.Entity.User;
import org.example.NoteKeeperApi.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public class BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);
    @Autowired
    private UserRepo userRepo;

    protected User getActiveUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepo.findByUsername(auth.getName());
        if (user.isPresent()) {
            return user.get();
        } else {
            String errorMsg = "Cannot get UserDetails from Security context";
            LOGGER.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
    }
}
