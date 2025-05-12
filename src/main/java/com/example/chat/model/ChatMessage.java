package com.example.chat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "messages")
public class ChatMessage {
    @Id
    private String id;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public ChatMessage() {
    }

    public ChatMessage(String id, String user, String content, Instant timestamp) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }

    private String user;
    private String content;
    private Instant timestamp;

    // Constructors, getters, setters omitted for brevity
}
