package com.example.note_keeper_api.Services;

import com.example.note_keeper_api.DTO.UserDTO;
import com.example.note_keeper_api.Entities.Role;
import com.example.note_keeper_api.Entities.User;
import com.example.note_keeper_api.Execeptions.UserNotFoundException;
import com.example.note_keeper_api.Execeptions.UsernameAlreadyExistException;
import com.example.note_keeper_api.Repositories.RoleRepository;
import com.example.note_keeper_api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService() {}
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void save(User user) throws UsernameAlreadyExistException {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            throw new UsernameAlreadyExistException(String.format("User with username {%s} already exist",user.getUsername()));
        }

        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);

    }
}
