package com.psw.recipesstorage.controller;

import com.psw.recipesstorage.mo.*;
import com.psw.recipesstorage.mo.StepEntity;
import com.psw.recipesstorage.mo.StepRepository;
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
public class stepsController {
    private static final Logger log = LoggerFactory.getLogger(stepsController.class); //logger

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private Validator validator;

    //GET on collection
    @RequestMapping(value = "/steps",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<StepEntity> getSteps() {
        Iterable steps=stepRepository.findAll();
        return steps;
    }

    //GET steps of a recipe
    @RequestMapping(value = "/recipes/{recipe_id}/steps", //recipesByRecipeId
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<StepEntity> getStepsOfRecipe(@PathVariable("recipe_id") Integer recipe_id) {
        Iterable steps=stepRepository.findByRecipesByRecipeId_Id(recipe_id);
        return steps;
    }

    //POST new steps for a recipe
    @RequestMapping(value = "/recipes/{recipe_id}/steps",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<?> createStep(@PathVariable("recipe_id") Integer recipe_id, @Valid @RequestBody List<StepEntity> steps, BindingResult bindingResult) {
        //check if recipe exists
        RecipeEntity recipe = recipeRepository.findById(recipe_id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        for(StepEntity step: steps) {
            //validate
            validator.validate(step, bindingResult);
            if (bindingResult.hasErrors()) {
                for (FieldError fieldError : bindingResult.getFieldErrors())
                    return ResponseEntity.badRequest().body(fieldError.getDefaultMessage());
            }
            step.setRecipesByRecipeId(recipe);
            //check if the same step is already present in the recipe (can't have the same step_number)
            if (stepRepository.findByRecipesByRecipeId_IdAndStepNumber(recipe_id, step.getStepNumber()) != null) {
                return ResponseEntity.badRequest().body("Step number " + step.getStepNumber() + " already present in the recipe");
            }
            stepRepository.save(step);
        }

        return ResponseEntity.ok("steps created successfully");
    }

    //PUT: update step by id
    @RequestMapping(value = "/steps/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> updateStep(@PathVariable("id") Integer id, @Valid @RequestBody StepEntity step, BindingResult bindingResult) {
        //check if step exists
        StepEntity stepToUpdate = stepRepository.findById(id);
        if (stepToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        validator.validate(step, bindingResult);
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors())
                //ignore notnull errors
                if (!fieldError.getCode().equals("NotNull"))
                    return ResponseEntity.badRequest().body(fieldError.getDefaultMessage());
        }

        //update step if present
        if (step.getStepNumber() != null)
            //check if we don't have already the same step number (and is not the same step)
            if (stepRepository.findByRecipesByRecipeId_IdAndStepNumber(stepToUpdate.getRecipesByRecipeId().getId(), step.getStepNumber()) != null && stepRepository.findByRecipesByRecipeId_IdAndStepNumber(stepToUpdate.getRecipesByRecipeId().getId(), step.getStepNumber()).getId() != stepToUpdate.getId()){
                return ResponseEntity.badRequest().body("Step number " + step.getStepNumber() + " already present in the recipe");
            }
            else
            stepToUpdate.setStepNumber(step.getStepNumber());
        if (step.getDescription() != null)
            stepToUpdate.setDescription(step.getDescription());
        if (step.getTitle() != null)
            stepToUpdate.setTitle(step.getTitle());

        stepRepository.save(stepToUpdate);
        return ResponseEntity.ok("step updated successfully");
    }

    //DELETE a step
    @RequestMapping(value = "/steps/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> deleteStep(@PathVariable("id") Integer id) {
        //check if step exists
        StepEntity stepToDelete = stepRepository.findById(id);
        if (stepToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        //delete by id
        stepRepository.deleteById(id);
        return ResponseEntity.ok("step deleted successfully");
    }

    //DELETE all steps of a recipe
    @RequestMapping(value = "/recipes/{recipe_id}/steps",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> deleteStepsOfRecipe(@PathVariable("recipe_id") Integer recipe_id) {
        //check if recipe exists
        RecipeEntity recipe = recipeRepository.findById(recipe_id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        //delete by recipe_id
        stepRepository.deleteByRecipesByRecipeId_Id(recipe_id);
        return ResponseEntity.ok("steps deleted successfully");
    }

    //DELETE all steps
    @RequestMapping(value = "/steps",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> deleteAllSteps() {
        stepRepository.deleteAll();
        return ResponseEntity.ok("steps deleted successfully");
    }

    //COUNT steps of a recipe
    @RequestMapping(value = "/recipes/{recipe_id}/steps/count",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public int countStepsOfRecipe(@PathVariable("recipe_id") Integer recipe_id) {
        //check if recipe exists
        RecipeEntity recipe = recipeRepository.findById(recipe_id);
        if (recipe == null) {
            return 0;
        }
        int count = stepRepository.countByRecipesByRecipeId_Id(recipe_id);
        log.info("Steps counted successfully");
        return count;
    }

    //COUNT all steps
    @RequestMapping(value = "/steps/count",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public int countSteps() {
        int count = stepRepository.count();
        log.info("Steps counted successfully");
        return count;
    }



}
