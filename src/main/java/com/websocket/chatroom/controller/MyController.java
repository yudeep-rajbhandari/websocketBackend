package com.websocket.chatroom.controller;

import com.websocket.chatroom.model.AnonymousPrinciple;
import com.websocket.chatroom.model.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class MyController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SimpUserRegistry simpUserRegistry;




    @MessageMapping("/message")
    private void receiveMyMessage(@RequestBody MyMessage message){
        List<AnonymousPrinciple> a =  simpUserRegistry.getUsers().stream().map(j->(AnonymousPrinciple)j.getPrincipal()).filter(j->j.getState().equals(AnonymousPrinciple.State.Online)).collect(Collectors.toList());
        for (AnonymousPrinciple p:a){
            simpMessagingTemplate.convertAndSendToUser(p.getName(),"/topic/public",message);
        }
    }

}
