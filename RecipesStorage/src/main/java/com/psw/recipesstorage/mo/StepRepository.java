package com.psw.recipesstorage.mo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StepRepository extends PagingAndSortingRepository<StepEntity, Integer> {
    List<StepEntity> findAll();
    StepEntity findById(Integer id);

    void save(StepEntity stepToUpdate);

    void deleteById(Integer id);

    void deleteAll();

    int count();

    Iterable<StepEntity> findByRecipesByRecipeId_Id(Integer id);

    void deleteByRecipesByRecipeId_Id(Integer recipeId);

    int countByRecipesByRecipeId_Id(Integer recipeId);
}
