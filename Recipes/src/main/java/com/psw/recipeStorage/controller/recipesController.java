package com.psw.recipeStorage.controller;

import com.psw.recipeStorage.mo.Difficulty;
import com.psw.recipeStorage.mo.RecipeEntity;
import com.psw.recipeStorage.mo.RecipeRepository;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

//Validator
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/recipeStorage") //path
public class recipesController {
    private static final Logger log = LoggerFactory.getLogger(recipesController.class); //logger

    //Usiamo Autowired per creare un'istanza dell'interfaccia in automatico
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

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

    //POST new recipe (with user id)
    @RequestMapping(value = "/recipes",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus( HttpStatus.CREATED) //201
    @Transactional
    public ResponseEntity<?> createRecipe(@Valid @RequestBody List<RecipeEntity> recipes, BindingResult bindingResult) {

        //Create all the recipes
        for (RecipeEntity recipe : recipes) {
            //check if user exists
            if (recipe.getUsersByUserId() == null || userRepository.findById(recipe.getUsersByUserId().getId()) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
            }

            //validate
            validator.validate(recipe, bindingResult);
            if (bindingResult.hasErrors()) {
                for (FieldError fieldError : bindingResult.getFieldErrors())
                    return ResponseEntity.badRequest().body(fieldError.getDefaultMessage());
            }

            //save
            recipeRepository.save(recipe);

        }
        return ResponseEntity.ok("Recipes created");
    }

    //PUT: update recipe by id
    @RequestMapping(value = "/recipes/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus( HttpStatus.NO_CONTENT) //204 or 200
    @Transactional //operazione atomica
    public ResponseEntity<?> updateRecipesById(@PathVariable("id") Integer id, @Valid @RequestBody RecipeEntity recipe, BindingResult bindingResult) {
        RecipeEntity recipeToUpdate=recipeRepository.findById(id); //find recipe to update
        if (recipeToUpdate == null) {
            return ResponseEntity.badRequest().body("Recipe not found");
        }
        //validate
        validator.validate(recipe, bindingResult);
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors())
                if (!fieldError.getCode().equals("NotNull"))  //ignore not null error (they are not an error)
                    return ResponseEntity.badRequest().body(fieldError.getDefaultMessage());
        }


        //Update attributes that needs to be updated
        if (recipe.getTitle() != null) {
            recipeToUpdate.setTitle(recipe.getTitle());
        }
        if (recipe.getDateOfCreation() != null) {
            recipeToUpdate.setDateOfCreation(recipe.getDateOfCreation());
        }
        if (recipe.getServing() != null) {
            recipeToUpdate.setServing(recipe.getServing());
        }
        if (recipe.getDescription() != null) {
            recipeToUpdate.setDescription(recipe.getDescription());
        }
        if (recipe.getDifficulty() != null) {
            recipeToUpdate.setDifficulty(recipe.getDifficulty());
        }
        if (recipe.getPhoto() != null) {
            recipeToUpdate.setPhoto(recipe.getPhoto());
        }
        if (recipe.getSteps() != null) {
            recipeToUpdate.setSteps(recipe.getSteps());
        }
        if (recipe.getTime() != null) {
            recipeToUpdate.setTime(recipe.getTime());
        }

        recipeRepository.save(recipeToUpdate);
        log.info("Recipe updated successfully");
        return ResponseEntity.ok().body("Recipe updated successfully");
    }

    //DELETE recipe by id
    @RequestMapping(value = "/recipes/{id}",
            method = RequestMethod.DELETE)
    @ResponseStatus( HttpStatus.NO_CONTENT) //204 or 200
    @Transactional //operazione atomica
    public void deleteRecipeById(@PathVariable("id") Integer id) {
        recipeRepository.deleteById(id);
    }

    //DELETE ALL recipes
    @RequestMapping(value = "/recipes",
            method = RequestMethod.DELETE)
    @ResponseStatus( HttpStatus.NO_CONTENT) //204 or 200
    @Transactional //operazione atomica
    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }

    //DELETE all recipes of an user
    @RequestMapping(value = "/recipes/user/{id}",
            method = RequestMethod.DELETE)
    @ResponseStatus( HttpStatus.NO_CONTENT) //204 or 200
    @Transactional //operazione atomica
    public void deleteAllRecipesOfUser(@PathVariable("id") Integer id) {
        Iterable<RecipeEntity> recipes=recipeRepository.findByUsersByUserId_Id(id);
        for (RecipeEntity recipe : recipes) {
            recipeRepository.deleteById(recipe.getId());
        }
    }

    //COUNT recipes
    @RequestMapping(value = "/recipes/count",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public long countRecipes() {
        int count = recipeRepository.count();
        log.info("Recipes counted successfully");
        return count;
    }

    //count recipes of an user
    @RequestMapping(value = "/recipes/user/{id}/count",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public long countRecipesOfUser(@PathVariable("id") Integer id) {
        Iterable<RecipeEntity> recipes=recipeRepository.findByUsersByUserId_Id(id);
        int count=0;
        for (RecipeEntity recipe : recipes) {
            count++;
        }
        log.info("Recipes counted successfully");
        return count;
    }



}
