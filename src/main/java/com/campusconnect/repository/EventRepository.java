package com.campusconnect.repository;

import com.campusconnect.entity.Event;
import com.campusconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByIsActiveTrueOrderByEventDateTimeAsc();
    
    List<Event> findByCategoryAndIsActiveTrueOrderByEventDateTimeAsc(String category);
    
    List<Event> findByCreatedByOrderByCreatedAtDesc(User createdBy);
    
    @Query("SELECT e FROM Event e WHERE e.isActive = true AND e.eventDateTime > :now ORDER BY e.eventDateTime ASC")
    List<Event> findUpcomingEvents(@Param("now") LocalDateTime now);
    
    @Query("SELECT e FROM Event e WHERE e.isActive = true AND e.registrationDeadline > :now ORDER BY e.registrationDeadline ASC")
    List<Event> findEventsWithOpenRegistration(@Param("now") LocalDateTime now);
    
    @Query("SELECT e FROM Event e JOIN e.participants p WHERE p.id = :userId AND e.isActive = true ORDER BY e.eventDateTime ASC")
    List<Event> findEventsByParticipant(@Param("userId") Long userId);
    
    @Query("SELECT e FROM Event e WHERE e.isActive = true AND e.title LIKE %:keyword% OR e.description LIKE %:keyword% ORDER BY e.eventDateTime ASC")
    List<Event> searchEventsByKeyword(@Param("keyword") String keyword);
}
