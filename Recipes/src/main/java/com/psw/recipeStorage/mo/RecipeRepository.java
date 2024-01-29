package com.psw.recipeStorage.mo;
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

    Iterable<RecipeEntity> findByUsersByUserId_Id(Integer id);


    Iterable<RecipeEntity> findByDifficulty(Difficulty difficulty);

    RecipeEntity findByUsersByUserId_IdAndId(Integer userId, Integer id); //seguo il nome che gli ho dato nell'entity

}
