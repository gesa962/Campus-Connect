package com.campusconnect.service;

import com.campusconnect.dto.EventDto;
import com.campusconnect.entity.Event;
import com.campusconnect.entity.User;
import com.campusconnect.repository.EventRepository;
import com.campusconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public EventDto createEvent(EventDto eventDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Only admins can create events
        if (user.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("Only admins can create events");
        }
        
        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setLocation(eventDto.getLocation());
        event.setEventDateTime(eventDto.getEventDateTime());
        event.setRegistrationDeadline(eventDto.getRegistrationDeadline());
        event.setMaxParticipants(eventDto.getMaxParticipants());
        event.setCategory(eventDto.getCategory());
        event.setOrganizer(eventDto.getOrganizer());
        event.setCreatedBy(user);
        
        Event savedEvent = eventRepository.save(event);
        return EventDto.fromEntity(savedEvent);
    }
    
    public EventDto updateEvent(Long eventId, EventDto eventDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        // Only the creator (admin) can update the event
        if (!event.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("You can only update events you created");
        }
        
        // Update fields
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getLocation() != null) {
            event.setLocation(eventDto.getLocation());
        }
        if (eventDto.getEventDateTime() != null) {
            event.setEventDateTime(eventDto.getEventDateTime());
        }
        if (eventDto.getRegistrationDeadline() != null) {
            event.setRegistrationDeadline(eventDto.getRegistrationDeadline());
        }
        if (eventDto.getMaxParticipants() > 0) {
            event.setMaxParticipants(eventDto.getMaxParticipants());
        }
        if (eventDto.getCategory() != null) {
            event.setCategory(eventDto.getCategory());
        }
        if (eventDto.getOrganizer() != null) {
            event.setOrganizer(eventDto.getOrganizer());
        }
        
        Event updatedEvent = eventRepository.save(event);
        return EventDto.fromEntity(updatedEvent);
    }
    
    public EventDto getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return EventDto.fromEntity(event);
    }
    
    public List<EventDto> getAllActiveEvents() {
        return eventRepository.findByIsActiveTrueOrderByEventDateTimeAsc().stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<EventDto> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now()).stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<EventDto> getEventsWithOpenRegistration() {
        return eventRepository.findEventsWithOpenRegistration(LocalDateTime.now()).stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<EventDto> getEventsByCategory(String category) {
        return eventRepository.findByCategoryAndIsActiveTrueOrderByEventDateTimeAsc(category).stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<EventDto> getEventsByCreator(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return eventRepository.findByCreatedByOrderByCreatedAtDesc(user).stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<EventDto> getEventsByParticipant(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return eventRepository.findEventsByParticipant(user.getId()).stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<EventDto> searchEvents(String keyword) {
        return eventRepository.searchEventsByKeyword(keyword).stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    public EventDto joinEvent(Long eventId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        if (!event.isActive()) {
            throw new RuntimeException("Event is not active");
        }
        
        if (!event.isRegistrationOpen()) {
            throw new RuntimeException("Registration is closed for this event");
        }
        
        if (event.isEventFull()) {
            throw new RuntimeException("Event is full");
        }
        
        if (event.getParticipants().contains(user)) {
            throw new RuntimeException("You are already registered for this event");
        }
        
        event.addParticipant(user);
        Event savedEvent = eventRepository.save(event);
        return EventDto.fromEntity(savedEvent);
    }
    
    public EventDto leaveEvent(Long eventId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        if (!event.getParticipants().contains(user)) {
            throw new RuntimeException("You are not registered for this event");
        }
        
        event.removeParticipant(user);
        Event savedEvent = eventRepository.save(event);
        return EventDto.fromEntity(savedEvent);
    }
    
    public void deleteEvent(Long eventId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        // Only the creator (admin) can delete the event
        if (!event.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete events you created");
        }
        
        eventRepository.deleteById(eventId);
    }
    
    public EventDto deactivateEvent(Long eventId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        // Only the creator (admin) can deactivate the event
        if (!event.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("You can only deactivate events you created");
        }
        
        event.setActive(false);
        Event savedEvent = eventRepository.save(event);
        return EventDto.fromEntity(savedEvent);
    }
}
