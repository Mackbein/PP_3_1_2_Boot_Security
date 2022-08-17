package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(int id);

    void saveUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void deleteUser(int id);

    User findByEmail(String email);
    List<Role> getAllRoles();


}
