# RecipesWebProject
A Simple Web Service to create and save recipes. Made using SpringBoot for the backend and Angular for the frontend

# Database
The database is written in MySQL and is formed by three tables:
- recipes with attributes:
    * id (primary key)
    * title
    * date_of_creation (datetime)
    * serving
    * description (nullable)
    * time
    * difficulty (enum)
    * photo (nullable, LONGTEXT to save the base64 string of the image)
- ingredients with attributes:
    * id (primary key)
    * name
    * quantity
    * unit_of_measurement
    * recipe_id (foreign key, relationship N:1 with recipes)
- steps with attributes:
    * id (primary key)
    * step_number (must be unique in the same recipe)
    * title
    * description
    * recipe_id (foreign key, relationship N:1 with recipes)

# Backend (REST API)
RecipesStorage - RESTapi
This is a REST API to manage a storage of recipes. It is written in Java using Spring Boot and Spring Data JPA and is available ad http://localhost:8080 <br>
The API allows to make the following CRUD calls (the results are in JSON format)<br>
All of the values are validated before saving them in the database. If a value is not valid, the API returns an error message (tipically an error 400: bad requests, or 404: not found).
- for the recipes:
    * GET http://localhost:8080/recipeStorage/recipes (all the recipes) 
    * GET http://localhost:8080/recipeStorage/recipes/{recipe_id} (a specific recipe) 
    * GET http://localhost:8080/recipeStorage/recipes?title=YourRecipeTitle (get recipe with title)
    * GET http://localhost:8080/recipeStorage/recipes?title_start=RecipeTitle (get recipe with beginning of its title)
    * GET http://localhost:8080/recipeStorage/recipes/difficulty/{recipe_difficulty} (get recipes by difficulty)
    * POST http://localhost:8080/recipeStorage/recipes (add an array of new recipes!)
    * PUT http://localhost:8080/recipeStorage/recipes/{recipe_id} (update a recipe)
    * DELETE http://localhost:8080/recipeStorage/recipes/{recipe_id} (delete a recipe)
    * DELETE http://localhost:8080/recipeStorage/recipes (delete all the recipes)
    * GET  http://localhost:8080/recipeStorage/recipes/count (count the number of recipes)

A post request for a recipe must specify:
{<br>
    "title": "YourRecipeTitle",<br>
    "serving": servingNumber,<br>
    "time": timeInMinutes,<br>
    "difficulty": "Easy or Medium or Hard",<br>
}


A post request for a recipe can also specify
{<br>
    "description": "YourRecipeDescription",<br>
    "photo": "YourRecipePhotoBase64String"<br>
}

- for the ingredients:
    * GET http://localhost:8080/recipeStorage/ingredients (all the ingredients)
    * GET http://localhost:8080/recipeStorage/recipes/{recipe_id}/ingredients (ingredients of a recipe)
    * POST http://localhost:8080/recipeStorage/recipes/{recipe_id}/ingredients (add an array of ingredients to a specific recipe)
    * PUT http://localhost:8080/recipeStorage/ingredients/{ingredient_id} (update an ingredient)
    * DELETE http://localhost:8080/recipeStorage/ingredients/{ingredient_id} (delete an ingredient)
    * DELETE http://localhost:8080/recipeStorage/recipes/{recipe_id}/ingredients (delete all the ingredients of a recipe)
    * DELETE http://localhost:8080/recipeStorage/ingredients (delete all the ingredients)
    * GET  http://localhost:8080/recipeStorage/recipes/{recipe_id}/ingredients/count (count the number of ingredients of a recipe)

A post request for an ingredient must specify
{<br>
    "name": "YourIngredientName",<br>
    "quantity": ingredientQuantity,<br>
    "unitOfMeasurement": "YourUnitOfMeasurement"<br>
}

- for the steps:
    * GET http://localhost:8080/recipeStorage/steps (all the steps)
    * GET http://localhost:8080/recipeStorage/recipes/{recipe_id}/steps (steps of a recipe)
    * POST http://localhost:8080/recipeStorage/recipes/{recipe_id}/steps (add an array of steps to a specific recipe)
    * PUT http://localhost:8080/recipeStorage/steps/{step_id} (update a step)
    * DELETE http://localhost:8080/recipeStorage/steps/{step_id} (delete a step)
    * DELETE http://localhost:8080/recipeStorage/recipes/{recipe_id}/steps (delete all the steps of a recipe)
    * DELETE http://localhost:8080/recipeStorage/steps (delete all the steps)
    * GET  http://localhost:8080/recipeStorage/recipes/{recipe_id}/steps/count (count the number of steps of a recipe)
    * GET http://localhost:8080/recipeStorage/steps/count (count all the steps saved in the database)

A post request for the step must specify
{<br>
    "stepNumber": stepNumber,<br>
    "title": "YourStepTitle",<br>
    "description": "YourStepDescription"<br>
}

--------------------------------------------------------------------------------------------------------------------------

Most important files of the application:
- RecipeStorageApplication.java: the main class of the application
- Entities: the classes that represent the entities of the database
- Repositories: the interfaces that extend the JpaRepository interface (provided by Spring Data JPA) and allow to make CRUD calls
- Controllers: the classes that manage the requests and the responses
- httpRequests: used to test the API with multiple calls

# Frontend (Angular)
The frontend is made in Angular, it can be started with 'ng serve' and is available ad http://localhost:4200

