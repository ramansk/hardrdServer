package com.crux.hardrdServer.resource;

import com.crux.hardrdServer.exception.TehnicalException;
import com.crux.hardrdServer.model.*;
import com.crux.hardrdServer.resource.entity.UserrResource;
import com.crux.hardrdServer.service.PlayerOperationsService;
import com.crux.hardrdServer.service.UserOperationsService;
import org.springframework.web.bind.annotation.RestController;

import com.crux.hardrdServer.resource.entity.ErrorMessage;
import com.crux.hardrdServer.resource.entity.PlayerResource;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private MapDao mapDao;

    @Autowired
    private PlayerOperationsService pos;
    @Autowired
    private UserOperationsService uos;

    @PostConstruct
    public void init() {
        loadMap();
    }

    @RequestMapping(path = "/player/update", method = RequestMethod.POST)
    public void updatePlayer(@RequestBody PlayerResource playerResource) {
        pos.updatePlayer(playerResource);
    }

    @RequestMapping(path = "/player/create", method = RequestMethod.POST)
    public void createPlayer(@RequestBody PlayerResource playerResource) throws TehnicalException {
        pos.createPlayer(playerResource);
    }

    @RequestMapping(path = "/player/get", method = RequestMethod.GET)
    public @ResponseBody
    List<PlayerResource> getPlayers() {
        return pos.getPlayers();
    }

    @RequestMapping(path = "/player/get/{id}", method = RequestMethod.GET)
    public @ResponseBody
    PlayerResource getPlayerByID(@PathVariable("id") String id) {
        return pos.getPlayerByID(id);
    }

    @RequestMapping(path = "/user/login/{username}/{password}", method = RequestMethod.GET)
    public Boolean validateUsernamePassword(@PathVariable("username") String username, @PathVariable("password") String password) {
        return uos.validateUsernamePassword(username, password);
    }

    @RequestMapping(path = "/user/create", method = RequestMethod.POST)
    public void createUser(@RequestBody Userr user) throws TehnicalException {
        uos.createUser(user);
    }

    @RequestMapping(path = "/user/get/{username}", method = RequestMethod.GET)
    public UserrResource getUser(@PathVariable("username") String username) {
        return uos.getUser(username);
    }


    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ErrorMessage onException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode("000");
        if (ex.getMessage() == null) {
            errorMessage.setMessage("Internal Error");
        } else {
            errorMessage.setMessage(ex.getMessage());
        }
        LOGGER.info(ex.getMessage(), ex);
        return errorMessage;
    }

    @RequestMapping(path = "/load", method = RequestMethod.GET)
    public String loadMap() {
        GMap gm = new GMap(256);
        gm.loadTiles("res/heightmap.png");
        for (MapInternal m : gm.getTiles()) {
            mapDao.saveMap(m);
        }
//		loadTerrain("heightmap");
        return "Done!";
    }

    @RequestMapping(path = "/getTerrain/{colNum}/{rowNum}", method = RequestMethod.GET)
    public @ResponseBody
    Map getTerrainByID(@PathVariable("colNum") Integer colNum, @PathVariable("rowNum") Integer rowNum) {
        System.out.println(colNum);
        System.out.println(rowNum);
        Map map = mapDao.getMap(colNum, rowNum);
        System.out.println(map.getRow());
        System.out.println(map.getCol());
        return map;
    }
}