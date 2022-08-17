package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.dto.UserDtoConverter;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public class UserServicelmpl implements UserService {

    private final UserDao userDao;
    private final UserDtoConverter userDtoConverter;
    @Autowired
    public UserServicelmpl(UserDao userDao, UserDtoConverter userDtoConverter) {
        this.userDao = userDao;
        this.userDtoConverter = userDtoConverter;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userDtoConverter.convertAllUsersInDto(userDao.getAllUsers());
    }

    @Override
    public UserDTO getUserById(int id) {
        return userDtoConverter.convertUserDtoInUser(userDao.getUserById(id));
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        userDao.saveUser(userDtoConverter.convertToUserInDto(userDTO));

    }

    @Override
    public void updateUser(UserDTO userDTO) {
        userDao.updateUser(userDtoConverter.convertToUserInDto(userDTO));
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
    @Override
    public List<Role> getAllRoles() {
        return userDao.getAllRoles();
    }

    public UserDTO getAuthorizedUser(){
        User user = userDao.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return userDtoConverter.convertUserDtoInUser(user);
    }

}
