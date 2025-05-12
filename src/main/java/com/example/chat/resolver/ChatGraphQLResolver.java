package com.example.chat.resolver;

import com.example.chat.model.ChatMessage;
import com.example.chat.service.ChatService;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
public class ChatGraphQLResolver {
    private final ChatService service;

    public ChatGraphQLResolver(ChatService service) {
        this.service = service;
    }

    @QueryMapping
    public List<ChatMessage> history() {
        return service.getHistory();
    }

    @MutationMapping
    public ChatMessage sendMessage(@Argument String user, @Argument String content) {
        return service.sendMessage(user, content);
    }

    @SubscriptionMapping
    public Flux<ChatMessage> messageSent() {
        return service.subscribe();
    }
}
