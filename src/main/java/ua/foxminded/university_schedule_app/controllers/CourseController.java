package ua.foxminded.university_schedule_app.controllers;

import java.util.List;
import java.util.Optional;

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
import ua.foxminded.university_schedule_app.Entity.Teacher;
import ua.foxminded.university_schedule_app.JPARepositories.TeacherRepository;
import ua.foxminded.university_schedule_app.Service.CourseService;
import ua.foxminded.university_schedule_app.Service.TeacherService;

@Controller
@RequestMapping("/courses")
public class CourseController {
private final CourseService courseService;
private final TeacherService teacherService;
    
    @Autowired 
    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }
   
    
    @PostMapping
    public String addCourse(@RequestParam("courseName") String courseName, @RequestParam("courseDescription") String courseDescription, @RequestParam("userId") Integer userId, RedirectAttributes redirectAttributes) {
       
        if (courseName == null || courseName.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Course name is required.");
            return "redirect:/courses";
        }
        
        Optional<Teacher> teacher = teacherService.getTeacherById(userId);
        
        if(teacher.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Teacher with ID " + userId + " not found.");
            return "redirect:/courses";
        }
        
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCourseDescription(courseDescription);
        course.setTeacher(teacher.get());
        courseService.addCourse(course);
        
        redirectAttributes.addFlashAttribute("success", "Course \"" + courseName + "\" was created successfully.");
        return "redirect:/courses";
    }
    
    @GetMapping
    public String getAllCourses(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "15") int pageSize, Model model) {
    Page<Course> page = courseService.getAllCourses(pageNumber, pageSize);
    model.addAttribute("courses", page.getContent());
    model.addAttribute("currentPage", pageNumber);
    model.addAttribute("totalPages", page.getTotalPages());
    return "courses";
    }
    
    @PostMapping("/delete-course/{courseId}")
    public String deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteCourse(courseId);
        return "redirect:/courses";
    }
    
    @GetMapping("/search")
    public String searchByCourseName(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "15") int pageSize, @RequestParam("courseName") String courseName, Model model) {
    Page<Course> page = courseService.searchByCourseName(courseName, pageNumber, pageSize);
    model.addAttribute("courses", page.getContent());
    model.addAttribute("currentPage", pageNumber);
    model.addAttribute("totalPages", page.getTotalPages());
    model.addAttribute("courseName", courseName);
    return "courses";
    }
    
}