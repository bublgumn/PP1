package org.exampleProjectOne.util;

import org.exampleProjectOne.dao.UserDaoMysql;
import org.exampleProjectOne.model.User;

import java.sql.*;

public class Initialize {

    public static void initializeMysql() throws Exception {
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

        UserDaoMysql userDaoMysql = getUserDaoMysql();
        userDaoMysql.addUser(userOne);
        userDaoMysql.addUser(userTwo);
        userDaoMysql.close();
    }


    public static Connection getMysqlConnection() {
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

    public static UserDaoMysql getUserDaoMysql() {
        return new UserDaoMysql(getMysqlConnection());
    }
}
