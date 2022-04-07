package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query query = session.createSQLQuery(" CREATE TABLE IF NOT EXISTS user " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(20) NOT NULL, lastName VARCHAR(20) NOT NULL, " +
                    "age TINYINT NOT NULL)").addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if(transaction != null) {
                transaction.rollback();
            }
        }
    }
    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query query = session.createSQLQuery("DROP TABLE IF EXISTS user").addEntity(User.class);
            query.executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = Util.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = Util.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            transaction.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Transaction transaction = null;
        try (Session session = Util.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            users = session.createQuery("from User").list();


            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("delete from User").executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if(transaction != null){
                transaction.rollback();
            }
        }
    }
}
