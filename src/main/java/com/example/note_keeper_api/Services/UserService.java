package com.example.note_keeper_api.Services;

import com.example.note_keeper_api.Entities.User;
import com.example.note_keeper_api.Execeptions.UsernameAlreadyExistException;
import com.example.note_keeper_api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User save(User user) throws UsernameAlreadyExistException {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            throw new UsernameAlreadyExistException(String.format("User with username {%s} already exist",user.getUsername()));
        }
        return userRepository.save(user);
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
