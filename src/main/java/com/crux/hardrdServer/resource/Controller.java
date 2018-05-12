package com.crux.hardrdServer.resource;

import org.springframework.web.bind.annotation.RestController;

import com.crux.hardrdServer.model.GMap;
import com.crux.hardrdServer.model.Map;
import com.crux.hardrdServer.model.MapDao;
import com.crux.hardrdServer.model.MapInternal;
import com.crux.hardrdServer.model.PlayerDao;
import com.crux.hardrdServer.resource.entity.ErrorMessage;
import com.crux.hardrdServer.resource.entity.PlayerResource;
import com.crux.hardrdServer.resource.entity.Updates;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.hibernate.validator.constraints.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostConstruct
	public void init()
	{
		loadMap();
	}
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

}