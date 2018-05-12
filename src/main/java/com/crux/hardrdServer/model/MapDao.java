package com.crux.hardrdServer.model;

import java.sql.Blob;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
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
	
	public void saveMap(MapInternal mapI)
	{
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			//tx  = session.beginTransaction();

			Map map = new Map();
			map.setCol(mapI.getCol());
			map.setRow(mapI.getRow());
			map.setHeights(mapI.getHeights());
			map.setIndices(mapI.getIndices());
			map.setNormals(mapI.getNormals());
			map.setTextureCoords(mapI.getTextureCoords());
			map.setVertices(mapI.getVertices());
			session.saveOrUpdate(map);
			
			//tx.commit();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		}
	}
	
	public Map getMap(Integer colNum, Integer rowNum) {
		Transaction tx = null;
		Map m = null;
		PlayerResource pr = new PlayerResource();
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Map> query = builder.createQuery(Map.class);
			Root<Map> root = query.from( Map.class );
			
			query.select( root).where(builder.equal(root.get("col"), colNum), builder.equal(root.get("row"), rowNum));

			m = session.createQuery(query).getResultList().get(0);
			



			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		}

		return m;
	}
}
