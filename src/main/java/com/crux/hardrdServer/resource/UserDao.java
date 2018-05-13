package com.crux.hardrdServer.resource;

import com.crux.hardrdServer.model.Userr;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;
    Transaction tx = null;
    public Userr getUser(String name)
    {
        Userr u = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            u = session.get(Userr.class, name);


            tx.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }

        return u;
    }

    public void createUser(Userr user)
    {
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(user);


            tx.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

}
