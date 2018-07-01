package com.crux.hardrdServer.service;

import com.crux.hardrdServer.exception.TehnicalException;
import com.crux.hardrdServer.model.Player;
import com.crux.hardrdServer.model.PlayerDao;
import com.crux.hardrdServer.model.Userr;
import com.crux.hardrdServer.resource.UserDao;
import com.crux.hardrdServer.resource.entity.PlayerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerOperationsService {

    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private UserDao userDao;

    //TODO:Add validation of input data
    public void updatePlayer(PlayerResource playerResource) {
        if (playerResource.getName() != null) {
            Player player = new Player();
            player.setPlayerId(playerResource.getName());
            player.setPlayerPosX(playerResource.getPosX());
            player.setPlayerPosY(playerResource.getPosY());
            player.setPlayerPosZ(playerResource.getPosZ());
            player.setPlayerRotX(playerResource.getRotX());
            player.setPlayerRotY(playerResource.getRotY());
            player.setPlayerRotZ(playerResource.getRotZ());
            player.setCurrentSpeed(playerResource.getCurrentSpeed());
            playerDao.updateOrSave(player);
        }
    }

    public void createPlayer(PlayerResource updates) throws TehnicalException {
        Userr userFromDatabase = userDao.getUser(updates.getUsername());
        if (userFromDatabase == null) {
            throw new TehnicalException("000", "User " + updates.getUsername() + " not found!");

        }
        List<Player> players = userFromDatabase.getPlayers();
        if (players == null) {
            userFromDatabase.setPlayers(new ArrayList<Player>());
        }

        Player player = new Player();
        player.setPlayerId(updates.getName());
        player.setPlayerPosX(updates.getPosX());
        player.setPlayerPosY(updates.getPosY());
        player.setPlayerPosZ(updates.getPosZ());
        player.setPlayerRotX(updates.getRotX());
        player.setPlayerRotY(updates.getRotY());
        player.setPlayerRotZ(updates.getRotZ());
        player.setCurrentSpeed(updates.getCurrentSpeed());
        player.setUserr(userFromDatabase);
        userFromDatabase.getPlayers().add(player);
        if (updates.getName() != null) {
            //update user
            userDao.createUser(userFromDatabase);
        }
    }

    public List<PlayerResource> getPlayers() {
        return playerDao.getAllPlayers();
    }

    public PlayerResource getPlayerByID(String id) {
        return playerDao.getPlayer(id);
    }
}
