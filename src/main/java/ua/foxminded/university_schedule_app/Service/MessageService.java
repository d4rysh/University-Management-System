package ua.foxminded.university_schedule_app.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Message> getAllMessages() {
        logger.info("Starting getAllMessages method.");
        List<Message> messages = messageRepository.findAll();
            logger.debug("Retrieved {} messages from database.", messages.size());
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
    public List<Message> getMessageBySender(Integer senderId) {
        logger.info("Starting getMessageBySender method for sender {}.", senderId);
        return messageRepository.findBySender_UserIdOrderByTimestampDesc(senderId);
    }
    
    @Transactional(readOnly = true)
    public List<Message> getMessageByReceiver(Integer receiverId) {
        logger.info("Starting getMessageByReceiver method for receiver {}.", receiverId);
        return messageRepository.findByReceiver_UserIdOrderByTimestampDesc(receiverId);
    }
    
  //list of messages that day:
    @Transactional(readOnly = true)
    public List<Message> getMessageBySenderAndTimestamp(Integer senderId, LocalDateTime startTime, LocalDateTime endTime) {
        logger.info("Fetching messages from sender {} between {} and {}.", senderId, startTime, endTime);
        return messageRepository.findBySender_UserIdAndTimestampBetweenOrderByTimestampDesc(senderId, startTime, endTime);
    }
    
    @Transactional(readOnly = true)
    public List<Message> getMessageByReceiverAndTimestamp(Integer receiverId, LocalDateTime startTime, LocalDateTime endTime) {
        logger.info("Fetching messages for receiver {} between {} and {}.", receiverId, startTime, endTime);
        return messageRepository.findByReceiver_UserIdAndTimestampBetweenOrderByTimestampDesc(receiverId, startTime, endTime);
    }
}