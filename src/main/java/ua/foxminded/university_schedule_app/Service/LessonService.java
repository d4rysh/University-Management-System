package ua.foxminded.university_schedule_app.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import ua.foxminded.university_schedule_app.Entity.Classroom;
import ua.foxminded.university_schedule_app.Entity.Lesson;
import ua.foxminded.university_schedule_app.JPARepositories.LessonRepository;
import ua.foxminded.university_schedule_app.Util.PaginationValidator;

@Service
public class LessonService {
    private static final Logger logger = LoggerFactory.getLogger(LessonService.class);
    private final LessonRepository lessonRepository;
    
    @Autowired 
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }
    
    @Transactional
    public Lesson addLesson(Lesson lesson) {
        logger.info("Adding lesson for course {} with teacher {} in classroom {} at slot {} for group {}.", lesson.getCourse().getCourseId(), lesson.getTeacher().getUserId(), lesson.getClassroom().getClassroomId(), lesson.getTimeSlot().getSlotName(), lesson.getGroup().getGroupId());
        
        if (lessonRepository.existsByGroup_GroupIdAndDateAndTimeSlot_SlotName(
                lesson.getGroup().getGroupId(), lesson.getLessonDate(), lesson.getTimeSlot().getSlotName())) {
            throw new IllegalStateException("Group already has a lesson at this time.");
        }
        if (lessonRepository.existsByTeacher_UserIdAndDateAndTimeSlot_SlotName(
                lesson.getTeacher().getUserId(), lesson.getLessonDate(), lesson.getTimeSlot().getSlotName())) {
            throw new IllegalStateException("Teacher already has a lesson at this time.");
        }
        
        Lesson savedLesson = lessonRepository.save(lesson);
        logger.info("Lesson added successfully with id: {}", savedLesson.getLessonId());
        return savedLesson;
    }
    
    //1
    @Transactional(readOnly = true)
    public Page<Lesson> getAllLessons(int pageNumber, int pageSize) {
        PaginationValidator.validatePagination(pageNumber, pageSize);
        logger.info("Starting getAllLessons method.");
        Page<Lesson> lessons = lessonRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return lessons;
}
    
    @Transactional
    public void deleteLesson(Integer lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new EntityNotFoundException("Lesson not found with id: " + lessonId);
        }
        logger.info("Deleting lesson with id: {}", lessonId);
        lessonRepository.deleteById(lessonId);
    }
 
    @Transactional(readOnly = true)
    public List<Lesson> getLessonsByGroupIdAndDate(Integer groupId, LocalDate startDate, LocalDate endDate) {
        logger.info("Fetching lessons for group {} between {} and {}.", groupId, startDate, endDate);
        return lessonRepository.findByGroup_GroupIdAndDateBetween(groupId, startDate, endDate);
    }
    
    @Transactional(readOnly = true)
    public List<Lesson> getLessonsByTeacherIdAndDate(Integer teacherId, LocalDate startDate, LocalDate endDate) {
        logger.info("Fetching lessons for teacher {} between {} and {}.", teacherId, startDate, endDate);
        return lessonRepository.findByTeacher_UserIdAndDateBetween(teacherId, startDate, endDate);
    }
    
    @Transactional(readOnly = true)
    public List<Lesson> getLessonsByCourseIdAndDate(Integer courseId, LocalDate startDate, LocalDate endDate) {
        logger.info("Fetching lessons for course {} between {} and {}.", courseId, startDate, endDate);
        return lessonRepository.findByCourse_CourseIdAndDateBetween(courseId, startDate, endDate);
    }
    
  //all lessons in one day
    @Transactional(readOnly = true)
    public List<Lesson> getLessonsByDate(LocalDate date) {
        logger.info("Fetching lessons on {}.", date);
        return lessonRepository.findByDate(date);
    }
    
    @Transactional(readOnly = true)
    public Optional<Lesson> getLessonsByGroupIdAndDateAndTimeSlot(Integer groupId, LocalDate date, String slotName) {
        logger.info("Fetching lessons for group {} on date {} at slot {}.", groupId, date, slotName);
        return lessonRepository.findByGroup_GroupIdAndDateAndTimeSlot_SlotName(groupId, date, slotName);
    }
    
    @Transactional(readOnly = true)
    public Optional<Lesson> getLessonsByTeacherIdAndDateAndTimeSlot(Integer teacherId, LocalDate date, String slotName) {
        logger.info("Fetching lessons for teacher {} on date {} at slot {}.", teacherId, date, slotName);
        return lessonRepository.findByTeacher_UserIdAndDateAndTimeSlot_SlotName(teacherId, date, slotName);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByGroupIdAndDateAndTimeSlot(Integer groupId, LocalDate date, String slotName) {
        logger.info("Starting existsByGroupIdAndDateAndTimeSlot method for group {} on date {} at slot {}.", groupId, date, slotName);
        return lessonRepository.existsByGroup_GroupIdAndDateAndTimeSlot_SlotName(groupId, date, slotName);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByTeacherIdAndDateAndTimeSlot(Integer teacherId, LocalDate date, String slotName) {
        logger.info("Starting existsByTeacherIdAndDateAndTimeSlot method for teacher {} on date {} at slot {}.", teacherId, date, slotName);
        return lessonRepository.existsByTeacher_UserIdAndDateAndTimeSlot_SlotName(teacherId, date, slotName);
    }
}