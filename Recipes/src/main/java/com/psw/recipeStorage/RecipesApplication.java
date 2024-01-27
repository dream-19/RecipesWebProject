package com.psw.recipeStorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipesApplication.class, args);
    }

}

/**
 * 1 step: Creo il progetto con spring initializer (with maven)
 *  aggiungo: spring web, spring data jpt (crea in automatico le classi dalle tabelle), mysql driver (collegamento al db)
 *
 */

