package com.crux.hardrdServer.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class GMap
{


	private int tileSize;

	//tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	public List<MapInternal> getTiles() {
		return tiles;
	}

	public void setTiles(List<MapInternal> tiles) {
		this.tiles = tiles;
	}


	private int numTilesAlong;
	private List<MapInternal> tiles = new ArrayList<>();

	
	public GMap(int tileSize)
	{
		this.tileSize = tileSize;
		//numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		//numColsToDraw = GamePanel.WIDTH / tileSize + 2;
	}
	
	public void loadTiles(String s)
	{
		try
		{
			tileset = ImageIO.read(new File(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			numTilesAlong = tileset.getHeight() / tileSize;
			//tiles = new Map[2][numTilesAcross];
			
			BufferedImage subImage;
			
			for (int col = 0; col <numTilesAcross; col++)
			{
				for(int row = 0; row <numTilesAlong; row++)
				{
				subImage = tileset.getSubimage(
						col * tileSize,
						row * tileSize,
						tileSize,
						tileSize
				);
				tiles.add(new MapInternal(subImage, row, col));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public int getTileSize()
	{
		return tileSize;
	}
}