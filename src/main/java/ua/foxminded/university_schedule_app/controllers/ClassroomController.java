package ua.foxminded.university_schedule_app.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.foxminded.university_schedule_app.Entity.Classroom;
import ua.foxminded.university_schedule_app.Entity.Course;
import ua.foxminded.university_schedule_app.Entity.Teacher;
import ua.foxminded.university_schedule_app.Service.ClassroomService;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {

 private final ClassroomService classroomService;
    
    @Autowired 
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }
    
    @PostMapping
    public String addClassroom(@RequestParam("roomNumber") Integer roomNumber, RedirectAttributes redirectAttributes) {
       
        if (roomNumber<=0) {
            redirectAttributes.addFlashAttribute("error", "Room number is required and must be greater than 0.");
            return "redirect:/classrooms";
        }
       
        Classroom classroom = new Classroom();
        classroom.setRoomNumber(roomNumber);
        classroomService.addClassroom(classroom);
        
        redirectAttributes.addFlashAttribute("success", "Classroom \"" + roomNumber + "\" was created successfully.");
        return "redirect:/classrooms";
    }
    
    @GetMapping
    public String getAllClassrooms(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "15") int pageSize, Model model) {
    Page<Classroom> page = classroomService.getAllClassrooms(pageNumber, pageSize);
    model.addAttribute("classrooms", page.getContent());
    model.addAttribute("currentPage", pageNumber);
    model.addAttribute("totalPages", page.getTotalPages());
    return "classrooms";
    }
    
    @PostMapping("/delete-classroom/{classroomId}")
    public String deleteClassroom(@PathVariable Integer classroomId) {
        classroomService.deleteClassroom(classroomId);
        return "redirect:/classrooms";
    }
    
    @GetMapping("/search")
    public String searchByRoomNumber(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "15") int pageSize, @RequestParam int roomNumber, Model model) {
    Page<Classroom> page = classroomService.searchByRoomNumber(roomNumber, pageNumber, pageSize);
    model.addAttribute("classrooms", page.getContent());
    model.addAttribute("currentPage", pageNumber);
    model.addAttribute("totalPages", page.getTotalPages());
    model.addAttribute("search", roomNumber);
    return "classrooms";
    }
    
}