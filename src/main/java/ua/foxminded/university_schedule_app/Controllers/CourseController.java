package ua.foxminded.university_schedule_app.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.foxminded.university_schedule_app.Entity.Course;
import ua.foxminded.university_schedule_app.Service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {
private final CourseService courseService;
    
    @Autowired 
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @PostMapping("/create-course")
    public String addCourse(@RequestParam("courseName") String courseName, @RequestParam("courseDescription") String courseDescription) {
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCourseDescription(courseDescription);
        courseService.addCourse(course);
        return "redirect:/courses";
    }
    
    @GetMapping
    public String getAllCourses(Model model) {
    List<Course> courses = courseService.getAllCourses();
    model.addAttribute("courses", courses);
    return "courses";
    }
    
    @PostMapping("/delete-course/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
    
    @GetMapping("/search/{courseName}")
    public String getCourseByCourseName(@PathVariable String courseName, Model model) {
    Optional<Course> course = courseService.getCourseByCourseName(courseName);
    if (course.isPresent()) {
        model.addAttribute("course", course.get());
    } else {
        model.addAttribute("error", "Course not found");
    }
    return "course";
    }
    
    @GetMapping("/search/teacher/{userId}")
    public String getCoursesByTeacherId(@PathVariable Integer userId, Model model) {
    List<Course> courses = courseService.getCoursesByTeacherId(userId);
    model.addAttribute("courses", courses);
    return "courses";
    }
    
}