package ua.foxminded.university_schedule_app.RepositoriesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.User;
import ua.foxminded.university_schedule_app.JPARepositories.UserRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;

@SpringBootTest(classes = { UserRepository .class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = User.class)
@EnableJpaRepositories(basePackageClasses = UserRepository .class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests extends PostgresTestContainerConfig {

    @Autowired
    private UserRepository userRepository;
    
    @Test
    @Transactional
    @Sql("/sql/insert_users.sql")
    void findByEmail_shouldReturnUser_whenExists() {
        Optional<User> user = userRepository.findByEmail("bob@example.com");
        assertTrue(user.isPresent());
        assertEquals("Bob", user.get().getFirstName());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_users.sql")
    void findByEmail_shouldReturnEmpty_whenNotExists() {
        Optional<User> user = userRepository.findByEmail("notfound@example.com");
        assertTrue(user.isEmpty());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_users.sql")
    void existsByEmail_shouldReturnTrue_whenExists() {
        boolean exists = userRepository.existsByEmail("john@example.com");
        assertTrue(exists);
    }

    @Test
    @Transactional
    @Sql("/sql/insert_users.sql")
    void existsByEmail_shouldReturnFalse_whenNotExists() {
        boolean exists = userRepository.existsByEmail("ghost@example.com");
        assertFalse(exists);
    }

    @Test
    @Transactional
    @Sql("/sql/insert_users.sql")
    void findByFirstNameAndLastName_shouldReturnUsers_whenExists() {
        List<User> users = userRepository.findByFirstNameAndLastName("Alice", "Green");
        assertEquals(1, users.size());
        assertEquals("alice@example.com", users.get(0).getEmail());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_users.sql")
    void findByFirstNameAndLastName_shouldReturnEmpty_whenNotExists() {
        List<User> users = userRepository.findByFirstNameAndLastName("Ghost", "User");
        assertTrue(users.isEmpty());
    }
}
