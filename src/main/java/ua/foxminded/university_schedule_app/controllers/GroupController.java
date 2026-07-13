package ua.foxminded.university_schedule_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.foxminded.university_schedule_app.Entity.Course;
import ua.foxminded.university_schedule_app.Entity.Group;
import ua.foxminded.university_schedule_app.Entity.Student;
import ua.foxminded.university_schedule_app.Entity.Teacher;
import ua.foxminded.university_schedule_app.JPARepositories.TeacherRepository;
import ua.foxminded.university_schedule_app.Service.CourseService;
import ua.foxminded.university_schedule_app.Service.GroupService;
import ua.foxminded.university_schedule_app.Service.StudentService;
import ua.foxminded.university_schedule_app.Service.TeacherService;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final StudentService studentService;
        
        @Autowired 
        public GroupController(GroupService groupService, StudentService studentService) {
            this.groupService = groupService;
            this.studentService = studentService;
        }
        
        @PostMapping
        public String addGroup(@RequestParam("groupName") String groupName, RedirectAttributes redirectAttributes) {
           
            if (groupName == null || groupName.isBlank()) {
                redirectAttributes.addFlashAttribute("error", "Group name is required.");
                return "redirect:/groups";
            }
            
            Group group = new Group();
            group.setGroupName(groupName);
            groupService.addGroup(group);
            
            redirectAttributes.addFlashAttribute("success", "Group \"" + groupName + "\" was created successfully.");
            return "redirect:/groups";
        }
        
        @PostMapping("{groupId}/remove-student/{userId}")
        public String removeStudentFromGroup(@PathVariable Integer groupId, @PathVariable Integer userId) {
            studentService.removeStudentFromGroup(userId);
            return "redirect:/groups";
        }
        
        @GetMapping
        public String getAllGroups(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "15") int pageSize, Model model) {
        Page<Group> page = groupService.getAllGroups(pageNumber, pageSize);
        
        model.addAttribute("groups", page.getContent());
        model.addAttribute("availableStudents", studentService.getStudentsWithoutGroup());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        return "groups";
        }
        
        @PostMapping("/delete-group/{groupId}")
        public String deleteGroup(@PathVariable Integer groupId) {
            groupService.deleteGroup(groupId);
            return "redirect:/groups";
        }
        
        @GetMapping("/search")
        public String searchByGroupName(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "15") int pageSize, @RequestParam("groupName") String groupName, Model model) {
        Page<Group> page = groupService.searchByGroupName(groupName, pageNumber, pageSize);
        model.addAttribute("groups", page.getContent());
        model.addAttribute("availableStudents", studentService.getStudentsWithoutGroup());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("groupName", groupName);
        return "groups";
        }
        
        @PostMapping("/{groupId}/add-student/{userId}")
        public String addStudentToGroup(@PathVariable("groupId") Integer groupId, @PathVariable("userId") Integer userId) {
            studentService.addStudentToGroup(userId, groupId);
            return "redirect:/groups";
        }
        
        @GetMapping("{groupId}/search-available-students")
        public String searchStudentsByLastName(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "15") int pageSize, @RequestParam("lastName") String lastName, @PathVariable("groupId") Integer groupId, Model model) {
            Page<Group> page = groupService.getAllGroups(pageNumber, pageSize);
            List<Student> students = studentService.searchStudentsWithoutGroupByLastName(lastName);
            
            model.addAttribute("groups", page.getContent());
            model.addAttribute("availableStudents", students);
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("openGroupId", groupId);
            model.addAttribute("lastNameSearch", lastName);
        return "groups";
        }
}