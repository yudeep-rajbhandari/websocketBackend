package com.websocket.chatroom.config;

import com.websocket.chatroom.model.AnonymousPrinciple;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Configuration
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler, Map<String, Object> attributes) {
        Principal principal = request.getPrincipal();

        if (principal == null) {
            principal = new AnonymousPrinciple();

            String uniqueName = UUID.randomUUID().toString();

            ((AnonymousPrinciple) principal).setName(uniqueName);
        }

        return principal;

    }

}