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
            session.persist(ovChipkaart);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Er is iets fout gegaan bij het opslaan van de OVChipkaart");
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
            session.merge(ovChipkaart);

            // Commit the transaction
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Updaten van OVChipkaart is niet gelukt.");
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

            OVChipkaart ovChipkaart1 = session.find(OVChipkaart.class, ovChipkaart.getKaart_nummer());
            if (ovChipkaart1 != null) {
                session.remove(ovChipkaart1);
                // Commit the transaction
                session.getTransaction().commit();

                return true;
            }
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Reiziger niet gevonden in de database");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return false;
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
            System.out.println("OVChipkaart niet gevonden in de database");
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
            System.out.println("Er is iets fout gegaan bij het opzoeken van alle OvChipkaarten");
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
            System.out.println("OVChipkaart(en) niet gevonden bij deze reiziger of in de database");
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}