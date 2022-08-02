package ru.kata.spring.boot_security.demo.models;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
@Data
public class User implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private int age;




    @Column
    private String password;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST} )
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User() {

    }

    public User(String username, String email, int age, String firstName, String lastName, Set<Role> roles) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.email = email;
        this.age = age;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && Objects.equals(username, user.username)
                && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, age, email, firstName, lastName);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
    public String reloadStr() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Role role : this.roles) {
            String roleN = role.getName().substring(5) + " ";
            stringBuilder.append(roleN);

        }
        return String.valueOf(stringBuilder);
    }
}