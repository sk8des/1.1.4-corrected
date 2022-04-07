package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection connection = Util.getInst().getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user" +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(20), age TINYINT)");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS user");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pstat = connection.prepareStatement("INSERT INTO user (name, lastName, age) VALUES(?, ?, ?)")) {
            connection.setAutoCommit(false);
            pstat.setString(1, name);
            pstat.setString(2, lastName);
            pstat.setByte(3, age);
            pstat.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement pstat = connection.prepareStatement("DELETE FROM user WHERE id = ?")) {
            pstat.setLong(1, id);
            pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public List<User> getAllUsers () {
            List<User> usList = new ArrayList<>();
            try(ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM user")) {
                while(resultSet.next()) {
                    User users = new User(resultSet.getString("name"), resultSet.getString("lastName"),
                            resultSet.getByte("age"));
                    users.setId(resultSet.getLong("id"));
                    usList.add(users);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return usList;
        }

        public void cleanUsersTable () {
            try(Statement statement = connection.createStatement()) {
                statement.executeUpdate("TRUNCATE TABLE user");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}

