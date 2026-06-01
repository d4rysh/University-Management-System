package ua.foxminded.university_schedule_app.JPARepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    
    Optional<Department> findByDepartmentName(String deparmentName);
    
    @Query("""
            SELECT d
            From Department d
            JOIN d.teachers t
            WHERE t.userId = :teacherId
            """)
    Optional<Department> findDepartmentByTeacherId(@Param("userId")Integer teacherId);

    boolean existsByDepartmentName (String deparmentName);
}
