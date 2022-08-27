package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private int id;

    private String firstName;

    private String lastName;
    private int age;
    private String email;
    private String password;

    private String[] roles;

    public UserDTO() {

    }


    public UserDTO(int id, String firstName, String lastName, int age, String email, String password, String[] roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}