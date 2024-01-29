package com.psw.recipeStorage.controller;

import com.psw.recipeStorage.mo.Difficulty;
import com.psw.recipeStorage.mo.RecipeEntity;
import com.psw.recipeStorage.mo.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

//Validator
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import org.springframework.validation.Validator;

import java.util.List;

@RestController
@RequestMapping(value = "/recipeStorage") //path
public class recipesController {
    private static final Logger log = LoggerFactory.getLogger(recipesController.class); //logger

    //Usiamo Autowired per creare un'istanza dell'interfaccia in automatico
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private Validator validator;

    //GET on collection (take all the recipes)
    @RequestMapping(value = "/recipes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<RecipeEntity> getRecipes() {
        Iterable recipes=recipeRepository.findAll();
        return recipes;
    }

    //GET recipe by id
    @RequestMapping(value = "/recipes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RecipeEntity getRecipeById(@PathVariable("id") Integer id) {
        RecipeEntity recipe=recipeRepository.findById(id);
        return recipe;
    }

    //GET by title
    @RequestMapping(value = "/recipes/title/{title}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RecipeEntity getRecipeByTitle(@PathVariable("title") String title) {
        RecipeEntity recipe=recipeRepository.findByTitle(title);
        return recipe;
    }

    //Get by user id
    @RequestMapping(value = "/recipes/user/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<RecipeEntity> getRecipeByUserId(@PathVariable("id") Integer id) {
        Iterable<RecipeEntity> recipes=recipeRepository.findByUsersByUserId_Id(id);
        return recipes;
    }

    //GET by user id and recipe id
    @RequestMapping(value = "/recipes/user/{id}/recipe/{recipeId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RecipeEntity getRecipeByUserIdAndRecipeId(@PathVariable("id") Integer id, @PathVariable("recipeId") Integer recipeId) {
        RecipeEntity recipe=recipeRepository.findByUsersByUserId_IdAndId(id, recipeId);
        return recipe;
    }

    //GET by difficulty
    @RequestMapping(value = "/recipes/difficulty/{difficulty}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<RecipeEntity> getRecipeByDifficulty(@PathVariable("difficulty") String difficulty) {
        //check if difficulty is correct
        if(!difficulty.equals("Easy") && !difficulty.equals("Medium") && !difficulty.equals("Hard")){
            return null;
        }
        Iterable<RecipeEntity> recipes=recipeRepository.findByDifficulty(Difficulty.valueOf(difficulty));
        return recipes;
    }




}
