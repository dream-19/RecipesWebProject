package com.psw.recipesstorage.controller;

import com.psw.recipesstorage.mo.*;
import com.psw.recipesstorage.mo.IngredientEntity;
import com.psw.recipesstorage.mo.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/recipeStorage") //path
public class ingredientsController {
    private static final Logger log = LoggerFactory.getLogger(ingredientsController.class); //logger

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private Validator validator;

    //GET on collection (take all the ingredients)
    @RequestMapping(value = "/ingredients",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<IngredientEntity> getIngredients() {
        Iterable ingredients=ingredientRepository.findAll();
        return ingredients;
    }

    //GET ingredients of a recipe
    @RequestMapping(value = "/recipes/{recipe_id}/ingredients", //recipesByRecipeId
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<IngredientEntity> getIngredientsOfRecipe(@PathVariable("recipe_id") Integer recipe_id) {
        Iterable ingredients=ingredientRepository.findByRecipesByRecipeId_Id(recipe_id);
        return ingredients;
    }

    //POST new ingredient for a recipe
    @RequestMapping(value = "/recipes/{recipe_id}/ingredients",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<?> createIngredient(@PathVariable("recipe_id") Integer recipe_id, @Valid @RequestBody List<IngredientEntity> ingredients, BindingResult bindingResult) {
        //check if recipe exists
        RecipeEntity recipe = recipeRepository.findById(recipe_id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        //Check if I am the owner of the recipe (?)
        for(IngredientEntity ingredient: ingredients) {
            //validate
            validator.validate(ingredient, bindingResult);
            if (bindingResult.hasErrors()) {
                for (FieldError fieldError : bindingResult.getFieldErrors())
                    return ResponseEntity.badRequest().body(fieldError.getDefaultMessage());
            }
            ingredient.setRecipesByRecipeId(recipe);
            ingredientRepository.save(ingredient);
        }

        return ResponseEntity.ok("ingredients created successfully");
    }

    //PUT: update ingredient by id
    @RequestMapping(value = "/ingredients/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> updateIngredient(@PathVariable("id") Integer id, @Valid @RequestBody IngredientEntity ingredient, BindingResult bindingResult) {
        //check if ingredient exists
        IngredientEntity ingredientToUpdate = ingredientRepository.findById(id);
        if (ingredientToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        validator.validate(ingredient, bindingResult);
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors())
                //ignore notnull errors
                if (!fieldError.getCode().equals("NotNull"))
                    return ResponseEntity.badRequest().body(fieldError.getDefaultMessage());
        }

        //update ingredient if present
        if (ingredient.getName() != null) {
            ingredientToUpdate.setName(ingredient.getName());
        }
        if (ingredient.getQuantity() != null) {
            ingredientToUpdate.setQuantity(ingredient.getQuantity());
        }
        if (ingredient.getUnitOfMeasurement() != null) {
            ingredientToUpdate.setUnitOfMeasurement(ingredient.getUnitOfMeasurement());
        }
        ingredientRepository.save(ingredientToUpdate);
        return ResponseEntity.ok("ingredient updated successfully");
    }

    //delete ingredient by id
    @RequestMapping(value = "/ingredients/{id}",
            method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<?> deleteIngredient(@PathVariable("id") Integer id) {
        //check if ingredient exists
        IngredientEntity ingredientToDelete = ingredientRepository.findById(id);
        if (ingredientToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        ingredientRepository.deleteById(id);
        return ResponseEntity.ok("ingredient deleted successfully");
    }

    //delete all ingredients
    @RequestMapping(value = "/ingredients",
            method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<?> deleteAllIngredients() {
        ingredientRepository.deleteAll();
        return ResponseEntity.ok("ingredients deleted successfully");
    }

    //delete all ingredients of a recipe
    @RequestMapping(value = "/recipes/{recipe_id}/ingredients",
            method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<?> deleteAllIngredientsOfRecipe(@PathVariable("recipe_id") Integer recipe_id) {
        //check if recipe exists
        RecipeEntity recipe = recipeRepository.findById(recipe_id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        //find all ingredients of a recipe
        Iterable<IngredientEntity> ingredients = ingredientRepository.findByRecipesByRecipeId_Id(recipe_id);
        for(IngredientEntity ingredient: ingredients) {
            ingredientRepository.deleteById(ingredient.getId());
        }
        return ResponseEntity.ok("ingredients deleted successfully");
    }

    //count ingredients of a recipe
    @RequestMapping(value = "/recipes/{recipe_id}/ingredients/count",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public int countIngredientsOfRecipe(@PathVariable("recipe_id") Integer recipe_id) {
        //find all ingredients of a recipe
        Iterable<IngredientEntity> ingredients = ingredientRepository.findByRecipesByRecipeId_Id(recipe_id);
        int count=0;
        for(IngredientEntity ingredient: ingredients) {
            count++;
        }
        return count;
    }

}
