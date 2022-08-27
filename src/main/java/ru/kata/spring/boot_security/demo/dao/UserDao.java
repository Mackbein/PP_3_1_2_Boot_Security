package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUserById(int id);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    User findByEmail(String email);

    List<Role> getAllRoles();

}

