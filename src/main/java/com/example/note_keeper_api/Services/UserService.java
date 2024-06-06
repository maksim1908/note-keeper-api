package com.example.note_keeper_api.Services;

import com.example.note_keeper_api.DTO.UserDTO;
import com.example.note_keeper_api.Entities.User;
import com.example.note_keeper_api.Execeptions.UserNotFoundException;
import com.example.note_keeper_api.Execeptions.UsernameAlreadyExistException;
import com.example.note_keeper_api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void save(User user) throws UsernameAlreadyExistException {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            throw new UsernameAlreadyExistException(String.format("User with username {%s} already exist",user.getUsername()));
        }
        userRepository.save(user);
    }
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::toUserDTO)
                .toList();
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if(byUsername.isEmpty()){
            throw new UserNotFoundException("User with this username don't exist");
        }
        return byUsername.get();
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    public void deleteById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

}
