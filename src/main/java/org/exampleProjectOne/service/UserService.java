package org.exampleProjectOne.service;

import org.exampleProjectOne.model.User;
import org.exampleProjectOne.util.Initialize;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> getAllUser();

    boolean addUser(User user);

    void updateClient(Long id, String name, String password, Long age);

    List<User> getUserByName(String name);

    void deleteUser(User user);
}
