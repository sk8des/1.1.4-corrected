package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    private static final UserService us = new UserServiceImpl();

    public static void main(String[] args) {
        us.createUsersTable();

        us.saveUser("Дмитрий", "Трощенко", (byte) 21);
        us.saveUser("Татьяна", "Трощенко", (byte) 18);
        us.saveUser("Аркадий", "Емельянов", (byte) 19);
        us.saveUser("Ксения", "Онегина", (byte) 24);

        us.removeUserById(2);

        us.getAllUsers();

        us.cleanUsersTable();

        us.dropUsersTable();
    }
}
