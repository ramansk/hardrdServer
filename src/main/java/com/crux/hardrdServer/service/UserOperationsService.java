package com.crux.hardrdServer.service;

import com.crux.hardrdServer.exception.TehnicalException;
import com.crux.hardrdServer.model.Player;
import com.crux.hardrdServer.model.Userr;
import com.crux.hardrdServer.resource.UserDao;
import com.crux.hardrdServer.resource.entity.PlayerResource;
import com.crux.hardrdServer.resource.entity.UserrResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserOperationsService {
    @Autowired
    private UserDao userDao;

    public Boolean validateUsernamePassword(String username, String password)
    {
        //TODO:Refactor login logic ( return passrowd token for example )
        Boolean result = false;
        Userr user = userDao.getUser(username);
        if (user != null && user.getName() != null && user.getPassword() != null)
        {
            result = user.getPassword().equals(password);
        }
        return result;
    }

    public void createUser(Userr user) throws TehnicalException {
        Userr userFromDatabase = userDao.getUser(user.getName());
        if(userFromDatabase != null)
        {
            throw new TehnicalException("000", "User already exists!");
        }
        userDao.createUser(user);
    }

    public UserrResource getUser(String username)
    {
        Userr user = userDao.getUser(username);
        UserrResource ur = new UserrResource();
        ur.setName(user.getName());
        ur.setPassword(user.getPassword());
        List<PlayerResource> prl = new ArrayList<>();
        for(Player player :user.getPlayers())
        {
            PlayerResource pr = new PlayerResource();
            pr.setName(player.getPlayerId());
            pr.setPosX(player.getPlayerPosX());
            pr.setPosY(player.getPlayerPosY());
            pr.setPosZ(player.getPlayerPosZ());
            pr.setRotX(player.getPlayerRotX());
            pr.setRotY(player.getPlayerRotY());
            pr.setRotZ(player.getPlayerRotZ());
            pr.setCurrentSpeed(player.getCurrentSpeed());
            prl.add(pr);
        }
        ur.setPlayers(prl);
        return ur;
    }
}
