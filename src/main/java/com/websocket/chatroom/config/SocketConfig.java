package com.websocket.chatroom.config;

import com.websocket.chatroom.model.AnonymousPrinciple;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSocketMessageBroker
public class SocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/myws").setAllowedOrigins("http://localhost:3000").setHandshakeHandler(new DefaultHandshakeHandler(){

            @Override
            protected Principal determineUser(ServerHttpRequest request,
                                              WebSocketHandler wsHandler, Map<String, Object> attributes) {
                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;

                HttpSession session = servletRequest.getServletRequest().getSession();
                Principal principal = request.getPrincipal();

                if (principal == null) {
                    principal = new AnonymousPrinciple();

                    String uniqueName = session.getId();

                    ((AnonymousPrinciple) principal).setName(uniqueName);
                }

                return principal;

            }
        }).withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");

    }
    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        GenericMessage message = (GenericMessage) event.getMessage();
        String simpDestination = (String) message.getHeaders().get("simpDestination");
        AnonymousPrinciple anonymousPrinciple = (AnonymousPrinciple) message.getHeaders().get("simpUser");
        anonymousPrinciple.setUsername((String) ((List)((Map)message.getHeaders().get("nativeHeaders")).get("name")).get(0));
//        if (simpDestination.startsWith("/topic/group/1")) {
//            // do stuff
//        }
    }
}
