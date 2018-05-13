package com.crux.hardrdServer.resource;

import com.crux.hardrdServer.exception.TehnicalException;
import com.crux.hardrdServer.model.*;
import com.crux.hardrdServer.resource.entity.UserrResource;
import org.springframework.web.bind.annotation.RestController;

import com.crux.hardrdServer.resource.entity.ErrorMessage;
import com.crux.hardrdServer.resource.entity.PlayerResource;
import com.crux.hardrdServer.resource.entity.Updates;

import java.util.ArrayList;
import java.util.Arrays;
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
	private static final float SIZE = 1800;
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 256*256*256;
	
	@Autowired
	private PlayerDao playerDao;
	@Autowired
	private MapDao mapDao;
	@Autowired
	private UserDao userDao;

	@PostConstruct
	public void init()
	{
		loadMap();
	}
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public void applyUpdates(@RequestBody Updates updates) {
		if(updates.getPlayerId() != null)
		{
			Player player = new Player();
			player.setPlayerId(updates.getPlayerId());
			player.setPlayerPosX(updates.getPlayerPosX());
			player.setPlayerPosY(updates.getPlayerPosY());
			player.setPlayerPosZ(updates.getPlayerPosZ());
			player.setPlayerRotX(updates.getPlayerRotX());
			player.setPlayerRotY(updates.getPlayerRotY());
			player.setPlayerRotZ(updates.getPlayerRotZ());
			player.setCurrentSpeed(updates.getCurrentSpeed());
			playerDao.updateOrSave(player);
		}
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public void createPlayer(@RequestBody Updates updates) throws TehnicalException{
		Userr userFromDatabase = userDao.getUser(updates.getUsername());
		if (userFromDatabase == null)
		{
			throw new TehnicalException("000", "User " +updates.getUsername()+" not found!");

		}
		List<Player> players = userFromDatabase.getPlayers();
		if(players == null )
		{
			userFromDatabase.setPlayers(new ArrayList<Player>());
		}

		Player player = new Player();
		player.setPlayerId(updates.getPlayerId());
		player.setPlayerPosX(updates.getPlayerPosX());
		player.setPlayerPosY(updates.getPlayerPosY());
		player.setPlayerPosZ(updates.getPlayerPosZ());
		player.setPlayerRotX(updates.getPlayerRotX());
		player.setPlayerRotY(updates.getPlayerRotY());
		player.setPlayerRotZ(updates.getPlayerRotZ());
		player.setCurrentSpeed(updates.getCurrentSpeed());
		player.setUserr(userFromDatabase);
		userFromDatabase.getPlayers().add(player);
		if(updates.getPlayerId() != null)
		{
			//update user
			userDao.createUser(userFromDatabase);
		}
	}
	
	@RequestMapping(path = "/get", method = RequestMethod.GET)
	public @ResponseBody List<PlayerResource> getPlayers() {
		return playerDao.getAllPlayers();
	}
	
	@RequestMapping(path = "/getPlayer/{id}", method = RequestMethod.GET)
	public @ResponseBody PlayerResource getPlayerByID(@PathVariable("id") String id) {
		return playerDao.getPlayer(id);
	}
	
	 @ExceptionHandler(Exception.class)
	 public @ResponseBody ErrorMessage onException(Exception ex)
	 {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode("000");
		if(ex.getMessage() == null)
		{
			errorMessage.setMessage("Internal Error");
		}
		else
		{
			errorMessage.setMessage(ex.getMessage());
		}
		LOGGER.info(ex.getMessage(), ex);
		return errorMessage;
	 }

	@RequestMapping(path = "/load", method = RequestMethod.GET)
	public String loadMap() {
		GMap gm = new GMap(256);
		gm.loadTiles("res/heightmap.png");
		for(MapInternal m : gm.getTiles())
		{
			mapDao.saveMap(m);
		}
//		loadTerrain("heightmap");
		return "Done!";
	}
	
	@RequestMapping(path = "/getTerrain/{colNum}/{rowNum}", method = RequestMethod.GET)
	public @ResponseBody Map getTerrainByID(@PathVariable("colNum") Integer colNum, @PathVariable("rowNum") Integer rowNum) {
		System.out.println(colNum);
		System.out.println(rowNum);
		Map map = mapDao.getMap(colNum, rowNum);
		System.out.println(map.getRow());
		System.out.println(map.getCol());
		return map;
	}
	
	Byte[] toObjects(byte[] bytesPrim) {
	    Byte[] bytes = new Byte[bytesPrim.length];
	    Arrays.setAll(bytes, n -> bytesPrim[n]);
	    return bytes;
	}

	@RequestMapping(path = "/user/login/{username}/{password}", method = RequestMethod.GET)
	public Boolean validateUsernamePassword(@PathVariable("username")String username, @PathVariable("password") String password)
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
	@RequestMapping(path = "/user/create", method = RequestMethod.POST)
	public void createUser(@RequestBody Userr user) throws TehnicalException {
		Userr userFromDatabase = userDao.getUser(user.getName());
		if(userFromDatabase != null)
		{
			throw new TehnicalException("000", "User already exists!");
		}
		userDao.createUser(user);
	}

	@RequestMapping(path = "/user/get/{username}", method = RequestMethod.GET)
	public UserrResource getUser(@PathVariable("username")String username)
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