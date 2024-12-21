package org.hu.dp;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hu.dp.domain.Adres;
import org.hu.dp.domain.OVChipkaart;
import org.hu.dp.domain.Product;
import org.hu.dp.domain.Reiziger;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Laad de configuratie en registreer services
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration
                    .addAnnotatedClass(Reiziger.class)
                    .addAnnotatedClass(Adres.class)
                    .addAnnotatedClass(OVChipkaart.class)
                    .addAnnotatedClass(Product.class)
                    .buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
