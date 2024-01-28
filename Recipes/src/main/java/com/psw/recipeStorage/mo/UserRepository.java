package com.psw.recipeStorage.mo;

/**
 * UserRepository is an interface that extends Spring Data JPA's repository interfaces like CrudRepository or PagingAndSortingRepository.
 * It acts as a data access layer for the UserEntity and provides methods for interacting with user data in the database.
 * This interface defines methods for common database operations like saving, finding, updating, or deleting user records.
 * You can also define custom query methods if needed.
 * Spring Data JPA generates the implementation of these methods at runtime based on the method names,
 * allowing you to work with user data without writing SQL queries explicitly.
 * You use UserRepository to perform database operations on UserEntity.
 * For example, you can use it to save a new user, find users by email, or retrieve all users.
 */

import org.apache.catalina.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

//Normalmente estendo CRUD ma con paging permetto anche di paginare i risultati
//Usiamo l'interfaccia per definire i metodi tramite naming convention, poi è Spring Boot che si occupa di creare le query
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Integer> {
    List<UserEntity> findAll(); //scrivo solo questo, la query è creata da springboot (per qualunque database)
    UserEntity findById(Integer id); //trovo con id

    void save(UserEntity userToUpdate);

    void deleteById(Integer id);

    void deleteAll();

    int count();


    UserEntity findByEmail(String email);

    UserEntity findByNickname(String nickname);
}
