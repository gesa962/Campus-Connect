package com.campusconnect.dto;

import com.campusconnect.entity.Event;
import com.campusconnect.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class EventDto {
    
    private Long id;
    
    @NotBlank(message = "Event title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;
    
    @NotBlank(message = "Event description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;
    
    @NotBlank(message = "Event location is required")
    private String location;
    
    @NotNull(message = "Event date and time is required")
    private LocalDateTime eventDateTime;
    
    @NotNull(message = "Registration deadline is required")
    private LocalDateTime registrationDeadline;
    
    private int maxParticipants;
    
    private String category;
    
    private String organizer;
    
    private Long createdById;
    
    private String createdByUsername;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private boolean isActive;
    
    private boolean isRegistrationOpen;
    
    private boolean isEventFull;
    
    private int currentParticipants;
    
    private Set<UserDto> participants;
    
    // Constructors
    public EventDto() {}
    
    public EventDto(String title, String description, String location, LocalDateTime eventDateTime, 
                   LocalDateTime registrationDeadline, int maxParticipants, String category, String organizer) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.eventDateTime = eventDateTime;
        this.registrationDeadline = registrationDeadline;
        this.maxParticipants = maxParticipants;
        this.category = category;
        this.organizer = organizer;
    }
    
    // Static factory method to create DTO from entity
    public static EventDto fromEntity(Event event) {
        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        dto.setEventDateTime(event.getEventDateTime());
        dto.setRegistrationDeadline(event.getRegistrationDeadline());
        dto.setMaxParticipants(event.getMaxParticipants());
        dto.setCategory(event.getCategory());
        dto.setOrganizer(event.getOrganizer());
        dto.setCreatedAt(event.getCreatedAt());
        dto.setUpdatedAt(event.getUpdatedAt());
        dto.setActive(event.isActive());
        dto.setRegistrationOpen(event.isRegistrationOpen());
        dto.setEventFull(event.isEventFull());
        dto.setCurrentParticipants(event.getParticipants().size());
        
        if (event.getCreatedBy() != null) {
            dto.setCreatedById(event.getCreatedBy().getId());
            dto.setCreatedByUsername(event.getCreatedBy().getUsername());
        }
        
        if (event.getParticipants() != null) {
            dto.setParticipants(event.getParticipants().stream()
                    .map(UserDto::fromEntity)
                    .collect(Collectors.toSet()));
        }
        
        return dto;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }
    
    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }
    
    public LocalDateTime getRegistrationDeadline() {
        return registrationDeadline;
    }
    
    public void setRegistrationDeadline(LocalDateTime registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }
    
    public int getMaxParticipants() {
        return maxParticipants;
    }
    
    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getOrganizer() {
        return organizer;
    }
    
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
    
    public Long getCreatedById() {
        return createdById;
    }
    
    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }
    
    public String getCreatedByUsername() {
        return createdByUsername;
    }
    
    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public boolean isRegistrationOpen() {
        return isRegistrationOpen;
    }
    
    public void setRegistrationOpen(boolean registrationOpen) {
        isRegistrationOpen = registrationOpen;
    }
    
    public boolean isEventFull() {
        return isEventFull;
    }
    
    public void setEventFull(boolean eventFull) {
        isEventFull = eventFull;
    }
    
    public int getCurrentParticipants() {
        return currentParticipants;
    }
    
    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }
    
    public Set<UserDto> getParticipants() {
        return participants;
    }
    
    public void setParticipants(Set<UserDto> participants) {
        this.participants = participants;
    }
}
