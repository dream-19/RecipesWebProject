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
 *          - nb: inserisco JsonIgnore per evitare loop infiniti nelle relazioni!
 *
 *
 *          --------------------------------------------------
 *
 *  3) Configuriamo springboot per vedere il datasource (per ora lo vede solo IDE)
 *  resources > application.properties (qui si mettono tutte le configurazioni di springboot)
 *  inserisco le configurazioni dell'esercizio 09 (aggiornando con le mie credenziali)
 *
 *  4) Creiamo da (data access object) -> qui si chiama REPOSITORY (= dove faccio le query)
 *  - creo il package controller
 *  - copio dall'es 09 nella cartella mo i due repository
 *  - customer order repository: creo una query a hoc per marcare gli ordini (usa il linguaggio HQL)
 *
 *
 *  5) Creiamo il REST CONTROLLER (qui ho le chiamate HTTP che mi servono)
 *  	-> dentro il package controller (copio sempre da 09) -> ordersdbRequests.http
 *   	-> attenzione al metodo non crud
 *
 *
 *
 *  6) Creo httpRequest ed escludo (per fare i test)
 *   - dentro aggiungo ordersdbRequests.http (con i metodi da usare)
 *   - posso provarli poi es: http://localhost:8080/sistema1/orders o runnale dal file di httprequest
 *   - org.apache.http.ConnectionClosedException: Premature end of chunk coded message body: closing chunk expected
 *   	si crea un loop infinito (oggetto - array), risolto dal JsonIgnore
 *
 */

