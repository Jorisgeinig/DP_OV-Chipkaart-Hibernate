package org.hu.dp.domain;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hu.dp.HibernateUtil;

import java.util.List;

public class AdresDAOPHibernate implements AdresDAO {

    public boolean save(Adres adres) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Save the Adres in the database
            session.persist(adres.getReiziger());

            // Commit the transaction
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

    public boolean update(Adres adres) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Update the Adres in the database
            session.update(adres);

            // Commit the transaction
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

    public boolean delete(Adres adres) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Retrieve the persistent Adres from the database
            Adres persistentAdres = session.get(Adres.class, adres.getAdres_id());
            if (persistentAdres != null) {
                session.delete(persistentAdres);
            } else {
                System.out.println("Adres not found in the database");
                return false;
            }

            // Commit the transaction
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

    public Adres findByReiziger(Reiziger reiziger) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Query to find Adres by the Reiziger ID
            String hql = "FROM Adres WHERE reiziger.reiziger_id = :reizigerId";
            Query<Adres> query = session.createQuery(hql, Adres.class);
            query.setParameter("reizigerId", reiziger.getReiziger_id());

            Adres adres = query.uniqueResult(); // Should return one result or null
            session.getTransaction().commit();
            return adres;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Adres> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // HQL query to retrieve all Adres objects
            String hql = "FROM Adres";
            Query<Adres> query = session.createQuery(hql, Adres.class);
            List<Adres> adressen = query.getResultList();

            return adressen;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
