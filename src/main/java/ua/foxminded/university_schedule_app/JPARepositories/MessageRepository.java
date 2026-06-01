package ua.foxminded.university_schedule_app.JPARepositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    
  //all messages with filter (newest first):
    Page<Message> findBySender_UserIdOrderByTimestampDesc(Integer senderId, Pageable pageable);
    
    Page<Message> findByReceiver_UserIdOrderByTimestampDesc(Integer receiverId, Pageable pageable);
    
    //list of messages that day:
    List<Message> findBySender_UserIdAndTimestampBetweenOrderByTimestampDesc (Integer senderId, LocalDateTime startTime, LocalDateTime endTime);
    
    List<Message> findByReceiver_UserIdAndTimestampBetweenOrderByTimestampDesc (Integer receiverId, LocalDateTime startTime, LocalDateTime endTime);
}