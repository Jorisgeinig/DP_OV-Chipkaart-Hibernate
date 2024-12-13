package org.hu.dp.domain;

import org.hibernate.Session;
import org.hu.dp.HibernateUtil;
import org.hibernate.query.Query;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {


    public boolean save(Reiziger reiziger) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Sla op in de database
            session.persist(reiziger);

            // Commit de transactie
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Reiziger reiziger) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(reiziger);
            session.getTransaction().commit();
        return true;
    } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Reiziger reiziger) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Reiziger persistentReiziger = session.get(Reiziger.class, reiziger.getReiziger_id());
            if (persistentReiziger != null) {
                session.delete(persistentReiziger);
            } else {
                System.out.println("Reiziger niet gevonden in de database");
                return false;
            }
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public List<Reiziger> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            String hql = "FROM Reiziger";
            Query<Reiziger> query = session.createQuery(hql, Reiziger.class);
            List<Reiziger> reizigers = query.getResultList();
            return reizigers;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
        } finally {
            session.close();
        }
    }
}
