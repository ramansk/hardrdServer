package com.crux.hardrdServer.model;

import java.awt.image.BufferedImage;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Map {
	int row;
	int col;
	private static final float SIZE = 1800;
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 256*256*256;
	public Map()
	{}
	public Map(BufferedImage image, int row, int col)
	{
		float [][] hights = null;

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

		this.setVertices(vertices);

		this.setTextureCoords(textureCoords);

		this.setNormals(normals);
		this.setHeights(hights);
		this.setId(1);
		this.setIndices(indices);
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private float [][] heights;
	public float[][] getHeights() {
		return heights;
	}

	public void setHeights(float[][] heights) {
		this.heights = heights;
	}

	private float[] vertices;

	private float[] textureCoords;

	private float[] normals;

	private int[] indices;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public void setTextureCoords(float[] textureCoords) {
		this.textureCoords = textureCoords;
	}

	public float[] getNormals() {
		return normals;
	}

	public void setNormals(float[] normals) {
		this.normals = normals;
	}

	public int[] getIndices() {
		return indices;
	}

	public void setIndices(int[] indices) {
		this.indices = indices;
	}

}
