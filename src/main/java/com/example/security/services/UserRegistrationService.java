package com.example.security.services;

import com.example.controllers.security.UserDTO;
import com.example.security.exceptions.EmailExistsException;
import com.example.security.exceptions.UsernameExistsException;
import com.example.security.models.User;

/**
 * Created by an on 20.04.2017.
 */
public interface UserRegistrationService {

    User registerNewUserAccount(UserDTO userDTO) throws EmailExistsException, UsernameExistsException;
}
