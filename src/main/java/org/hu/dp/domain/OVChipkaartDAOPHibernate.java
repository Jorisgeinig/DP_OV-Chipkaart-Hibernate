package org.hu.dp.domain;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hu.dp.HibernateUtil;

import java.util.List;

public class OVChipkaartDAOPHibernate implements OVChipkaartDAO {

    public boolean save(OVChipkaart ovChipkaart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Save the ovChipkaart in the database
            session.persist(ovChipkaart);

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

    public boolean update(OVChipkaart ovChipkaart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Update the OVChipkaart in the database
            session.update(ovChipkaart);

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

    public boolean delete(OVChipkaart ovChipkaart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Retrieve the persistent OVChipkaart from the database
            OVChipkaart persistentOvChipkaart = session.get(OVChipkaart.class, ovChipkaart.getKaart_nummer());
            if (persistentOvChipkaart != null) {
                session.delete(persistentOvChipkaart);
            } else {
                System.out.println("OVChipkaart not found in the database");
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

    public OVChipkaart findbyKaartNummer(int kaartNummer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Query to find OVChipkaart by kaart_nummer
            String hql = "FROM OVChipkaart WHERE kaart_nummer = :kaartNr";
            Query<OVChipkaart> query = session.createQuery(hql, OVChipkaart.class);
            query.setParameter("kaartNr", kaartNummer);

            OVChipkaart ovChipkaart = query.uniqueResult(); // Should return one result or null
            session.getTransaction().commit();
            return ovChipkaart;
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

    public List<OVChipkaart> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // HQL query to retrieve all OVChipkaart objects
            String hql = "FROM OVChipkaart";
            Query<OVChipkaart> query = session.createQuery(hql, OVChipkaart.class);
            List<OVChipkaart> ovChipkaarten = query.getResultList();

            return ovChipkaarten;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Query to find OVChipkaart by Reiziger
            String hql = "FROM OVChipkaart WHERE reiziger = :reiziger";
            Query<OVChipkaart> query = session.createQuery(hql, OVChipkaart.class);
            query.setParameter("reiziger", reiziger);

            List<OVChipkaart> ovChipkaarten = query.getResultList();
            session.getTransaction().commit();
            return ovChipkaarten;
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
}