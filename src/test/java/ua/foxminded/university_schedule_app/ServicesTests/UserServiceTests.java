package ua.foxminded.university_schedule_app.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.university_schedule_app.Entity.User;
import ua.foxminded.university_schedule_app.JPARepositories.UserRepository;
import ua.foxminded.university_schedule_app.Service.UserService;

@SpringBootTest(classes = {UserService.class})
public class UserServiceTests {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User getUserEntity() {
        User user = new User();
        user.setUserId(1);
        user.setFirstName("Alice");
        user.setLastName("Green");
        user.setEmail("alice@example.com");
        return user;
    }

    @Test
    void addUser_shouldSaveUser_whenValid() {
        User user = getUserEntity();
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.addUser(user);

        assertEquals("Alice", result.getFirstName());
        assertEquals(1, result.getUserId());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteUser_shouldThrowException_whenUserNotExists() {
        when(userRepository.existsById(99)).thenReturn(false);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> userService.deleteUser(99));

        assertEquals("User with id 99 does not exist.", e.getMessage());
    }

    @Test
    void deleteUser_shouldDelete_whenUserExists() {
        when(userRepository.existsById(1)).thenReturn(true);

        userService.deleteUser(1);

        verify(userRepository).deleteById(1);
    }
}
