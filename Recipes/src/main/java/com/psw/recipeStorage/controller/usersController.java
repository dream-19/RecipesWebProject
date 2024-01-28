package com.psw.recipeStorage.controller;

/**
 * UsersController is a Spring MVC controller class that handles HTTP requests related to user management.
 * It acts as the entry point for handling web requests and serves as part of your application's RESTful API.
 * In the UsersController, you define request mappings that specify which URLs should trigger which methods.
 * Inside the controller methods, you call methods from the UserRepository to perform database operations related to users.
 * The controller's responsibility is to handle incoming requests, process data, interact with the repository
 * to fetch or modify user data, and return appropriate HTTP responses (e.g., JSON responses) to the client.
 */

import com.psw.recipeStorage.mo.UserEntity;
import com.psw.recipeStorage.mo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

//Validator
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import org.springframework.validation.Validator;

import java.util.List;

//REST CONTROLLER: creo il rest controller per la collection users
@RestController
@RequestMapping(value = "/recipeStorage") //path
public class usersController {

    private static final Logger log = LoggerFactory.getLogger(usersController.class); //logger

    //Usiamo Autowired per creare un'istanza dell'interfaccia in automatico
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    //GET sulla collection -> produce JSON
    @RequestMapping(value = "/users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<UserEntity> getUsers() {
        Iterable users=userRepository.findAll(); //chiamo findAll sull'istanza dell'interfaccia!
        return users;
    }

    //GET by id -> produce JSON
    @RequestMapping(value = "/users/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity getUserById(@PathVariable("id") Integer id) {
        UserEntity user=userRepository.findById(id);
        return user;
    }

    //UPDATE by id
    @RequestMapping(value = "/users/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus( HttpStatus.NO_CONTENT) //204 or 200
    @Transactional //operazione atomica
    //user contains the new information
    public void updateUserById(@PathVariable("id") Integer id, @RequestBody UserEntity user) {
        UserEntity userToUpdate=userRepository.findById(id); //find user to update

        //Update attributes that needs to be updated
        if (user.getName() != null) {
            userToUpdate.setName(user.getName());
        }
        if (user.getSurname() != null) {
            userToUpdate.setSurname(user.getSurname());
        }
        if (user.getNickname() != null) {
            userToUpdate.setNickname(user.getNickname());
        }
        if (user.getEmail() != null) {
            userToUpdate.setEmail(user.getEmail());
        }
        if (user.getDateOfBirth() != null) {
            userToUpdate.setDateOfBirth(user.getDateOfBirth());
        }

        // Hash the new password using SHA-256
        if (user.getHashPsw() != null) {
            log.info("I want to change the password to: " + user.getHashPsw());
            userToUpdate.setHashPsw(hashThePsw(user.getHashPsw()));
        }

        userRepository.save(userToUpdate);
        log.info("User updated successfully");
        return;
    }

    //CREATE new user
    @RequestMapping(value = "/users",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> addUser(@Valid @RequestBody List<UserEntity> users) {
        if (users.isEmpty()) {
            return ResponseEntity.badRequest().body("User list is empty");
        }
        for (UserEntity user : users) {
            // validate
            BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
            validator.validate(user, bindingResult);

            if (bindingResult.hasErrors()) {
                // Handle validation errors
                // You can return these errors or log them as required
                return ResponseEntity.badRequest().body("mary"+bindingResult.getAllErrors());
            }

            //Create
            user.setHashPsw(hashThePsw(user.getHashPsw())); // assuming you hash passwords
            userRepository.save(user); // saving each user
            log.info("user added successfully");
        }
        log.info("Users created successfully");

        return ResponseEntity.ok().body("All users are valid ");
    }

    //DELETE by id
    @RequestMapping(value = "/users/{id}",
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteUserById(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        log.info("User deleted successfully");
        return;
    }


    //DELETE all users
    @RequestMapping(value = "/users",
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
        log.info("All users deleted successfully");
        return;
    }

    //COUNT users
    @RequestMapping(value = "/users/count",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public long countUsers() {
        int count = userRepository.count();
        log.info("Users counted successfully");
        return count;
    }


    // Helper method to convert bytes to hexadecimal string
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String hashThePsw(String password){
        String hashedPassword = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashedPassword = bytesToHex(encodedHash); // Convert bytes to hexadecimal string
            log.info("hash of the psw: " + hashedPassword);

        } catch (NoSuchAlgorithmException e) {
            log.info("Not able to hash the password");
        }
        return hashedPassword;
    }


}
