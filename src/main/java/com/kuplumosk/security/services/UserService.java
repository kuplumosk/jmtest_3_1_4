package com.kuplumosk.security.services;


import com.kuplumosk.security.entitys.Role;
import com.kuplumosk.security.entitys.User;
import com.kuplumosk.security.repositories.RoleRepository;
import com.kuplumosk.security.repositories.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService() {
    }

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
            user.getRoles());
    }

    public void deleteUser(long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid filledUser Id:" + id));
        userRepository.delete(user);
    }

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

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findById(long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid filledUser Id:" + id));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

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
        UserService that = (UserService) o;
        return Objects.equals(userRepository, that.userRepository) && Objects
            .equals(roleRepository, that.roleRepository) && Objects.equals(passwordEncoder, that.passwordEncoder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRepository, roleRepository, passwordEncoder);
    }
}
