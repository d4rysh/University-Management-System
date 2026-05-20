package ua.foxminded.university_schedule_app.ServicesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.persistence.EntityNotFoundException;
import ua.foxminded.university_schedule_app.Entity.Classroom;
import ua.foxminded.university_schedule_app.JPARepositories.ClassroomRepository;
import ua.foxminded.university_schedule_app.Service.ClassroomService;

@SpringBootTest(classes = {ClassroomService.class})
public class ClassroomServiceTests {
    
    @MockBean
    private ClassroomRepository classroomRepository;

    @Autowired
    private ClassroomService classroomService;

    private Classroom getClassroomEntity() {
        Classroom classroom = new Classroom();
        classroom.setClassroomId(1);
        classroom.setRoomNumber(101);
        return classroom;
    }

    @Test
    void addClassroom_shouldThrowException_whenRoomNumberAlreadyExists() {
        Classroom classroom = getClassroomEntity();
        when(classroomRepository.existsByRoomNumber(101)).thenReturn(true);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> classroomService.addClassroom(classroom));

        assertEquals("Classroom with this room number already exists!", e.getMessage());
    }

    @Test
    void addClassroom_shouldThrowException_whenRoomNumberIsNotPositive() {
        Classroom classroom = getClassroomEntity();
        classroom.setRoomNumber(0);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> classroomService.addClassroom(classroom));

        assertEquals("Room number must be positive.", e.getMessage());
    }

    @Test
    void addClassroom_shouldSaveClassroom_whenValid() {
        Classroom classroom = getClassroomEntity();
        when(classroomRepository.existsByRoomNumber(101)).thenReturn(false);
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);

        Classroom result = classroomService.addClassroom(classroom);

        assertEquals(101, result.getRoomNumber());
        verify(classroomRepository).save(any(Classroom.class));
    }

    @Test
    void deleteClassroom_shouldThrowException_whenClassroomNotExists() {
        when(classroomRepository.existsById(99)).thenReturn(false);

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> classroomService.deleteClassroom(99));

        assertEquals("Classroom not found with id: 99", e.getMessage());
    }

    @Test
    void deleteClassroom_shouldDelete_whenClassroomExists() {
        when(classroomRepository.existsById(1)).thenReturn(true);

        classroomService.deleteClassroom(1);

        verify(classroomRepository).deleteById(1);
    }
}
