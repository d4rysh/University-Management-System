package ua.foxminded.university_schedule_app.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.Lesson;
import ua.foxminded.university_schedule_app.Entity.Message;
import ua.foxminded.university_schedule_app.JPARepositories.MessageRepository;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private final MessageRepository messageRepository;
    
    @Autowired 
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    
    @Transactional
    public Message addMessage(Message message) {
     
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be empty.");
        }
        
        if (message.getContent().length() > 1000) {
            throw new IllegalArgumentException("Message content exceeds 1000 characters.");
        }
        
        logger.info("Adding message from sender {} to receiver {} at {}.", message.getSender().getUserId(),
                message.getReceiver().getUserId(), message.getTimestamp());
        
        Message savedMessage = messageRepository.save(message);
        logger.info("Message added successfully with id: {}", savedMessage.getMessageId());

        return savedMessage;
    }
    
    @Transactional(readOnly = true)
    public Page<Message> getAllMessages(int pageNumber, int pageSize) {
        if (pageNumber<0) {
            throw new IllegalArgumentException("pageNumber must be 0 and over");
        }
        if (pageSize>15 || pageSize<=0) {
            throw new IllegalArgumentException("pageSize must be from 1 to 15");
        }
        logger.info("Starting getAllMessages method.");
        Page<Message> messages = messageRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return messages;
    }
    
    @Transactional
    public void deleteMessage(Integer messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new IllegalArgumentException("Message with id " + messageId + " does not exist.");
        }
        
        logger.info("Deleting message with id: {}", messageId);
        messageRepository.deleteById(messageId);
    }
    
    
    @Transactional(readOnly = true)
    public Optional<Message> getMessageByMessageId(Integer messageId) {
        logger.info("Starting getMessageByMessageId method for message {}.", messageId);
        return messageRepository.findById(messageId);
    }
    
  //all messages with filter (newest first):
    @Transactional(readOnly = true)
    public Page<Message> getMessageBySender(Integer senderId, int pageNumber, int pageSize) {
        if (pageNumber<0) {
            throw new IllegalArgumentException("pageNumber must be 0 and over");
        }
        if (pageSize>15 || pageSize<=0) {
            throw new IllegalArgumentException("pageSize must be from 1 to 15");
        }
        logger.info("Starting getMessageBySender method for sender {}.", senderId);
        Page<Message> messages = messageRepository.findBySender_UserIdOrderByTimestampDesc(senderId, PageRequest.of(pageNumber, pageSize));
        return messages;
    }
    
    @Transactional(readOnly = true)
    public Page<Message> getMessageByReceiver(Integer receiverId, int pageNumber, int pageSize) {
        if (pageNumber<0) {
            throw new IllegalArgumentException("pageNumber must be 0 and over");
        }
        if (pageSize>15 || pageSize<=0) {
            throw new IllegalArgumentException("pageSize must be from 1 to 15");
        }
        logger.info("Starting getMessageByReceiver method for receiver {}.", receiverId);
        Page<Message> messages = messageRepository.findByReceiver_UserIdOrderByTimestampDesc(receiverId, PageRequest.of(pageNumber, pageSize));
        return messages;
    }
    
  //list of messages that day:
    @Transactional(readOnly = true)
    public List<Message> getMessageBySenderAndDate(Integer senderId, LocalDate date) {
        LocalDateTime startTime = date.atStartOfDay();
        LocalDateTime endTime = date.atTime(LocalTime.MAX);
        logger.info("Fetching messages from sender {} between {} and {}.", senderId, startTime, endTime);
        return messageRepository.findBySender_UserIdAndTimestampBetweenOrderByTimestampDesc(senderId, startTime, endTime);
    }
    
    @Transactional(readOnly = true)
    public List<Message> getMessageByReceiverAndDate(Integer receiverId, LocalDate date) {
        LocalDateTime startTime = date.atStartOfDay();
        LocalDateTime endTime = date.atTime(LocalTime.MAX);
        logger.info("Fetching messages for receiver {} between {} and {}.", receiverId, startTime, endTime);
        return messageRepository.findByReceiver_UserIdAndTimestampBetweenOrderByTimestampDesc(receiverId, startTime, endTime);
    }
}