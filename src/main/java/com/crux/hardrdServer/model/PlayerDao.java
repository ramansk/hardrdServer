package com.crux.hardrdServer.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crux.hardrdServer.resource.entity.PlayerResource;
import com.crux.hardrdServer.resource.entity.Updates;

@Component
public class PlayerDao implements IPlayerDao{
	@Autowired
	SessionFactory sessionFactory;
	Transaction tx = null;
	public int updateOrSave(Updates updates) {
		try (Session session = sessionFactory.openSession()) {
			tx  = session.beginTransaction();
			Player player = new Player();
			player.setPlayerId(updates.getPlayerId());
			player.setPlayerPosX(updates.getPlayerPosX());
			player.setPlayerPosY(updates.getPlayerPosY());
			player.setPlayerPosZ(updates.getPlayerPosZ());
			player.setPlayerRotX(updates.getPlayerRotX());
			player.setPlayerRotY(updates.getPlayerRotY());
			player.setPlayerRotZ(updates.getPlayerRotZ());
			player.setCurrentSpeed(updates.getCurrentSpeed());
			session.saveOrUpdate(player);
			
			tx.commit();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		}

		return 0;

	}
	
	
	public List<PlayerResource> getAllPlayers()
	{
		List<PlayerResource> prl = new ArrayList<>();
		try (Session session = sessionFactory.openSession()) {
			tx  = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Player> pl = session.createQuery("from Player").setCacheable(true).list();
			for(Player p: pl)
			{
				PlayerResource pr = new PlayerResource();
				pr.setPosX(p.getPlayerPosX());
				pr.setPosY(p.getPlayerPosY());
				pr.setPosZ(p.getPlayerPosZ());
				pr.setRotX(p.getPlayerRotX());
				pr.setRotY(p.getPlayerRotY());
				pr.setRotZ(p.getPlayerRotZ());
				pr.setName(p.getPlayerId());
				pr.setCurrentSpeed(p.getCurrentSpeed());
				
				prl.add(pr);
			}


			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		}

		return prl;
	}
	
	public PlayerResource getPlayer(String id) {
		PlayerResource pr = new PlayerResource();
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			Player p = session.get(Player.class, id);
			if(p == null)
			{
	//			throw new TehnicalException("000", "No player in database");
			}
			
			
			pr.setPosX(p.getPlayerPosX());
			pr.setPosY(p.getPlayerPosY());
			pr.setPosZ(p.getPlayerPosZ());
			pr.setRotX(p.getPlayerRotX());
			pr.setRotY(p.getPlayerRotY());
			pr.setRotZ(p.getPlayerRotZ());
			pr.setName(p.getPlayerId());
			pr.setCurrentSpeed(p.getCurrentSpeed());
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		}

		return pr;
	}
}
