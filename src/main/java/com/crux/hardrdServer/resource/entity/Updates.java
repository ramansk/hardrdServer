package com.crux.hardrdServer.resource.entity;

public class Updates {
	private String playerId;
	private Float playerPosX;
	private Float playerPosY;
	private Float playerPosZ;
	private Float playerRotX;
	private Float playerRotY;
	private Float playerRotZ;
	private Float currentSpeed;
	public Float getCurrentSpeed() {
		return currentSpeed;
	}
	public void setCurrentSpeed(Float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public Float getPlayerPosX() {
		return playerPosX;
	}
	public void setPlayerPosX(Float playerPosX) {
		this.playerPosX = playerPosX;
	}
	public Float getPlayerPosY() {
		return playerPosY;
	}
	public void setPlayerPosY(Float playerPosY) {
		this.playerPosY = playerPosY;
	}
	public Float getPlayerPosZ() {
		return playerPosZ;
	}
	public void setPlayerPosZ(Float playerPosZ) {
		this.playerPosZ = playerPosZ;
	}
	public Float getPlayerRotX() {
		return playerRotX;
	}
	public void setPlayerRotX(Float playerRotX) {
		this.playerRotX = playerRotX;
	}
	public Float getPlayerRotY() {
		return playerRotY;
	}
	public void setPlayerRotY(Float playerRotY) {
		this.playerRotY = playerRotY;
	}
	public Float getPlayerRotZ() {
		return playerRotZ;
	}
	public void setPlayerRotZ(Float playerRotZ) {
		this.playerRotZ = playerRotZ;
	}
}
