package org.exampleProjectOne.service;

import org.exampleProjectOne.dao.UserDao;
import org.exampleProjectOne.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static UserService userService;
    private UserDao userDao;

    private UserService() throws Exception {
        initialize();
        userDao = UserService.getBankClientDAO();
    }

    public static UserService getUserService() throws Exception {
        if (userService == null) {
            userService = new UserService();
            return userService;
        }
        return userService;
    }

    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    public boolean addUser(User client) throws Exception {
        Connection connection = userDao.getConnection();
        if (client != null && userDao.validateClient(client.getName(), client.getPassword())) {
            try (PreparedStatement stmt = connection.prepareStatement("select * from users where name = ?")) {
                stmt.setString(1, client.getName());
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return false;
                }
                resultSet.close();
                try {
                    userDao.addClient(client);
                    return true;
                } catch (SQLException e) {
                    throw new Exception();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void updateClientsMoney(Long id, String name, String password, Long age) throws SQLException {
        userDao.updateClientsMoney(id, name, password, age);
    }

    public List<User> getUserByName(String name) throws SQLException {
        return userDao.getUserByName(name);
    }

    public void deleteUser(User user) throws SQLException {
        userDao.deleteUser(user);
    }

    private void initialize() throws Exception {

        DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
        StringBuilder url = new StringBuilder();

        url.
                append("jdbc:mysql://").        //db type
                append("localhost:").           //host name
                append("3306/").                //port
                append("mysql?").          //db name
                append("user=root&").          //login
                append("password=admin65");       //password

        Connection connection = DriverManager.getConnection(url.toString());
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS users");
        stmt.execute("create table if not exists users (id bigint auto_increment, name varchar(256), password varchar(256), age bigint, primary key (id))");
        stmt.close();
        connection.close();

        User userOne = new User("Admin", "admin", 1L);
        User userTwo = new User("AdminTwo", "adminTwo", 2L);

        UserDao userDao = UserService.getBankClientDAO();
        userDao.addUser(userOne);
        userDao.addUser(userTwo);
        userDao.close();
    }


    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("mysql?").          //db name
                    append("user=root&").          //login
                    append("password=admin65");       //password

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static UserDao getBankClientDAO() {
        return new UserDao(getMysqlConnection());
    }


}
