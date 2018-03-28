package com.crux.hardrdServer.resource;

import org.springframework.web.bind.annotation.RestController;

import com.crux.hardrdServer.model.PlayerDao;
import com.crux.hardrdServer.resource.entity.PlayerResource;
import com.crux.hardrdServer.resource.entity.Updates;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class Controller {
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
	
	@Autowired
	private PlayerDao playerDao;
	
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public void applyUpdates(@RequestBody Updates updates) {
		if(updates.getPlayerId() != null)
		{
			playerDao.updateOrSave(updates);
		}
	}
	
	@RequestMapping(path = "/get", method = RequestMethod.GET)
	public @ResponseBody List<PlayerResource> getPlayers() {
		return playerDao.getAllPlayers();
	}
	
	

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String home() {
		return "Hi!";
	}

}