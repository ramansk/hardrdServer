package com.crux.hardrdServer.model;

import java.sql.Blob;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.BlobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crux.hardrdServer.resource.entity.PlayerResource;


@Component
public class MapDao {
	@Autowired
	SessionFactory sessionFactory;
	
	public void saveMap(Map map)
	{
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx  = session.beginTransaction();

			session.saveOrUpdate(map);
			
			tx.commit();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		}
	}
	
	public Map getMap(Integer id) {
		Transaction tx = null;
		Map m = null;
		PlayerResource pr = new PlayerResource();
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			m = session.get(Map.class, id);


			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		}

		return m;
	}
}
