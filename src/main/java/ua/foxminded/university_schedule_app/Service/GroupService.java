package ua.foxminded.university_schedule_app.Service;

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
import ua.foxminded.university_schedule_app.Entity.Course;
import ua.foxminded.university_schedule_app.Entity.Group;
import ua.foxminded.university_schedule_app.JPARepositories.GroupRepository;
import ua.foxminded.university_schedule_app.Util.PaginationValidator;

@Service
public class GroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);
    private final GroupRepository groupRepository;
    
    @Autowired 
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
    
    @Transactional
    public Group addGroup(Group group) {
        if (groupRepository.existsByGroupName(group.getGroupName())) {
            throw new IllegalArgumentException("Group with this name already exists!");
        }
        logger.info("Starting addGroup for group: {}.", group.getGroupName());
        Group savedGroup = groupRepository.save(group);
        logger.info("Group added successfully with id: {}", savedGroup.getGroupId());
        return savedGroup;
    }
    
    @Transactional(readOnly = true)
    public Page<Group> getAllGroups(int pageNumber, int pageSize) {
        PaginationValidator.validatePagination(pageNumber, pageSize);
        logger.info("Starting getAllGroups method.");
        Page<Group> groups = groupRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return groups;
}
    
    @Transactional
    public void deleteGroup(Integer groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new EntityNotFoundException("Group not found with id: " + groupId);
        }
        logger.info("Deleting group with id: {}", groupId);
        groupRepository.deleteById(groupId);
    }
    
    
    @Transactional(readOnly = true)
    public Optional<Group> getGroupById(Integer groupId) {
        logger.info("Starting getGroupById method for group {}.", groupId);
        return groupRepository.findById(groupId);
    }
    
    @Transactional(readOnly = true)
    public Optional<Group> getGroupByGroupName(String groupName) {
        logger.info("Starting getGroupByGroupName method for group {}.", groupName);
        return groupRepository.findByGroupName(groupName);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByGroupName(String groupName) {
        logger.info("Starting existsByGroupName method for group {}.", groupName);
        return groupRepository.existsByGroupName(groupName);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByGroupId(Integer groupId) {
        logger.info("Starting existsByGroupId method for group {}.", groupId);
        return groupRepository.existsByGroupId(groupId);
    }
    
    @Transactional(readOnly = true)
    public Optional<Group> getGroupByStudent(Integer studentId) {
        logger.info("Starting getGroupByStudent method for student {}.", studentId);
        return groupRepository.findByStudents_UserId(studentId);
    }
    
    @Transactional(readOnly = true)
    public List<Group> getGroupsByTeacher(Integer teacherId) {
        logger.info("Starting getGroupsByTeacher method for teacher {}.", teacherId);
        return groupRepository.findGroupsByUserId(teacherId);
    }
    
    @Transactional(readOnly = true)
    public Page<Group> searchByGroupName(String groupName, int pageNumber, int pageSize) {
        PaginationValidator.validatePagination(pageNumber, pageSize);
        logger.info("Starting searchByGroupName method for: {}.", groupName);
        Page<Group> groups = groupRepository.findByGroupNameContainingIgnoreCase(groupName, PageRequest.of(pageNumber, pageSize));
        return groups;
    }
 
}
