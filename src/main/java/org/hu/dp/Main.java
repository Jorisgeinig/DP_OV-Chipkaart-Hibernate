package org.hu.dp;

import org.hibernate.Session;
import org.hu.dp.domain.Reiziger;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        // Maak een Hibernate-sessie
        Session session = HibernateUtil.getSessionFactory().openSession();

        URL resource = Main.class.getClassLoader().getResource("hibernate.cfg.xml");
        if (resource == null) {
            System.out.println("hibernate.cfg.xml not found in classpath!");
        } else {
            System.out.println("hibernate.cfg.xml found at: " + resource);
        }


        try {
            // Start een transactie
            session.beginTransaction();

            // Maak een nieuwe Reiziger aan
            Reiziger reiziger = new Reiziger();
            reiziger.setReiziger_id(10);
            reiziger.setVoorletters("J");
            reiziger.setAchternaam("Jansen");
            reiziger.setGeboortedatum(Date.valueOf("1990-05-15"));

            System.out.println(reiziger);

            // Sla op in de database
            session.persist(reiziger);

            // Commit de transactie
            session.getTransaction().commit();

            System.out.println("Reiziger opgeslagen: " + reiziger);
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // Sluit de sessie
            session.close();
            HibernateUtil.shutdown();
        }
    }
    }