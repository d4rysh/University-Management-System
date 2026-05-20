package ua.foxminded.university_schedule_app.RepositoriesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


import java.util.List;

import ua.foxminded.university_schedule_app.Entity.Group;
import ua.foxminded.university_schedule_app.JPARepositories.GroupRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;

@SpringBootTest(classes = { GroupRepository .class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Group.class)
@EnableJpaRepositories(basePackageClasses = GroupRepository .class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GroupRepositoryTests extends PostgresTestContainerConfig {

    @Autowired
    private GroupRepository groupRepository;
    
    @Test
    @Transactional
    @Sql("/sql/insert_group.sql")
    void findByGroupId_shouldReturnGroup_whenExists() {
        Optional<Group> group = groupRepository.findByGroupId(1);
        assertTrue(group.isPresent());
        assertEquals("AA-95", group.get().getGroupName());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_group.sql")
    void findByGroupName_shouldReturnGroup_whenExists() {
        Optional<Group> group = groupRepository.findByGroupName("AA-95");
        assertTrue(group.isPresent());
        assertEquals(1, group.get().getGroupId());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_group.sql")
    void existsByGroupName_shouldReturnTrue_whenGroupExists() {
        boolean exists = groupRepository.existsByGroupName("AA-95");
        assertTrue(exists);
    }

    @Test
    @Transactional
    @Sql("/sql/insert_group.sql")
    void existsByGroupId_shouldReturnTrue_whenGroupExists() {
        boolean exists = groupRepository.existsByGroupId(1);
        assertTrue(exists);
    }

    @Test
    @Transactional
    @Sql("/sql/insert_group_with_student.sql")
    void findByStudentsUserId_shouldReturnGroup_whenStudentAssignedToGroup() {
        Optional<Group> group = groupRepository.findByStudents_UserId(2);
        assertTrue(group.isPresent());
        assertEquals("AA-95", group.get().getGroupName());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_group_with_teacher.sql")
    void findGroupsByUserId_shouldReturnGroups_whenTeacherHasLessons() {
        List<Group> groups = groupRepository.findGroupsByUserId(3);
        assertEquals(1, groups.size());
        assertEquals("AA-95", groups.get(0).getGroupName());
    }
}
