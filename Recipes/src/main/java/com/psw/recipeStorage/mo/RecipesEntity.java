package com.psw.recipeStorage.mo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "recipes", schema = "RecipeDB", catalog = "")
public class RecipesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    @Basic
    @Column(name = "date_of_creation", nullable = true)
    private Timestamp dateOfCreation;
    @Basic
    @Column(name = "serving", nullable = false)
    private Integer serving;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "steps", nullable = false, length = -1)
    private String steps;
    @Basic
    @Column(name = "time", nullable = false)
    private Integer time;
    @Basic
    @Column(name = "difficulty", nullable = false)
    private Enum difficulty; //set to enum
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
    private Collection<IngredientsEntity> ingredientsById;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore //NB
    private UsersEntity usersByUserId;

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

    public Enum getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Enum difficulty) {
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
        RecipesEntity that = (RecipesEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(dateOfCreation, that.dateOfCreation) && Objects.equals(serving, that.serving) && Objects.equals(description, that.description) && Objects.equals(steps, that.steps) && Objects.equals(time, that.time) && Objects.equals(difficulty, that.difficulty) && Objects.equals(photo, that.photo) && Objects.equals(usersByUserId, that.usersByUserId); //modified
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, dateOfCreation, serving, description, steps, time, difficulty, photo, usersByUserId); //modified
    }

    public Collection<IngredientsEntity> getIngredientsById() {
        return ingredientsById;
    }

    public void setIngredientsById(Collection<IngredientsEntity> ingredientsById) {
        this.ingredientsById = ingredientsById;
    }

    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
