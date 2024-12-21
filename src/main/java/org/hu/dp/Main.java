package org.hu.dp;
import org.hu.dp.domain.*;

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
        System.out.print("[Test Save] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        reizigerDAO.save(reiziger);
        reizigers = reizigerDAO.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Update Test
        Reiziger newReiziger = new Reiziger(10, "L.P", null, "Hoogkerk", java.sql.Date.valueOf("1977-02-04"));
        System.out.println("[Test Update] eerst is reiziger: " + reiziger);
        reizigerDAO.update(newReiziger);
        System.out.println("[Test] Na update is reiziger: " + newReiziger);

        // Delete Test
        System.out.println("\n[Test Delete] eerst zijn er " + reizigers.size() + " reizigers");
        reizigerDAO.delete(newReiziger);
        List<Reiziger> reizigersAfter = reizigerDAO.findAll();
        System.out.println("[Test] Na delete zijn er " + reizigersAfter.size() + " reizigers");
    }

    private static void testAdres(AdresDAO adresDAO, ReizigerDAO reizigerDAO) {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Retrieve all addresses from the database
        List<Adres> adressen = adresDAO.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Create a new Reiziger and Adres, and persist them in the database
        String geboortedatum = "1981-03-14";
        Reiziger reiziger = new Reiziger(11, "S", null, "Boers", java.sql.Date.valueOf(geboortedatum));
        Adres adres = new Adres(6, "1234AB", "12", "Bakkerstraat", "Utrecht");

        // Establish the one-to-one relationship
        adres.setReiziger(reiziger);
        reiziger.setAdres(adres);

        System.out.print("[Test Save] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adresDAO.save(adres);  // Save Adres and reiziger together
        adressen = adresDAO.findAll();
        System.out.println(adressen.size() + " adressen\n");

        // Update Test
        Adres updatedAdres = new Adres(6, "5678CD", "34", "Kerkstraat", "Amsterdam");
        updatedAdres.setReiziger(reiziger);  // Keep the relationship intact
        System.out.println("[Test Update] eerst is adres: " + adres);
        adresDAO.update(updatedAdres);
        System.out.println("[Test] Na update is adres: " + updatedAdres);

        // Delete Test
        System.out.println("\n[Test Delete] eerst zijn er " + adressen.size() + " adressen");
        adresDAO.delete(updatedAdres);
        reizigerDAO.delete(reiziger);
        List<Adres> adressenAfter = adresDAO.findAll();
        System.out.println("[Test] Na delete zijn er " + adressenAfter.size() + " adressen");
    }

    private static void testOVChipkaart(OVChipkaartDAO ovChipkaartDAO, ReizigerDAO reizigerDAO) {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Retrieve all OVChipkaarten from the database
        List<OVChipkaart> ovChipkaarten = ovChipkaartDAO.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende OVChipkaarten:");
        for (OVChipkaart oc : ovChipkaarten) {
            System.out.println(oc);
        }
        System.out.println();

        // Create a new Reiziger and OVChipkaart, associate them with each other
        Reiziger reiziger = new Reiziger(12, "S", null, "Boers", java.sql.Date.valueOf("1981-03-14"));
        OVChipkaart ovChipkaart = new OVChipkaart(12345, java.sql.Date.valueOf("2024-12-31"), 2, 50.0);
        reiziger.getOvChipkaartList().add(ovChipkaart);
        ovChipkaart.setReiziger(reiziger);

        // Save the OVChipkaart with the Reiziger relation by just saving reiziger and making use of the cascade function
        // Which then also saves OVChipkaart
        System.out.print("[Test Save] Eerst " + ovChipkaarten.size() + " OVChipkaarten, na ReizigerDAO.save() ");
        reizigerDAO.save(reiziger);
        ovChipkaarten = ovChipkaartDAO.findAll();
        System.out.println(ovChipkaarten.size() + " OVChipkaarten\n");


        // Test saving just the OVChipkaart with an existing Reiziger in the database
        System.out.print("[Test Save] Eerst " + ovChipkaarten.size() + " OVChipkaarten, na OVChipkaartDAO.save() ");
        OVChipkaart ovChipkaart1 = new OVChipkaart(88888, java.sql.Date.valueOf("2013-08-04"), 1, 35.0);
        Reiziger persistedReiziger = reizigerDAO.findAll().get(0);
        ovChipkaart1.setReiziger(persistedReiziger);
        ovChipkaartDAO.save(ovChipkaart1);
        ovChipkaarten = ovChipkaartDAO.findAll();
        System.out.println(ovChipkaarten.size() + " OVChipkaarten\n");
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende OVChipkaarten:");
        for (OVChipkaart oc : ovChipkaarten) {
            System.out.println(oc);
        }
        System.out.println();

        // Update Test
        System.out.println("[Test Update] eerst is ovchipkaart: " + ovChipkaart);
        OVChipkaart newovChipkaart = new OVChipkaart(12345, java.sql.Date.valueOf("2022-12-31"), 1, 100.0);
        Reiziger reiziger1 = reizigerDAO.findAll().get(0);
        newovChipkaart.setReiziger(reiziger1);
        ovChipkaartDAO.update(newovChipkaart);
        System.out.println("[Test] Na update is ovchipkaart: " + newovChipkaart);


        // Test findByReiziger() function.
        List<OVChipkaart> ovChipkaartenByReiziger = ovChipkaartDAO.findByReiziger(reiziger1);
        System.out.println("\n[Test findByReiziger] For reiziger " + reiziger1.getAchternaam() + ", found " + ovChipkaartenByReiziger.size() + " OVChipkaarten");

        // Delete Test
        System.out.println("\n[Test Delete] eerst zijn er " + ovChipkaarten.size() + " OVChipkaarten");
        ovChipkaartDAO.delete(ovChipkaart);
        reizigerDAO.delete(reiziger);
        ovChipkaarten = ovChipkaartDAO.findAll();
        System.out.println("[Test] Na delete zijn er " + ovChipkaarten.size() + " OVChipkaarten");

        ovChipkaartDAO.delete(ovChipkaart1);
    }


    public static void main(String[] args) {
        ReizigerDAO reizigerDAO = new ReizigerDAOHibernate();
        AdresDAO adresDAO = new AdresDAOPHibernate();
        OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOPHibernate();
      //  testReiziger(reizigerDAO);
       // testAdres(adresDAO, reizigerDAO);
        testOVChipkaart(ovChipkaartDAO, reizigerDAO);

        HibernateUtil.shutdown();
        }
    }
