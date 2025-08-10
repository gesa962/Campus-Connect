package com.campusconnect.config;

import com.campusconnect.entity.Event;
import com.campusconnect.entity.User;
import com.campusconnect.repository.EventRepository;
import com.campusconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Create sample users if they don't exist
        if (userRepository.count() == 0) {
            createSampleUsers();
            createSampleEvents();
        }
    }
    
    private void createSampleUsers() {
        // Create admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@campus.edu");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRole(User.Role.ADMIN);
        admin.setDepartment("Administration");
        userRepository.save(admin);
        
        // Create student users
        User student1 = new User();
        student1.setUsername("john.doe");
        student1.setEmail("john.doe@student.edu");
        student1.setPassword(passwordEncoder.encode("student123"));
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setRole(User.Role.STUDENT);
        student1.setStudentId("STU001");
        student1.setDepartment("Computer Science");
        userRepository.save(student1);
        
        User student2 = new User();
        student2.setUsername("jane.smith");
        student2.setEmail("jane.smith@student.edu");
        student2.setPassword(passwordEncoder.encode("student123"));
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setRole(User.Role.STUDENT);
        student2.setStudentId("STU002");
        student2.setDepartment("Engineering");
        userRepository.save(student2);
        
        User student3 = new User();
        student3.setUsername("mike.johnson");
        student3.setEmail("mike.johnson@student.edu");
        student3.setPassword(passwordEncoder.encode("student123"));
        student3.setFirstName("Mike");
        student3.setLastName("Johnson");
        student3.setRole(User.Role.STUDENT);
        student3.setStudentId("STU003");
        student3.setDepartment("Business");
        userRepository.save(student3);
    }
    
    private void createSampleEvents() {
        User admin = userRepository.findByUsername("admin").get();
        User student1 = userRepository.findByUsername("john.doe").get();
        User student2 = userRepository.findByUsername("jane.smith").get();
        
        // Create sample events
        Event event1 = new Event();
        event1.setTitle("Campus Tech Meetup");
        event1.setDescription("Join us for an exciting tech meetup where students can network with industry professionals and learn about the latest technologies.");
        event1.setLocation("Main Campus Auditorium");
        event1.setEventDateTime(LocalDateTime.now().plusDays(7));
        event1.setRegistrationDeadline(LocalDateTime.now().plusDays(5));
        event1.setMaxParticipants(50);
        event1.setCategory("Technology");
        event1.setOrganizer("Tech Club");
        event1.setCreatedBy(admin);
        eventRepository.save(event1);
        
        Event event2 = new Event();
        event2.setTitle("Spring Sports Tournament");
        event2.setDescription("Annual spring sports tournament featuring basketball, volleyball, and soccer competitions.");
        event2.setLocation("University Sports Complex");
        event2.setEventDateTime(LocalDateTime.now().plusDays(14));
        event2.setRegistrationDeadline(LocalDateTime.now().plusDays(10));
        event2.setMaxParticipants(100);
        event2.setCategory("Sports");
        event2.setOrganizer("Sports Department");
        event2.setCreatedBy(admin);
        eventRepository.save(event2);
        
        Event event3 = new Event();
        event3.setTitle("Career Fair 2024");
        event3.setDescription("Connect with top employers and explore internship and job opportunities across various industries.");
        event3.setLocation("Student Center");
        event3.setEventDateTime(LocalDateTime.now().plusDays(21));
        event3.setRegistrationDeadline(LocalDateTime.now().plusDays(18));
        event3.setMaxParticipants(200);
        event3.setCategory("Career");
        event3.setOrganizer("Career Services");
        event3.setCreatedBy(admin);
        eventRepository.save(event3);
        
        Event event4 = new Event();
        event4.setTitle("Cultural Festival");
        event4.setDescription("Celebrate diversity with music, dance, food, and cultural performances from around the world.");
        event4.setLocation("Campus Green");
        event4.setEventDateTime(LocalDateTime.now().plusDays(28));
        event4.setRegistrationDeadline(LocalDateTime.now().plusDays(25));
        event4.setMaxParticipants(150);
        event4.setCategory("Cultural");
        event4.setOrganizer("International Students Association");
        event4.setCreatedBy(admin);
        eventRepository.save(event4);
        
        // Add some participants to events
        event1.addParticipant(student1);
        event1.addParticipant(student2);
        eventRepository.save(event1);
        
        event2.addParticipant(student1);
        eventRepository.save(event2);
        
        event3.addParticipant(student2);
        event3.addParticipant(student3);
        eventRepository.save(event3);
    }
}
