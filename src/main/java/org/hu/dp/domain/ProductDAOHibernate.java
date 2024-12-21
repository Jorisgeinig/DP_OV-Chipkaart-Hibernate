package org.hu.dp.domain;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hu.dp.HibernateUtil;

import java.util.List;

public class ProductDAOHibernate implements ProductDAO{

    public boolean save(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(product);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Er is iets fout gegaan bij het opslaan van de Product");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Update the OVChipkaart in the database
            session.merge(product);

            // Commit the transaction
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Updaten van product is niet gelukt.");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Product niet gevonden in de database");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Use HQL to query the `ov_chipkaart_product` join table indirectly
            String hql = "SELECT p FROM Product p JOIN p.ovChipkaarten o WHERE o.kaart_nummer = :kaart_nummer";
            Query<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("kaart_nummer", ovChipkaart.getKaart_nummer());

            List<Product> products = query.getResultList();

            session.getTransaction().commit();
            return products;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Producten konden niet opgehaald worden.");
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            String hql = "FROM Product";
            Query<Product> query = session.createQuery(hql, Product.class);
            List<Product> product = query.getResultList();
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public Product findById(int productNummer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Product product = session.find(Product.class, productNummer);
            session.close();
            return product;
        } catch (Exception e) {
            System.out.println("Product is niet gevonden");
            e.printStackTrace();
        }
        return null;
    }
}
