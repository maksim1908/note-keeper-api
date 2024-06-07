package com.example.note_keeper_api;

import com.example.note_keeper_api.Services.UserService;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ComponentScan("com.example.note_keeper_api")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories
public class SpringConfig {
//    @Bean
//    public NoteEntity noteEntity(){
//        return new NoteEntity();
//    }
//
//    @Bean
//    @Scope("prototype")
//    public NoteEntity noteEntityConstructor(){
//        return new NoteEntity(1,"example","dffddf");
//    }


}
