package com.psw.recipeStorage.mo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;


@Entity
@Table(name = "recipes", schema = "RecipeDB", catalog = "")
public class RecipeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @NotNull(message = "Title field is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    @Basic
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_creation", nullable = true)
    private Timestamp dateOfCreation;
    @Basic
    @NotNull(message = "Serving field is required")
    @Column(name = "serving", nullable = false)
    private Integer serving;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @NotNull(message = "Steps field is required")
    @Column(name = "steps", nullable = false, length = -1)
    private String steps;
    @Basic
    @NotNull(message = "Time field is required")
    @Column(name = "time", nullable = false)
    private Integer time;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Difficulty field is required")
    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty; //enum value
    @Basic
    @Column(name = "photo", nullable = true, length = 255)
    private String photo;

    /*
    @Basic //DUPLICATED
    @Column(name = "user_id", nullable = true)
    private Integer userId;
    */

    @OneToMany(mappedBy = "recipesByRecipeId")
    @JsonIgnore //NB
    private Collection<IngredientEntity> ingredientsById;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore //NB
    private UserEntity usersByUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Timestamp dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Integer getServing() {
        return serving;
    }

    public void setServing(Integer serving) {
        this.serving = serving;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /*public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeEntity that = (RecipeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(dateOfCreation, that.dateOfCreation) && Objects.equals(serving, that.serving) && Objects.equals(description, that.description) && Objects.equals(steps, that.steps) && Objects.equals(time, that.time) && Objects.equals(difficulty, that.difficulty) && Objects.equals(photo, that.photo) && Objects.equals(usersByUserId, that.usersByUserId); //modified
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, dateOfCreation, serving, description, steps, time, difficulty, photo, usersByUserId); //modified
    }

    public Collection<IngredientEntity> getIngredientsById() {
        return ingredientsById;
    }

    public void setIngredientsById(Collection<IngredientEntity> ingredientsById) {
        this.ingredientsById = ingredientsById;
    }

    public UserEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UserEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
