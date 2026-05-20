package ua.foxminded.university_schedule_app.JPARepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer>{
    
    Optional<Classroom> findByRoomNumber(int roomNumber);
    
    boolean existsByRoomNumber (int roomNumber);
    
}
