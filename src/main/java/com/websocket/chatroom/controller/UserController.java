package com.websocket.chatroom.controller;

import com.websocket.chatroom.model.AnonymousPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @GetMapping("/changeStatus")
    public AnonymousPrinciple changeStatus(@RequestParam String name){
       AnonymousPrinciple a =  simpUserRegistry.getUsers().stream().map(j->(AnonymousPrinciple)j.getPrincipal()).filter(j->j.getUsername().equals(name)).collect(Collectors.toList()).get(0);
        if(a.getState().equals(AnonymousPrinciple.State.Online)){
            a.setState(AnonymousPrinciple.State.DoNotDisturb);
        }
        else {
            a.setState(AnonymousPrinciple.State.Online);
        }
        return a;
    }
//


    @GetMapping("/getAllUser")
    public List<AnonymousPrinciple> getAllUser(){
      return  simpUserRegistry.getUsers().stream().map(j->(AnonymousPrinciple)j.getPrincipal()).collect(Collectors.toList());
    }
}
