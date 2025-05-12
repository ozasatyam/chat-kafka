package com.example.chat.service;

import com.example.chat.model.ChatMessage;
import com.example.chat.repository.ChatMessageRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Instant;
import java.util.List;

@Service
public class ChatService {
    private final ChatMessageRepository repo;
    private final KafkaTemplate<String, String> kafka;
    private final Sinks.Many<ChatMessage> sink = Sinks.many().multicast().onBackpressureBuffer();

    public ChatService(ChatMessageRepository repo, KafkaTemplate<String, String> kafka) {
        this.repo = repo;
        this.kafka = kafka;
    }

    public List<ChatMessage> getHistory() {
        return repo.findAllByOrderByTimestampAsc();
    }

    public ChatMessage sendMessage(String user, String content) {
        ChatMessage msg = new ChatMessage();
        msg.setUser(user);
        msg.setContent(content);
        msg.setTimestamp(Instant.now());
        repo.save(msg);
        kafka.send("chat-topic", msg.getId());
        return msg;
    }

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void listen(String messageId) {
        repo.findById(messageId)
                .ifPresent(sink::tryEmitNext);
    }

    public Flux<ChatMessage> subscribe() {
        return sink.asFlux();
    }
}
