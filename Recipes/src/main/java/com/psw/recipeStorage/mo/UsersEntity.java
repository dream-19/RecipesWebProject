package com.psw.recipeStorage.mo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "RecipeDB", catalog = "")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "surname", nullable = false, length = 255)
    private String surname;
    @Basic
    @Column(name = "nickname", nullable = false, length = 255)
    private String nickname;
    @Basic
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Basic
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;
    @Basic
    @Column(name = "hash_psw", nullable = false, length = 255)
    private String hashPsw;
    @OneToMany(mappedBy = "usersByUserId")
    @JsonIgnore //NB
    private Collection<RecipesEntity> recipesById;

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
        UsersEntity that = (UsersEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(nickname, that.nickname) && Objects.equals(email, that.email) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(hashPsw, that.hashPsw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, nickname, email, dateOfBirth, hashPsw);
    }

    public Collection<RecipesEntity> getRecipesById() {
        return recipesById;
    }

    public void setRecipesById(Collection<RecipesEntity> recipesById) {
        this.recipesById = recipesById;
    }
}
