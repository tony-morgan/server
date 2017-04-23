package com.example.security.services;

import com.example.controllers.security.UserDTO;
import com.example.security.exceptions.EmailExistsException;
import com.example.security.exceptions.UsernameExistsException;
import com.example.security.models.Role;
import com.example.security.models.User;
import com.example.security.models.UserRole;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UserRepository;
import com.example.security.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by an on 20.04.2017.
 */
@Service
public class UserRegistrationServiceSupport implements UserRegistrationService {

    private static final String DEFAULT_ROLE_NAME = "Clients";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public User registerNewUserAccount(UserDTO userDTO) throws EmailExistsException, UsernameExistsException {

        if (emailExists(userDTO.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address: " + userDTO.getEmail());
        }
        if (usernameExists(userDTO.getUsername())) {
            throw new UsernameExistsException(
                    "There is an account with that username: " + userDTO.getUsername());
        }
        User user = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setEncryptedPassword(hashedPassword);

        Role defaultRole = roleRepository.findByName(DEFAULT_ROLE_NAME);
        UserRole userRole = new UserRole();
        userRole.setRole(defaultRole);
        userRole.setUser(user);

        userRepository.save(user);
        userRoleRepository.save(userRole);

        return user;
    }

    private boolean emailExists(String email) {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    private boolean usernameExists(String username) {

        User user = userRepository.findByUsername(username);
        if (user != null) {
            return true;
        }
        return false;
    }
}
