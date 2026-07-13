package ua.foxminded.university_schedule_app.JPARepositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Classroom;
import ua.foxminded.university_schedule_app.Entity.Group;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer>{
    
    Optional<Classroom> findByRoomNumber(int roomNumber);
    
    Page<Classroom> findByRoomNumber(int roomNumber, Pageable pageable);
    
    boolean existsByRoomNumber (int roomNumber);
    
}
