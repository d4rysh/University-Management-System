package ua.foxminded.university_schedule_app.JPARepositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    
    List<Lesson> findByGroup_GroupIdAndDateBetween(Integer groupId, LocalDate startDate, LocalDate endDate);
    
    List<Lesson> findByTeacher_UserIdAndDateBetween(Integer teacherId, LocalDate startDate, LocalDate endDate);
    
    List<Lesson> findByCourse_CourseIdAndDateBetween(Integer courseId, LocalDate startDate, LocalDate endDate);
            
    //all lessons in one day
    List<Lesson> findByDate(LocalDate date);
    
    Optional<Lesson> findByGroup_GroupIdAndDateAndTimeSlot_SlotName(Integer groupId, LocalDate date, String slotName);
    
    Optional<Lesson> findByTeacher_UserIdAndDateAndTimeSlot_SlotName(Integer teacherId, LocalDate date, String slotName);

    boolean existsByGroup_GroupIdAndDateAndTimeSlot_SlotName(Integer groupId, LocalDate date, String slotName);
    
    boolean existsByTeacher_UserIdAndDateAndTimeSlot_SlotName(Integer teacherId, LocalDate date, String slotName);

    

}
