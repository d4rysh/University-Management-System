package ua.foxminded.university_schedule_app.JPARepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    
    Optional<Course> findByCourseName(String courseName);
    
    List<Course> findByTeacher_UserId(Integer userId);
    
    boolean existsByCourseName(String courseName);
    
}
