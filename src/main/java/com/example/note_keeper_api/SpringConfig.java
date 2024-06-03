package com.example.note_keeper_api;

import com.example.note_keeper_api.Entities.NoteEntity;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.example.note_keeper_api")
@PropertySource("classpath:application.properties")
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
