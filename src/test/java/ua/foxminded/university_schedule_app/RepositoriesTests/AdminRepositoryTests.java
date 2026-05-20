package ua.foxminded.university_schedule_app.RepositoriesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.Admin;
import ua.foxminded.university_schedule_app.JPARepositories.AdminRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;

@SpringBootTest(classes = { AdminRepository .class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Admin.class)
@EnableJpaRepositories(basePackageClasses = AdminRepository .class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminRepositoryTests extends PostgresTestContainerConfig {

    @Autowired
    private AdminRepository adminRepository;
    
    @Test
    @Transactional
    @Sql("/sql/insert_admins.sql")
    void existsByEmail_shouldReturnTrue_whenAdminExists() {
        boolean exists = adminRepository.existsByEmail("admin@example.com");
        assertTrue(exists);
    }

    @Test
    @Transactional
    @Sql("/sql/insert_admins.sql")
    void existsByEmail_shouldReturnFalse_whenAdminNotExists() {
        boolean exists = adminRepository.existsByEmail("notexists@example.com");
        assertFalse(exists);
    }

    @Test
    @Transactional
    @Sql("/sql/insert_admins.sql")
    void findByLastName_shouldReturnAdmins_whenExists() {
        List<Admin> admins = adminRepository.findByLastName("Brown");
        assertEquals(1, admins.size());
        assertEquals("Bob", admins.get(0).getFirstName());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_admins.sql")
    void findByLastName_shouldReturnEmpty_whenNotExists() {
        List<Admin> admins = adminRepository.findByLastName("NotExists");
        assertTrue(admins.isEmpty());
    }
}
