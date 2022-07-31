package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    Object getUserById(int id);

    void saveUser(User user);

    void updateUser(int id, User user);

    void deleteUser(int id);

    User findByUsername(String username);
    List<Role> getAllRoles();
}
