RecipesStorage - RESTapi
This is a REST API to manage a storage of recipes. It is written in Java using Spring Boot and Spring Data JPA.
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

 --------------------------------------------------------------------------------------------------------------------------

The API allows to make the following CRUD calls (the results are in JSON format):
All of the values are validated before saving them in the database. If a value is not valid, the API returns an error message (tipically an error 400: bad requests, or 404: not found).
- for the recipes:
GET http://localhost:8080/recipeStorage/recipes (all the recipes)
GET http://localhost:8080/recipeStorage/recipes/{recipe_id} (a specific recipe)
GET http://localhost:8080/recipeStorage/recipes?title=YourRecipeTitle (get recipe with title)
GET http://localhost:8080/recipeStorage/recipes?title_start=RecipeTitle (get recipe with beginning of its title)
GET http://localhost:8080/recipeStorage/recipes/difficulty/{recipe_difficulty} (get recipes by difficulty)
POST http://localhost:8080/recipeStorage/recipes (add an array of new recipes!)
PUT http://localhost:8080/recipeStorage/recipes/{recipe_id} (update a recipe)
DELETE http://localhost:8080/recipeStorage/recipes/{recipe_id} (delete a recipe)
DELETE http://localhost:8080/recipeStorage/recipes (delete all the recipes)
GET  http://localhost:8080/recipeStorage/recipes/count (count the number of recipes)

A post request for a recipe must specify
{
    "title": "YourRecipeTitle",
    "serving": servingNumber,
    "time": timeInMinutes,
    "difficulty": "Easy or Medium or Hard",
}

A post request for a recipe can also specify
{
    "description": "YourRecipeDescription",
    "photo": "YourRecipePhotoBase64String"
}

- for the ingredients:
GET http://localhost:8080/recipeStorage/ingredients (all the ingredients)
GET http://localhost:8080/recipeStorage/recipes/{recipe_id}/ingredients (ingredients of a recipe)
POST http://localhost:8080/recipeStorage/recipes/{recipe_id}/ingredients (add an array of ingredients to a specific recipe)
PUT http://localhost:8080/recipeStorage/ingredients/{ingredient_id} (update an ingredient)
DELETE http://localhost:8080/recipeStorage/ingredients/{ingredient_id} (delete an ingredient)
DELETE http://localhost:8080/recipeStorage/recipes/{recipe_id}/ingredients (delete all the ingredients of a recipe)
DELETE http://localhost:8080/recipeStorage/ingredients (delete all the ingredients)
GET  http://localhost:8080/recipeStorage/recipes/{recipe_id}/ingredients/count (count the number of ingredients of a recipe)

A post request for an ingredient must specify
{
    "name": "YourIngredientName",
    "quantity": ingredientQuantity,
    "unitOfMeasurement": "YourUnitOfMeasurement"
}

- for the steps:
GET http://localhost:8080/recipeStorage/steps (all the steps)
GET http://localhost:8080/recipeStorage/recipes/{recipe_id}/steps (steps of a recipe)
POST http://localhost:8080/recipeStorage/recipes/{recipe_id}/steps (add an array of steps to a specific recipe)
PUT http://localhost:8080/recipeStorage/steps/{step_id} (update a step)
DELETE http://localhost:8080/recipeStorage/steps/{step_id} (delete a step)
DELETE http://localhost:8080/recipeStorage/recipes/{recipe_id}/steps (delete all the steps of a recipe)
DELETE http://localhost:8080/recipeStorage/steps (delete all the steps)
GET  http://localhost:8080/recipeStorage/recipes/{recipe_id}/steps/count (count the number of steps of a recipe)
GET http://localhost:8080/recipeStorage/steps/count (count all the steps saved in the database)

A post request for the step must specify
{
    "stepNumber": stepNumber,
    "title": "YourStepTitle",
    "description": "YourStepDescription"
}

 --------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------

Most important files of the application:
- RecipeStorageApplication.java: the main class of the application
- Entities: the classes that represent the entities of the database
- Repositories: the interfaces that extend the JpaRepository interface (provided by Spring Data JPA) and allow to make CRUD calls
- Controllers: the classes that manage the requests and the responses
- httpRequests: used to test the API with multiple calls
