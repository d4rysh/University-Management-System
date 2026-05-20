package ua.foxminded.university_schedule_app.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.persistence.EntityNotFoundException;
import ua.foxminded.university_schedule_app.Entity.Group;
import ua.foxminded.university_schedule_app.JPARepositories.GroupRepository;
import ua.foxminded.university_schedule_app.Service.GroupService;

@SpringBootTest(classes = {GroupService.class})
public class GroupServiceTests {

    @MockBean
    private GroupRepository groupRepository;

    @Autowired
    private GroupService groupService;

    private Group getGroupEntity() {
        Group group = new Group();
        group.setGroupId(1);
        group.setGroupName("Group A");
        return group;
    }

    @Test
    void addGroup_shouldThrowException_whenGroupNameAlreadyExists() {
        Group group = getGroupEntity();
        when(groupRepository.existsByGroupName("Group A")).thenReturn(true);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> groupService.addGroup(group));

        assertEquals("Group with this name already exists!", e.getMessage());
    }

    @Test
    void addGroup_shouldSaveGroup_whenValid() {
        Group group = getGroupEntity();
        when(groupRepository.existsByGroupName("Group A")).thenReturn(false);
        when(groupRepository.save(any(Group.class))).thenReturn(group);

        Group result = groupService.addGroup(group);

        assertEquals("Group A", result.getGroupName());
        assertEquals(1, result.getGroupId());
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void deleteGroup_shouldThrowException_whenGroupNotExists() {
        when(groupRepository.existsById(99)).thenReturn(false);

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> groupService.deleteGroup(99));

        assertEquals("Group not found with id: 99", e.getMessage());
    }

    @Test
    void deleteGroup_shouldDelete_whenGroupExists() {
        when(groupRepository.existsById(1)).thenReturn(true);

        groupService.deleteGroup(1);

        verify(groupRepository).deleteById(1);
    }
}
