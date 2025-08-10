package com.campusconnect.controller;

import com.campusconnect.dto.EventDto;
import com.campusconnect.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {
    
    @Autowired
    private EventService eventService;
    
    // Public endpoints
    @GetMapping("/public/all")
    public ResponseEntity<List<EventDto>> getAllPublicEvents() {
        List<EventDto> events = eventService.getAllActiveEvents();
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/public/upcoming")
    public ResponseEntity<List<EventDto>> getUpcomingEvents() {
        List<EventDto> events = eventService.getUpcomingEvents();
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/public/open-registration")
    public ResponseEntity<List<EventDto>> getEventsWithOpenRegistration() {
        List<EventDto> events = eventService.getEventsWithOpenRegistration();
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/public/category/{category}")
    public ResponseEntity<List<EventDto>> getEventsByCategory(@PathVariable String category) {
        List<EventDto> events = eventService.getEventsByCategory(category);
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/public/search")
    public ResponseEntity<List<EventDto>> searchEvents(@RequestParam String keyword) {
        List<EventDto> events = eventService.searchEvents(keyword);
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/public/{eventId}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long eventId) {
        EventDto event = eventService.getEventById(eventId);
        return ResponseEntity.ok(event);
    }
    
    // Protected endpoints
    @GetMapping("/my-events")
    public ResponseEntity<List<EventDto>> getMyEvents() {
        String username = getCurrentUsername();
        List<EventDto> events = eventService.getEventsByCreator(username);
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/my-participations")
    public ResponseEntity<List<EventDto>> getMyParticipations() {
        String username = getCurrentUsername();
        List<EventDto> events = eventService.getEventsByParticipant(username);
        return ResponseEntity.ok(events);
    }
    
    @PostMapping
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto eventDto) {
        String username = getCurrentUsername();
        EventDto createdEvent = eventService.createEvent(eventDto, username);
        return ResponseEntity.ok(createdEvent);
    }
    
    @PutMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long eventId, @Valid @RequestBody EventDto eventDto) {
        String username = getCurrentUsername();
        EventDto updatedEvent = eventService.updateEvent(eventId, eventDto, username);
        return ResponseEntity.ok(updatedEvent);
    }
    
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        String username = getCurrentUsername();
        eventService.deleteEvent(eventId, username);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{eventId}/deactivate")
    public ResponseEntity<EventDto> deactivateEvent(@PathVariable Long eventId) {
        String username = getCurrentUsername();
        EventDto deactivatedEvent = eventService.deactivateEvent(eventId, username);
        return ResponseEntity.ok(deactivatedEvent);
    }
    
    @PostMapping("/{eventId}/join")
    public ResponseEntity<EventDto> joinEvent(@PathVariable Long eventId) {
        String username = getCurrentUsername();
        EventDto event = eventService.joinEvent(eventId, username);
        return ResponseEntity.ok(event);
    }
    
    @PostMapping("/{eventId}/leave")
    public ResponseEntity<EventDto> leaveEvent(@PathVariable Long eventId) {
        String username = getCurrentUsername();
        EventDto event = eventService.leaveEvent(eventId, username);
        return ResponseEntity.ok(event);
    }
    
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
