package com.crux.hardrdServer.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.crux.hardrdServer.resource.entity.PlayerResource;
import com.crux.hardrdServer.resource.entity.Updates;

public class JPACompatiblePlayerDao implements IPlayerDao {
	private EntityManagerFactory emf;

	@Override
	public int updateOrSave(Updates updates) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		try {

		et.begin();
		Player player = new Player();
		player.setPlayerId(updates.getPlayerId());
		player.setPlayerPosX(updates.getPlayerPosX());
		player.setPlayerPosY(updates.getPlayerPosY());
		player.setPlayerPosZ(updates.getPlayerPosZ());
		player.setPlayerRotX(updates.getPlayerRotX());
		player.setPlayerRotY(updates.getPlayerRotY());
		player.setPlayerRotZ(updates.getPlayerRotZ());
		player.setCurrentSpeed(updates.getCurrentSpeed());
		em.persist(player);
		
		et.commit();
		} catch (Exception e)
		{
			et.rollback();
		}
		return 0;
	}

	@Override
	public List<PlayerResource> getAllPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerResource getPlayer(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
