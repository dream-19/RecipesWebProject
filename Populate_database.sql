-- Disable foreign key checks
use RecipeDB;
SET FOREIGN_KEY_CHECKS = 0;

-- clean tables
TRUNCATE TABLE ingredients;
TRUNCATE TABLE steps;
TRUNCATE TABLE recipes;

-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;


-- Insert into Recipes
INSERT INTO recipes (title, serving, time, difficulty)
VALUES 
    ('Classic Spaghetti Bolognese', 4, 60, 'Medium'),
    ('Vegan Avocado Salad', 2, 15, 'Easy'),
    ('Chicken Alfredo Pasta', 4, 45, 'Medium'),
    ('Vegetable Stir-Fry', 3, 30, 'Easy'),
    ('Chocolate Chip Cookies', 24, 25, 'Easy'),
    ('Grilled Salmon with Lemon', 2, 20, 'Hard'),
    ('Mushroom Risotto', 4, 40, 'Medium');


-- Insert into Ingredients for Recipe 1 (Spaghetti Bolognese)
INSERT INTO ingredients (name, quantity, unit_of_measurement, recipe_id)
VALUES 
    ('Spaghetti', 500, 'g', 1),
    ('Ground Beef', 250, 'g', 1),
    ('Tomato Sauce', 200, 'ml', 1);

-- Insert into Ingredients for Recipe 2 (Avocado Salad)
INSERT INTO ingredients (name, quantity, unit_of_measurement, recipe_id)
VALUES 
    ('Avocado', 2, 'piece', 2),
    ('Lettuce', 1, 'piece', 2),
    ('Olive Oil', 50, 'ml', 2);
    
INSERT INTO ingredients (name, quantity, unit_of_measurement, recipe_id)
VALUES 
    ('All-Purpose Flour', 250, 'g', 3),
    ('Chocolate Chips', 200, 'g', 3),
    ('Butter', 150, 'g', 3),
    ('Sugar', 100, 'g', 3);

-- Insert ingredients for Grilled Salmon with Lemon
INSERT INTO ingredients (name, quantity, unit_of_measurement, recipe_id)
VALUES 
    ('Salmon Fillets', 2, 'pieces', 4),
    ('Lemon', 1, 'piece', 4),
    ('Olive Oil', 30, 'ml', 4);

-- Insert ingredients for Mushroom Risotto
INSERT INTO ingredients (name, quantity, unit_of_measurement, recipe_id)
VALUES 
    ('Arborio Rice', 300, 'g', 5),
    ('Mushrooms', 200, 'g', 5),
    ('Chicken Broth', 750, 'ml', 5),
    ('Onion', 1, 'piece', 5);
    

-- Insert steps for 'Classic Spaghetti Bolognese'
INSERT INTO steps (step_number, title, description, recipe_id)
VALUES
    (1, 'Boil Pasta', 'Boil spaghetti pasta until al dente.', 1),
    (2, 'Cook Ground Beef', 'In a pan, cook ground beef until browned.', 1),
    (3, 'Sauté Onions and Garlic', 'In the same pan, sauté onions and garlic.', 1),
    (4, 'Add Tomatoes and Seasoning', 'Add canned tomatoes and seasoning to the pan.', 1),
    (5, 'Simmer Sauce', 'Simmer the sauce until it thickens.', 1);

-- Insert steps for 'Vegan Avocado Salad'
INSERT INTO steps (step_number, title, description, recipe_id)
VALUES
    (1, 'Chop Avocado', 'Chop ripe avocados into bite-sized pieces.', 2),
    (2, 'Mix Ingredients', 'Mix avocados, tomatoes, onions, and cilantro in a bowl.', 2),
    (3, 'Add Dressing', 'Drizzle olive oil and lime juice, then season with salt and pepper.', 2);

-- Insert steps for 'Chicken Alfredo Pasta'
INSERT INTO steps (step_number, title, description, recipe_id)
VALUES
    (1, 'Cook Pasta', 'Cook fettuccine pasta until al dente.', 3),
    (2, 'Sauté Chicken', 'In a pan, sauté chicken pieces until cooked through.', 3),
    (3, 'Prepare Alfredo Sauce', 'In a separate pan, prepare Alfredo sauce.', 3),
    (4, 'Combine Pasta, Chicken, and Sauce', 'Combine pasta, chicken, and Alfredo sauce.', 3);

-- Insert steps for 'Vegetable Stir-Fry'
INSERT INTO steps (step_number, title, description, recipe_id)
VALUES
    (1, 'Chop Vegetables', 'Chop a mix of your favorite vegetables.', 4),
    (2, 'Heat Oil', 'Heat oil in a wok or large skillet.', 4),
    (3, 'Stir-Fry Vegetables', 'Stir-fry the chopped vegetables in the hot oil.', 4),
    (4, 'Add Sauce', 'Add your favorite stir-fry sauce and stir well.', 4);

-- Insert steps for 'Chocolate Chip Cookies'
INSERT INTO steps (step_number, title, description, recipe_id)
VALUES
    (1, 'Preheat Oven', 'Preheat your oven to 350°F (175°C).', 5),
    (2, 'Mix Ingredients', 'Mix butter, sugar, eggs, and vanilla extract in a bowl.', 5),
    (3, 'Add Dry Ingredients', 'Add flour, baking soda, and chocolate chips to the mixture.', 5),
    (4, 'Bake', 'Drop spoonfuls of cookie dough onto a baking sheet and bake for 10-12 minutes.', 5);

-- Insert steps for 'Grilled Salmon with Lemon'
INSERT INTO steps (step_number, title, description, recipe_id)
VALUES
    (1, 'Preheat Grill', 'Preheat your grill to medium-high heat.', 6),
    (2, 'Season Salmon', 'Season salmon fillets with salt, pepper, and olive oil.', 6),
    (3, 'Grill Salmon', 'Place salmon fillets on the grill and cook for about 4-5 minutes per side.', 6),
    (4, 'Serve with Lemon', 'Serve the grilled salmon with lemon wedges.', 6);

-- Insert steps for 'Mushroom Risotto'
INSERT INTO steps (step_number, title, description, recipe_id)
VALUES
    (1, 'Sauté Mushrooms', 'In a large pan, sauté mushrooms in butter until browned.', 7),
    (2, 'Cook Onions and Garlic', 'Add onions and garlic to the pan and cook until translucent.', 7),
    (3, 'Add Arborio Rice', 'Add Arborio rice to the pan and stir until coated with butter.', 7),
    (4, 'Add Broth', 'Gradually add warm chicken or vegetable broth while stirring constantly.', 7),
    (5, 'Simmer and Stir', 'Simmer and stir until the rice is creamy and cooked.', 7);

