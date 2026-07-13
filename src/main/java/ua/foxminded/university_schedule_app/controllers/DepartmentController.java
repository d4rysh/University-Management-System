package ua.foxminded.university_schedule_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.foxminded.university_schedule_app.Entity.Course;
import ua.foxminded.university_schedule_app.Entity.Department;
import ua.foxminded.university_schedule_app.Entity.Group;
import ua.foxminded.university_schedule_app.JPARepositories.TeacherRepository;
import ua.foxminded.university_schedule_app.Service.CourseService;
import ua.foxminded.university_schedule_app.Service.DepartmentService;
import ua.foxminded.university_schedule_app.Service.TeacherService;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
        
        @Autowired 
        public DepartmentController(DepartmentService departmentService) {
            this.departmentService = departmentService;
        }
        
        @GetMapping
        public String getAllCourses(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "15") int pageSize, Model model) {
            Page<Department> page = departmentService.getAllDepartments(pageNumber, pageSize);
            model.addAttribute("departments", page.getContent());
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("totalPages", page.getTotalPages());
            return "departments";
        }
        
        @PostMapping
        public String addDepartment(@RequestParam("departmentName") String departmentName, RedirectAttributes redirectAttributes) {
           
            if (departmentName == null || departmentName.isBlank()) {
                redirectAttributes.addFlashAttribute("error", "Department name is required.");
                return "redirect:/departments";
            }
            
            Department department = new Department();
            department.setDepartmentName(departmentName);
            departmentService.addDepartment(department);
            
            redirectAttributes.addFlashAttribute("success", "Department \"" + departmentName + "\" was created successfully.");
            return "redirect:/departments";
        }
        
        @PostMapping("/delete-department/{departmentId}")
        public String deleteDepartment(@PathVariable Integer departmentId) {
            departmentService.deleteDepartment(departmentId);
            return "redirect:/departments";
        }
        
        @GetMapping("/search")
        public String searchByDepartmentName(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "15") int pageSize, @RequestParam("departmentName") String departmentName, Model model) {
        Page<Department> page = departmentService.searchByDepartmentName(departmentName, pageNumber, pageSize);
        model.addAttribute("departments", page.getContent());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("departmentName", departmentName);
        return "departments";
        }
        
        
        
}
