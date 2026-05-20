package ua.foxminded.university_schedule_app.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.university_schedule_app.Entity.Message;
import ua.foxminded.university_schedule_app.Entity.User;
import ua.foxminded.university_schedule_app.JPARepositories.MessageRepository;
import ua.foxminded.university_schedule_app.Service.MessageService;

@SpringBootTest(classes = {MessageService.class})
public class MessageServiceTests {

    @MockBean
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    private Message getMessageEntity() {
        User sender = new User();
        sender.setUserId(1);
        User receiver = new User();
        receiver.setUserId(2);

        Message message = new Message();
        message.setMessageId(10);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent("Hello!");
        message.setTimestamp(LocalDateTime.now());
        return message;
    }
    
    private static final String DOUBLE_SPACE = "  ";

    @Test
    void addMessage_shouldThrowException_whenContentIsEmpty() {
        Message message = getMessageEntity();
        message.setContent(DOUBLE_SPACE);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> messageService.addMessage(message));

        assertEquals("Message content cannot be empty.", e.getMessage());
    }

    @Test
    void addMessage_shouldThrowException_whenContentTooLong() {
        Message message = getMessageEntity();
        message.setContent("a".repeat(1001));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> messageService.addMessage(message));

        assertEquals("Message content exceeds 1000 characters.", e.getMessage());
    }

    @Test
    void addMessage_shouldSaveMessage_whenValid() {
        Message message = getMessageEntity();
        when(messageRepository.save(any(Message.class))).thenReturn(message);

        Message result = messageService.addMessage(message);

        assertEquals("Hello!", result.getContent());
        assertEquals(10, result.getMessageId());
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void deleteMessage_shouldThrowException_whenMessageNotExists() {
        when(messageRepository.existsById(99)).thenReturn(false);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> messageService.deleteMessage(99));

        assertEquals("Message with id 99 does not exist.", e.getMessage());
    }

    @Test
    void deleteMessage_shouldDelete_whenMessageExists() {
        when(messageRepository.existsById(10)).thenReturn(true);

        messageService.deleteMessage(10);

        verify(messageRepository).deleteById(10);
    }
}