package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USERNAME = "postgres";
    private static final String JDBC_PASSWORD = "Asdqwe153!";

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory buildSessionFactory() {
        try {

            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                    .applySetting("hibernate.connection.url", JDBC_URL)
                    .applySetting("hibernate.connection.username", JDBC_USERNAME)
                    .applySetting("hibernate.connection.password", JDBC_PASSWORD)
                    .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect")
                    .build();

            return new MetadataSources(registry)
                    .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Connection getConnection() {
        return connection;
    }
}
