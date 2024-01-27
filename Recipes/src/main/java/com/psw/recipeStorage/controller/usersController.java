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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

//REST CONTROLLER: creo il rest controller per la collection users
@RestController
@RequestMapping(value = "/recipeStorage") //path

public class usersController {

    private static final Logger log = LoggerFactory.getLogger(usersController.class); //logger

    //Usiamo Autowired per creare un'istanza dell'interfaccia in automatico
    @Autowired
    private UserRepository userRepository;

    //GET sulla collection -> produce JSON
    @RequestMapping(value = "/users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<UserEntity> getUsers() {
        Iterable users=userRepository.findAll(); //chiamo findAll sull'istanza dell'interfaccia!
        return users;
    }

}
