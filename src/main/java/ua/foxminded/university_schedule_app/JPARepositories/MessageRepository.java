package ua.foxminded.university_schedule_app.JPARepositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    
  //all messages with filter (newest first):
    List<Message> findBySender_UserIdOrderByTimestampDesc(Integer senderId);
    
    List<Message> findByReceiver_UserIdOrderByTimestampDesc(Integer receiverId);
    
    //list of messages that day:
    List<Message> findBySender_UserIdAndTimestampBetweenOrderByTimestampDesc (Integer senderId, LocalDateTime startTime, LocalDateTime endTime);
    
    List<Message> findByReceiver_UserIdAndTimestampBetweenOrderByTimestampDesc (Integer receiverId, LocalDateTime startTime, LocalDateTime endTime);

}