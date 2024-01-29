package com.psw.recipesstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipesStorageApplication.class, args);
    }

}
/**
 * 1 step: Creo il progetto con spring initializer (with maven)
 *      aggiungo: spring web, spring data jpa (crea in automatico le classi dalle tabelle), mysql driver (collegamento al db)
 *
 *  2 step: collego il database: usando la sezione 'database' sulla dx
 *        + > datasource > mysql > inserisco i dati della connessione alla tabella ( su host è localhost, inserisco il mio username e psw)
 *        Poi seleziono il db da usare (RecipeDB) e testo la connessione per verificare che funzioni
 *
 * 3 step: Creo il Model Object (MO) ovvero creiamo gli oggetti a partire dal db (uso JPA)
 *      new package su com.psw.recipeStorage > 'mo' (qui dentro vengono create le entità)
 *      view > tool windows > persistence (aggiunge la sezione per la creazione di oggetti persistenti)
 *      recipeStorage tasto dx> assign data sources > RecipeDB
 *      Per la creazione effettiva delle entità: 'generate persistence mapping'
 *          - ricordarsi di selezionare il package giusto (mo) e selezionando tutte le entità e le colonne di relazione!
 *          - nb: inserisco JsonIgnore per evitare loop infiniti nelle relazioni ManyToOne (non nelle OneToMany)!
 *          - nb: elimino gli attributi che risultano duplicati (es: recipeId) (ho modificato anche due funzioni equal e hash)
 *
 *  4 step: Inseriamo le configurazioni per vedere effettivamente il db (uso mysql driver)
 *         - resources > application.properties > inserisco le configurazione (copia da altri esercizi)
 *         - a questo punto posso fare il primo run per vedere se ci sono errori
 *
 *  5 step: creiamo il data access object (repository) per fare le query (model: interazione con il db)
 *        - creo le interfacce: StepsRepository, RecipesRepository, IngredientsRepository (dentro mo):
 *          qui dentro andiamo a definire le query (usando il linguaggio HQL)
 *
 *  6 step: Creiamo il REST CONTROLLER (qui ho le chiamate HTTP che mi servono)
 *       - creo il package controller (dentro recipeStorage)
 *       - creo i controller: StepsController, RecipesController, IngredientsController con i vari metodi crud (o anche aggiuntivi)
 *
 *  7 step: Creo httpRequest per fare il testing delle chiamate
 *       - creo la cartella httpRequests in Recipes
 *          - tasto dx > mark directory as > excluded (per non farla vedere a springboot)
 *       - creo il file: recipeStorageRequests.http (con i metodi da testare)
 *       - Posso testarli usando i vari link (localhost:8080/recipeStorage/...) o fare run direttamente dal httprequest
 */
