package com.psw.recipesstorage.mo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IngredientRepository extends PagingAndSortingRepository<IngredientEntity, Integer> {
    List<IngredientEntity> findAll();
    IngredientEntity findById(Integer id);

    void save(IngredientEntity ingredientToUpdate);

    void deleteById(Integer id);

    void deleteAll();

    int count();

    Iterable<IngredientEntity> findByRecipesByRecipeId_Id(Integer id);

}
