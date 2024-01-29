package com.psw.recipesstorage.mo;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "steps", schema = "RecipeDB", catalog = "")
public class StepEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @NotNull(message = "Step number field is required")
    @Column(name = "step_number", nullable = false)
    private Integer stepNumber;
    @Basic
    @NotNull(message = "Title field is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    @Basic
    @NotNull(message = "Description field is required")
    @Size(min = 1, max = 512, message = "Description must be between 1 and 255 characters")
    @Column(name = "description", nullable = false, length = 512)
    private String description;
    /*@Basic
    @Column(name = "recipe_id", nullable = true)
    private Integer recipeId;*/
    @ManyToOne
    @JsonIgnore //NB
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private RecipeEntity recipesByRecipeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(Integer stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepEntity that = (StepEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(stepNumber, that.stepNumber) && Objects.equals(title, that.title) && Objects.equals(description, that.description) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stepNumber, title, description);
    }

    public RecipeEntity getRecipesByRecipeId() {
        return recipesByRecipeId;
    }

    public void setRecipesByRecipeId(RecipeEntity recipesByRecipeId) {
        this.recipesByRecipeId = recipesByRecipeId;
    }
}
