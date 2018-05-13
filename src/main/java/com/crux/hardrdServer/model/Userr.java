package com.crux.hardrdServer.model;

import com.crux.hardrdServer.model.Player;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
public class Userr {
    @Id
    private String name;
    private String password;
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Player> players;

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
