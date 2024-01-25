-- Create Database
CREATE DATABASE IF NOT EXISTS RecipeDB;
USE RecipeDB;

-- Create Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    date_of_birth DATE NOT NULL,
    hash_psw VARCHAR(255) NOT NULL
);

-- Create Recipes Table
CREATE TABLE IF NOT EXISTS recipes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    date_of_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    serving INT NOT NULL,
    description TEXT,
    steps TEXT NOT NULL,
    time INT NOT NULL, -- Assuming this is in minutes
    difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
    photo VARCHAR(255), -- URL or path to the photo
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
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
