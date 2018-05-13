package com.crux.hardrdServer.resource.entity;

import com.crux.hardrdServer.model.Player;

import javax.persistence.*;
import java.util.List;

public class UserrResource {
    private String name;
    private String password;
    private List<PlayerResource> players;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PlayerResource> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerResource> players) {
        this.players = players;
    }
}
