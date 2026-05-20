package ua.foxminded.university_schedule_app.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;
    
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    public Message() {};
    
    public Message(User sender, User receiver, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }
    
    //getters
    public Integer getMessageId() {
        return messageId;
    }
    
    public User getSender() {
        return sender;
    }
    
    public User getReceiver() {
        return receiver;
    }
    
    public String getContent() {
        return content;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    //setters
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    
    public void setSender(User sender) {
        this.sender = sender;
    }
    
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
}
