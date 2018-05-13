package com.crux.hardrdServer.model;

import java.util.List;

import com.crux.hardrdServer.resource.entity.PlayerResource;
import com.crux.hardrdServer.resource.entity.Updates;

public interface IPlayerDao {
	int updateOrSave(Player player);
	List<PlayerResource> getAllPlayers();
	PlayerResource getPlayer(String id);
}
