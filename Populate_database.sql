-- clean tables
TRUNCATE TABLE ingredients;
TRUNCATE TABLE recipes;
TRUNCATE TABLE users;

-- Insert into Users
INSERT INTO users (name, surname, nickname, email, date_of_birth, hash_psw)
VALUES 
    ('John', 'Doe', 'johnd', 'johnd@example.com', '1990-06-15', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f'), -- password123
    ('Emily', 'Smith', 'emilys', 'emilys@example.com', '1985-09-23', 'be9cbe311c27325c1f2641791ea8ae2916f042359e9eee4ce60514bf269c3ee4'), -- emily2024
    ('Alex', 'Johnson', 'alexj', 'alexj@example.com', '1992-03-01', 'f99e6e23bfc11c18b661ae4dd582d2f2477e2f22a6fb4d23b7e366235862fce2'); -- alexPass!

-- Insert into Recipes
INSERT INTO recipes (title, serving, time, difficulty, user_id)
VALUES 
    ('Classic Spaghetti Bolognese', 4, 60, 'Medium', 1),
    ('Vegan Avocado Salad', 2, 15, 'Easy', 2);

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
