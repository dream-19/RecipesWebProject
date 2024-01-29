package com.psw.recipesstorage.mo;
/* Interfaccia per definire le query */
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecipeRepository extends PagingAndSortingRepository<RecipeEntity, Integer> {
    List<RecipeEntity> findAll(); //scrivo solo questo, la query è creata da springboot (per qualunque database)
    RecipeEntity findById(Integer id); //trovo con id

    void save(RecipeEntity recipeToUpdate);

    void deleteById(Integer id);

    void deleteAll();

    int count();

    RecipeEntity findByTitle(String title);

    Iterable<RecipeEntity> findByDifficulty(Difficulty difficulty);


}
