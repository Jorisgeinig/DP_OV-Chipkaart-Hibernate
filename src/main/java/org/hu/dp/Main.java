package org.hu.dp;

import org.hibernate.Session;
import org.hu.dp.domain.Reiziger;
import org.hu.dp.domain.ReizigerDAO;
import org.hu.dp.domain.ReizigerDAOHibernate;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    private static void testReiziger(ReizigerDAO reizigerDAO) {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = reizigerDAO.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger reiziger = new Reiziger(10, "S", null, "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        reizigerDAO.save(reiziger);
        reizigers = reizigerDAO.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Update Test
        Reiziger newReiziger = new Reiziger(10, "L.P", null, "Hoogkerk", java.sql.Date.valueOf("1977-02-04"));
        System.out.println("[Test] eerst is reiziger: " + reiziger);
        reizigerDAO.update(newReiziger);
        System.out.println("[Test] Na update is reiziger: " + newReiziger);

        // Delete Test
        System.out.println("[Test] eerst zijn er " + reizigers.size());
        reizigerDAO.delete(newReiziger);
        List<Reiziger> reizigersAfter = reizigerDAO.findAll();
        System.out.println("[Test] Na delete zijn er " + reizigersAfter.size());
    }

    public static void main(String[] args) {
        ReizigerDAO reizigerDAO = new ReizigerDAOHibernate();
        testReiziger(reizigerDAO);
        HibernateUtil.shutdown();
        }
    }
