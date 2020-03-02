package org.exampleProjectOne.service;

import org.exampleProjectOne.dao.UserDao;
import org.exampleProjectOne.dao.UserDaoMysql;
import org.exampleProjectOne.model.User;
import org.exampleProjectOne.util.Initialize;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceMysql implements UserService {

    private static UserServiceMysql userServiceMysql;

    private UserServiceMysql() throws Exception {
        Initialize.initializeMysql();
    }

    public static UserServiceMysql getUserServiceMysql() throws Exception {
        if (userServiceMysql == null) {
            userServiceMysql = new UserServiceMysql();
            return userServiceMysql;
        }
        return userServiceMysql;
    }

    public List<User> getAllUser() {
        try (UserDao userDao = Initialize.getUserDaoMysql()) {
            return userDao.getAllUser();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public boolean addUser(User user) {
        if (user != null) {
            try (UserDao userDao = Initialize.getUserDaoMysql()) {
                userDao.addUser(user);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public void updateClient(Long id, String name, String password, Long age) {
        try (UserDao userDao = Initialize.getUserDaoMysql()) {
            userDao.updateClient(id, name, password, age);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getUserByName(String name) {
        try (UserDao userDao = Initialize.getUserDaoMysql()) {
            List<User> result = userDao.getUserByName(name);
            return result;
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    public void deleteUser(User user) {
        try (UserDao userDao = Initialize.getUserDaoMysql()) {
            userDao.deleteUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
