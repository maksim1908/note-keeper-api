package com.example.note_keeper_api;

import com.example.note_keeper_api.Entities.NoteEntity;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication

public class NoteKeeperApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteKeeperApiApplication.class, args);
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
//		NoteEntity note = context.getBean("noteEntity",NoteEntity.class);
//		System.out.println(note.getId() + " " + note.getContent());
//		NoteEntity noteEntityConstructor = context.getBean("noteEntityConstructor", NoteEntity.class);
//		System.out.println(noteEntityConstructor.getId() + " " + noteEntityConstructor.getContent());
//		context.close();
	}
}
