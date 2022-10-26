package com.websocket.chatroom.model;

import java.security.Principal;
import java.util.Objects;

public class AnonymousPrinciple implements Principal {
    private String name;

    private String username;
    private State state = State.Online;
    public enum State{
        Online,DoNotDisturb
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String getName() {
        return name;
    }

    public State getState() {
        return state;
    }
    @Override
    public boolean equals(Object another) {
        if (!(another instanceof Principal))
            return false;

        Principal principal = (Principal) another;
        return principal.getName() == this.name;

    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
