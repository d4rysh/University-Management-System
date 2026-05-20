package ua.foxminded.university_schedule_app.JPARepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.TimeSlot;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    
    Optional<TimeSlot> findBySlotName (String slotName);
    
}
