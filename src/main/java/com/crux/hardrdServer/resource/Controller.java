package com.crux.hardrdServer.resource;

import org.springframework.web.bind.annotation.RestController;

import com.crux.hardrdServer.model.GMap;
import com.crux.hardrdServer.model.Map;
import com.crux.hardrdServer.model.MapDao;
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

import javax.imageio.ImageIO;

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
	public String home() {
		GMap gm = new GMap(256);
		gm.loadTiles("res/heightmap.png");
		for(Map m : gm.getTiles())
		{
			mapDao.saveMap(m);
		}
//		loadTerrain("heightmap");
		return "Done!";
	}
	
	@RequestMapping(path = "/getTerrain/{id}", method = RequestMethod.GET)
	public @ResponseBody Map getTerrainByID(@PathVariable("id") String id) {
		return mapDao.getMap(1);
	}
	
	private void loadTerrain(String heightMap){
		float [][] hights = null;
		BufferedImage image = null;
		
		
		
		try {
			image = ImageIO.read(new File("res/" + heightMap + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int VERTEX_COUNT = image.getHeight();
		hights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				float height = getHeight(j,i,image);
				hights[j][i] = height;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		
		Map map = new Map();

		map.setVertices(vertices);

		map.setTextureCoords(textureCoords);

		map.setNormals(normals);
		map.setHeights(hights);
		//map.setId(1);
		map.setIndices(indices);
		mapDao.saveMap(map);
		
		
		

		System.out.println("");
	//	ByteBuffer wrapped = ByteBuffer.wrap(result.getIndices()); // big-endian by default
//		int num = wrapped.getShort(); // 1
	//	System.out.println("done!!");
		//return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	Byte[] toObjects(byte[] bytesPrim) {
	    Byte[] bytes = new Byte[bytesPrim.length];
	    Arrays.setAll(bytes, n -> bytesPrim[n]);
	    return bytes;
	}
	
	private float getHeight(int x, int z, BufferedImage image) {
		if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getWidth()) {
			return 0;
		}
		
		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOUR/2f;
		height /= MAX_PIXEL_COLOUR/2f;
		height *= MAX_HEIGHT;

		return height;		
	}

}