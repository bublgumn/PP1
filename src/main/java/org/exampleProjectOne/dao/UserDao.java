package org.exampleProjectOne.dao;

import org.exampleProjectOne.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public List<User> getAllUser() {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User bankClient = new User();
                bankClient.setName(resultSet.getString(2));
                bankClient.setPassword(resultSet.getString(3));
                bankClient.setAge(resultSet.getLong(4));
                bankClient.setId(resultSet.getLong(1));
                result.add(bankClient);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<User> getUserByName(String name) throws SQLException {
        String search = "select * from users where name = ?;";
        List<User> userList = new ArrayList<>();
        User result = null;
        try (PreparedStatement stmt = connection.prepareStatement(search)
        ) {
            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result = new User();
                result.setName(resultSet.getString(2));
                result.setPassword(resultSet.getString(3));
                result.setAge(resultSet.getLong(4));
                result.setId(resultSet.getLong(1));
            }
        }
        if (result != null){
            userList.add(result);
        }
        return userList;
    }

    public void deleteUser(User user) throws SQLException {
        String sql = "DELETE FROM users WHERE name = ? and password = ? and age = ?";
        PreparedStatement result = connection.prepareStatement(sql);
        result.setString(1, user.getName());
        result.setString(2, user.getPassword());
        result.setLong(3, user.getAge());
        result.executeUpdate();
        result.close();
    }

    public void updateClientsMoney(Long id, String name, String password, Long age) throws SQLException {
        if (validateClient(name, password) && id != null && age !=null) {
            String update = "update users set name = ?, password = ?, age = ? where id = ?";
            PreparedStatement result = connection.prepareStatement(update);
            result.setString(1, name);
            result.setString(2, password);
            result.setLong(3, age);
            result.setLong(4, id);
            result.executeUpdate();
            result.close();
        }
    }

    public boolean validateClient(String name, String password) {
        return !name.isEmpty() && !password.isEmpty() && !(name == null) && !(password == null);
    }

    public void addClient(User client) throws SQLException {
        String update = "insert into users (name, password, age) values (?, ?, ?)";
        PreparedStatement result = connection.prepareStatement(update);
        result.setString(1, client.getName());
        result.setString(2, client.getPassword());
        result.setLong(3, client.getAge());
        result.executeUpdate();
        result.close();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User client) throws SQLException {
        String update = "insert into users (name, password, age) values (?, ?, ?)";
        PreparedStatement result = connection.prepareStatement(update);
        result.setString(1, client.getName());
        result.setString(2, client.getPassword());
        result.setLong(3, client.getAge());
        result.executeUpdate();
        result.close();
    }

}
