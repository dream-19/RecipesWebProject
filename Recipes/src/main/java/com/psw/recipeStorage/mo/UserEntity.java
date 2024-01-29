package com.psw.recipeStorage.mo;

/**
 * UserEntity is a Java class that represents the entity or model for users in your application.
 * It typically maps to a database table. This class contains fields that represent the properties of a user
 * An instance of UserEntity represents a single user's data.
 * You use UserEntity to define the structure of the users table in your database and
 * to create, read, update, or delete user records in the database.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "RecipeDB", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @NotNull(message = "Name field is required")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Column(name = "name", nullable = false, length = 255) //this is a constraint at database level
    private String name;
    @Basic
    @NotNull(message = "Surname field is required")
    @Size(min = 1, max = 255, message = "Surname must be between 1 and 255 characters")
    @Column(name = "surname", nullable = false, length = 255)
    private String surname;
    @Basic
    @NotNull(message = "Nickname field is required")
    @Size(min = 1, max = 255, message = "Nickname must be between 1 and 255 characters")
    @Column(name = "nickname", nullable = false, length = 255, unique = true)
    private String nickname;
    @Basic
    @Email(message = "Email must be valid")
    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;
    @Basic
    @NotNull(message = "Date of birth field is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;
    @Basic
    @NotNull(message = "Password field is required")
    @Size(min = 1, max = 64, message = "Password must be between 1 and 64 characters")
    @Column(name = "hash_psw", nullable = false, length = 64)
    private String hashPsw;
    @OneToMany(mappedBy = "usersByUserId")
    @JsonIgnore //NB
    private Collection<RecipeEntity> recipesById;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHashPsw() {
        return hashPsw;
    }

    public void setHashPsw(String hashPsw) {
        this.hashPsw = hashPsw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(nickname, that.nickname) && Objects.equals(email, that.email) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(hashPsw, that.hashPsw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, nickname, email, dateOfBirth, hashPsw);
    }

    public Collection<RecipeEntity> getRecipesById() {
        return recipesById;
    }

    public void setRecipesById(Collection<RecipeEntity> recipesById) {
        this.recipesById = recipesById;
    }
}
