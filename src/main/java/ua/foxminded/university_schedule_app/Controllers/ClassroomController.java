package ua.foxminded.university_schedule_app.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.foxminded.university_schedule_app.Entity.Classroom;
import ua.foxminded.university_schedule_app.Service.ClassroomService;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {

 private final ClassroomService classroomService;
    
    @Autowired 
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }
    
    @PostMapping("/create-classroom")
    public String addClassroom(@RequestParam("roomNumber") int roomNumber) {
        Classroom classroom = new Classroom();
        classroom.setRoomNumber(roomNumber);
        classroomService.addClassroom(classroom);
        return "redirect:/classrooms";
    }
    
    @GetMapping
    public String getAllClassrooms(Model model) {
    List<Classroom> classrooms = classroomService.getAllClassrooms();
    model.addAttribute("classrooms", classrooms);
    return "classrooms";
    }
    
    @PostMapping("/delete-classroom/{id}")
    public String deleteClassroom(@PathVariable Integer id) {
        classroomService.deleteClassroom(id);
        return "redirect:/classrooms";
    }
    
    @GetMapping("/search/{roomNumber}")
    public String getClassroomByRoomNumber(@PathVariable int roomNumber, Model model) {
    Optional<Classroom> classroom = classroomService.getClassroomByRoomNumber(roomNumber);
    if (classroom.isPresent()) {
        model.addAttribute("classroom", classroom.get());
    } else {
        model.addAttribute("error", "Classroom not found");
    }
    return "classroom";
    }
    
}