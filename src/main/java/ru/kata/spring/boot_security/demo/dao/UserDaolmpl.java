package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDaolmpl implements UserDao, UserDetailsService {

    @PersistenceContext
    EntityManager entityManager;


    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        try {
            return entityManager.createQuery("FROM User", User.class).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public User getUserById(int id) {
        try {
            return entityManager.find(User.class, id);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Transactional
    public void saveUser(User user) {
        try {
            entityManager.persist(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Transactional
    public void updateUser(int id, User user) {
        try {
            User userResult = entityManager.find(User.class, id);
            userResult.setAge(user.getAge());
            userResult.setUsername(user.getUsername());
            userResult.setEmail(user.getEmail());
            entityManager.merge(userResult);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Transactional
    public void deleteUser(int id) {
        try {
            User user = entityManager.find(User.class, id);
            entityManager.remove(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Transactional
    public User findByUsername(String username) {
        try {
            return (User) entityManager.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}


