package com.crux.hardrdServer.resource.entity;

public class PlayerResource {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Float getPosX() {
		return posX;
	}
	public void setPosX(Float posX) {
		this.posX = posX;
	}
	public Float getPosY() {
		return posY;
	}
	public void setPosY(Float posY) {
		this.posY = posY;
	}
	public Float getPosZ() {
		return posZ;
	}
	public void setPosZ(Float posZ) {
		this.posZ = posZ;
	}
	public Float getRotX() {
		return rotX;
	}
	public void setRotX(Float rotX) {
		this.rotX = rotX;
	}
	public Float getRotY() {
		return rotY;
	}
	public void setRotY(Float rotY) {
		this.rotY = rotY;
	}
	public Float getRotZ() {
		return rotZ;
	}
	public void setRotZ(Float rotZ) {
		this.rotZ = rotZ;
	}

	private Float posX, posY, posZ;
	private Float rotX, rotY, rotZ;
	private Float currentSpeed;
	public Float getCurrentSpeed() {
		return currentSpeed;
	}
	public void setCurrentSpeed(Float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

}
