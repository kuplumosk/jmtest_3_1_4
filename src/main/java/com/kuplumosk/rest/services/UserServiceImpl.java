package com.kuplumosk.rest.services;

import com.kuplumosk.rest.entitys.Role;
import com.kuplumosk.rest.entitys.User;
import com.kuplumosk.rest.exceptionHandling.NoSuchUserException;
import com.kuplumosk.rest.repositories.RoleRepository;
import com.kuplumosk.rest.repositories.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), user.getRoles());
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void updateUser(User user) {
        String newPass;
        if (passwordEncoder.matches(user.getPassword(),
            userRepository.getOne(user.getId()).getPassword())) {
            newPass = userRepository.getOne(user.getId()).getPassword();
        } else if (user.getPassword().equals("")) {
            newPass = userRepository.getOne(user.getId()).getPassword();
        } else {
            newPass = passwordEncoder.encode(user.getPassword());
        }
        user.setPassword(newPass);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(long id) {
        User user = userRepository.getOne(id);
        userRepository.delete(user);
    }

    @Override
    public User findById(long id) {
        User user;
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new NoSuchUserException(" There is no user with ID = " + id);
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserServiceImpl that = (UserServiceImpl) o;
        return Objects.equals(userRepository, that.userRepository) && Objects
            .equals(roleRepository, that.roleRepository) && Objects.equals(passwordEncoder, that.passwordEncoder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRepository, roleRepository, passwordEncoder);
    }
}
