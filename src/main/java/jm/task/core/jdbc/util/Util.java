package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection = null;
    private static Util inst = null;
    private String username = "root";
    private String pass = "12345";
    private String url = "jdbc:mysql://localhost:3306/Mysql";
    private Util() {
        try {
            if (null == connection || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, pass);
                System.out.println("Database connected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Util getInst() {
        if (null == inst) {
            inst = new Util();
        }
        return inst;
    }

    public Connection getConnection() {
        return connection;
    }

    public static class HibernateUtil {
        private static SessionFactory sessionFactory;
        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                try {
                    Configuration configuration = new Configuration();

                    // Hibernate settings equivalent to hibernate.cfg.xml's properties
                    Properties settings = new Properties();
                    settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                    settings.put(Environment.URL, "jdbc:mysql://localhost:3306/mysql");
                    settings.put(Environment.USER, "root");
                    settings.put(Environment.PASS, "12345");
                    settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                    settings.put(Environment.SHOW_SQL, "true");

                    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                    settings.put(Environment.HBM2DDL_AUTO, "update");

                    configuration.setProperties(settings);

                    configuration.addAnnotatedClass(User.class);

                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();

                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return sessionFactory;
        }
    }
}
