package com.psw.recipeStorage.mo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ingredients", schema = "RecipeDB", catalog = "")
public class IngredientEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @NotNull(message = "Name field is required")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @NotNull(message = "Quantity field is required")
    @Column(name = "quantity", nullable = false, precision = 2)
    private BigDecimal quantity;
    @Basic
    @NotNull(message = "Unit of measurement field is required")
    @Size(min = 1, max = 50, message = "Unit of measurement must be between 1 and 50 characters")
    @Column(name = "unit_of_measurement", nullable = false, length = 50)
    private String unitOfMeasurement;

    /*@Basic //duplication!
    @Column(name = "recipe_id", nullable = true)
    private Integer recipeId;*/

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    @JsonIgnore //NB
    private RecipeEntity recipesByRecipeId;

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
        IngredientEntity that = (IngredientEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(quantity, that.quantity) && Objects.equals(unitOfMeasurement, that.unitOfMeasurement) && Objects.equals(recipesByRecipeId, that.recipesByRecipeId); //modified
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, unitOfMeasurement, recipesByRecipeId); //modified
    }

    public RecipeEntity getRecipesByRecipeId() {
        return recipesByRecipeId;
    }

    public void setRecipesByRecipeId(RecipeEntity recipesByRecipeId) {
        this.recipesByRecipeId = recipesByRecipeId;
    }
}
