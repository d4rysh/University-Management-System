package ua.foxminded.university_schedule_app.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import ua.foxminded.university_schedule_app.Entity.Classroom;
import ua.foxminded.university_schedule_app.JPARepositories.ClassroomRepository;

@Service
public class ClassroomService {
    private static final Logger logger = LoggerFactory.getLogger(ClassroomService.class);
    private final ClassroomRepository classroomRepository;
    
    @Autowired 
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }
    
    @Transactional
    public Classroom addClassroom(Classroom classroom) {
        logger.info("Starting addClassroom for classroom: {}.", classroom.getRoomNumber());
        if (classroomRepository.existsByRoomNumber(classroom.getRoomNumber())) {
            throw new IllegalArgumentException("Classroom with this room number already exists!");
        }
        if (classroom.getRoomNumber() <= 0) {
            throw new IllegalArgumentException("Room number must be positive.");
        }
        Classroom savedClassroom = classroomRepository.save(classroom);
        logger.info("Classroom added successfully with id: {}", savedClassroom.getClassroomId());
        return savedClassroom;
    }
    
    @Transactional(readOnly = true)
    public List<Classroom> getAllClassrooms() {
        logger.info("Starting getAllClassrooms method.");
        List<Classroom> classrooms = classroomRepository.findAll();
            logger.debug("Retrieved {} classrooms from database.", classrooms.size());
        return classrooms;
    }
    
    @Transactional
    public void deleteClassroom(Integer classroomId) {
        if (!classroomRepository.existsById(classroomId)) {
            throw new EntityNotFoundException("Classroom not found with id: " + classroomId);
        }
        logger.info("Deleting classroom with id: {}", classroomId);
        classroomRepository.deleteById(classroomId);
    }
    
    @Transactional(readOnly = true)
    public Optional<Classroom> getClassroomByRoomNumber(int roomNumber) {
        logger.info("Starting getClassroomByRoomNumber method for classroom {}.", roomNumber);
        return classroomRepository.findByRoomNumber(roomNumber);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByRoomNumber (int roomNumber) {
        logger.info("Starting existsByRoomNumber method for classroom {}.", roomNumber);
        return classroomRepository.existsByRoomNumber(roomNumber);
    }
}


