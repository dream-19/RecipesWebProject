package com.psw.recipeStorage.mo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ingredients", schema = "RecipeDB", catalog = "")
public class IngredientsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "quantity", nullable = false, precision = 2)
    private BigDecimal quantity;
    @Basic
    @Column(name = "unit_of_measurement", nullable = false, length = 50)
    private String unitOfMeasurement;

    /*@Basic //duplication!
    @Column(name = "recipe_id", nullable = true)
    private Integer recipeId;*/

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    @JsonIgnore //NB
    private RecipesEntity recipesByRecipeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    // DUPLICATED
    /* public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    } */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientsEntity that = (IngredientsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(quantity, that.quantity) && Objects.equals(unitOfMeasurement, that.unitOfMeasurement) && Objects.equals(recipesByRecipeId, that.recipesByRecipeId); //modified
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, unitOfMeasurement, recipesByRecipeId); //modified
    }

    public RecipesEntity getRecipesByRecipeId() {
        return recipesByRecipeId;
    }

    public void setRecipesByRecipeId(RecipesEntity recipesByRecipeId) {
        this.recipesByRecipeId = recipesByRecipeId;
    }
}
