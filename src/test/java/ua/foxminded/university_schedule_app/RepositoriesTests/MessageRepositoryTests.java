package ua.foxminded.university_schedule_app.RepositoriesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.Message;
import ua.foxminded.university_schedule_app.JPARepositories.MessageRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;

@SpringBootTest(classes = { MessageRepository .class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Message.class)
@EnableJpaRepositories(basePackageClasses = MessageRepository .class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MessageRepositoryTests extends PostgresTestContainerConfig {

    @Autowired
    private MessageRepository messageRepository;
    
    @Test
    @Transactional
    @Sql("/sql/insert_messages.sql")
    void findBySenderUserIdOrderByTimestampDesc_shouldReturnMessages_whenExists() {
        List<Message> messages = messageRepository.findBySender_UserIdOrderByTimestampDesc(2);
        assertEquals(1, messages.size());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_messages.sql")
    void findByReceiverUserIdOrderByTimestampDesc_shouldReturnMessages_whenExists() {
        List<Message> messages = messageRepository.findByReceiver_UserIdOrderByTimestampDesc(3);
        assertEquals(1, messages.size());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_messages.sql")
    void findBySenderUserIdAndTimestampBetweenOrderByTimestampDesc_shouldReturnMessagesInRange() {
        LocalDateTime start = LocalDateTime.of(2025, 5, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 5, 2, 23, 59);
        List<Message> messages = messageRepository
                .findBySender_UserIdAndTimestampBetweenOrderByTimestampDesc(2, start, end);
        assertEquals(1, messages.size());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_messages.sql")
    void findByReceiverUserIdAndTimestampBetweenOrderByTimestampDesc_shouldReturnMessagesInRange() {
        LocalDateTime start = LocalDateTime.of(2025, 5, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 5, 2, 23, 59);
        List<Message> messages = messageRepository
                .findByReceiver_UserIdAndTimestampBetweenOrderByTimestampDesc(3, start, end);
        assertEquals(1, messages.size());
    }
}
