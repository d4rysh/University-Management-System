package ua.foxminded.university_schedule_app.JPARepositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Department;
import ua.foxminded.university_schedule_app.Entity.Group;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    
    Optional<Department> findByDepartmentName(String deparmentName);
    
    @Query("""
            SELECT d
            From Department d
            JOIN d.teachers t
            WHERE t.userId = :teacherId
            """)
    Optional<Department> findDepartmentByTeacherId(@Param("teacherId")Integer teacherId);

    Page<Department> findByDepartmentNameContainingIgnoreCase(String deparmentName, Pageable pageable);
    
    boolean existsByDepartmentName (String deparmentName);
}
