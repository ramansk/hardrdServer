package com.crux.hardrdServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Map {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer row;
	private Integer col;
	
	private float[] vertices;

	private float[] textureCoords;

	private float[] normals;

	private int[] indices;


	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getCol() {
		return col;
	}
	public void setCol(Integer col) {
		this.col = col;
	}

	public Map()
	{}
	

	private float [][] heights;
	public float[][] getHeights() {
		return heights;
	}

	public void setHeights(float[][] heights) {
		this.heights = heights;
	}



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
