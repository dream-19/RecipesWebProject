-- Create Database
DROP DATABASE IF EXISTS RecipeDB;
CREATE DATABASE IF NOT EXISTS RecipeDB;
USE RecipeDB;

-- Create Recipes Table
CREATE TABLE IF NOT EXISTS recipes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    date_of_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    serving INT NOT NULL,
    description TEXT,
    time INT NOT NULL, -- Assuming this is in minutes
    difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
    photo VARCHAR(255)
);

-- Create Ingredients Table
CREATE TABLE IF NOT EXISTS ingredients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    unit_of_measurement VARCHAR(50) NOT NULL,
    recipe_id INT,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id)
    ON DELETE CASCADE
);

-- Create Steps Table
CREATE TABLE IF NOT EXISTS steps (
	id INT AUTO_INCREMENT PRIMARY KEY,
    step_number INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    recipe_id INT,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id)
    ON DELETE CASCADE
);
