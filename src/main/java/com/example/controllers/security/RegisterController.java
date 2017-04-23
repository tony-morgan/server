package com.example.controllers.security;

import com.example.security.exceptions.EmailExistsException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.security.exceptions.UsernameExistsException;
import com.example.security.models.User;
import com.example.security.services.UserRegistrationServiceSupport;

import javax.validation.Valid;

/**
 * Created by an on 20.04.2017.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRegistrationServiceSupport userRegistrationServiceSupport;

    @RequestMapping(value = "/register",
            produces = {"application/json"},
            consumes = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<String> registerNewUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {

        JSONObject responseObject = new JSONObject();
        if (bindingResult.hasErrors()) {

            JSONArray errorsArray = new JSONArray();
            for (FieldError fe : bindingResult.getFieldErrors()) {

                logger.trace(fe.toString());
                JSONObject errorObject = new JSONObject();
                errorObject.put(fe.getField(), fe.getDefaultMessage());
                errorsArray.put(errorObject);
            }

            responseObject.put("fieldErrors", errorsArray);

            return new ResponseEntity<>(responseObject.toString(), HttpStatus.BAD_REQUEST);
        }

        User registeredUser;
        try {
            registeredUser = userRegistrationServiceSupport.registerNewUserAccount(userDTO);
        } catch (EmailExistsException | UsernameExistsException e) {

            logger.error(e.toString());
            responseObject.put("registrationError", e.getMessage());
            return new ResponseEntity<>(responseObject.toString(), HttpStatus.BAD_REQUEST);
        }

        responseObject.put("message", "User created !");
        responseObject.put("username", registeredUser.getUsername());
        responseObject.put("email", registeredUser.getEmail());

        return new ResponseEntity<>(responseObject.toString(), HttpStatus.OK);
    }

}
