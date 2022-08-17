package ru.kata.spring.boot_security.demo.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.UserDaolmpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {


    public User convertToUserInDto(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDTO, User.class);
        Set<Role> roles = new HashSet<>();
        for (int i = 0; i < userDTO.getRoles().length; i++) {
            roles.add(new Role(userDTO.getRoles()[i]));
        }
        user.setRoles(roles);
        return user;
    }

    public UserDTO convertUserDtoInUser(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }
    public List<UserDTO> convertAllUsersInDto(List<User> userList) {
        return userList.stream().map(this::convertUserDtoInUser).collect(Collectors.toList());
    }

    public List<User> convertAllDtoInUsers(List<UserDTO> userList) {
        return userList.stream().map(this::convertToUserInDto).collect(Collectors.toList());
    }

}
