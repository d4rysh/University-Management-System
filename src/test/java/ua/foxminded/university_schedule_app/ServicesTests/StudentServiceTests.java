package ua.foxminded.university_schedule_app.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.university_schedule_app.JPARepositories.GroupRepository;
import ua.foxminded.university_schedule_app.JPARepositories.StudentRepository;
import ua.foxminded.university_schedule_app.Service.StudentService;

@SpringBootTest(classes = {StudentService.class})
public class StudentServiceTests {

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private GroupRepository groupRepository;

    @Autowired
    private StudentService studentService;

    @Test
    void countStudentsInGroup_shouldThrowException_whenGroupNotExists() {
        when(groupRepository.existsByGroupId(99)).thenReturn(false);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> studentService.countStudentsInGroup(99));

        assertEquals("Group with id 99 does not exist.", e.getMessage());
    }

    @Test
    void countStudentsInGroup_shouldReturnCount_whenGroupExists() {
        when(groupRepository.existsByGroupId(1)).thenReturn(true);
        when(studentRepository.countStudentsInGroup(1)).thenReturn(5);

        int result = studentService.countStudentsInGroup(1);

        assertEquals(5, result);
        verify(studentRepository).countStudentsInGroup(1);
    }
}
