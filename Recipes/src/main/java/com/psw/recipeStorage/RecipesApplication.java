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
 *      aggiungo: spring web, spring data jpt (crea in automatico le classi dalle tabelle), mysql driver (collegamento al db)
 *
 *  2 step: collego il database: usando la sezione 'database' sulla dx
 *        + > datasource > mysql > inserisco i dati della connessione alla tabella ( su host è localhost, inserisco il mio username e psw)
 *        Poi seleziono il db da usare (RecipeDB) e testo la connessione per verificare che funzioni
 *
 * 3 step: Creo il Model Object (MO) ovvero creiamo gli oggetti a partire dal db
 *
 *  * 2) MO model object: Creo in automatico gli oggetti a partire dal db
 *  * new package su com.psw.sistem1 > 'mo' (qui dentro creo gli oggetti JPA)
 *  * view > tool windows > persistence (creo qui dentro gli oggetti persistenti)
 *  * sistema1 tasto dx> assign data sources > ordersdb
 *  * sistema1 tasto dx > generate persistence mappin > schema > screen per le impostazioni
 *  *  - crea dentro mo curstomerorderentity e customerorderrowentity (nb: a JsonIgnore)
 */

