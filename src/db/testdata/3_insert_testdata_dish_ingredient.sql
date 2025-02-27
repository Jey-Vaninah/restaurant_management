INSERT INTO "dish_ingredient" ("id_dish", "id_ingredient", "required_quantity", "unit") VALUES
    ('D001', 'I001', 100.00, 'G'),
    ('D001', 'I002', 0.15, 'L'),
    ('D001', 'I003', 1.00, 'U'),
    ('D001', 'I004', 1.00, 'U');


SELECT d.id AS dish_id, d.name AS dish_name, d.unit_price,
       i.id AS ingredient_id, i.name AS ingredient_name,
       i.update_datetime, i.unit_price AS ingredient_unit_price, i.unit,
       di.required_quantity AS ingredient_quantity
FROM dish d
         LEFT JOIN dish_ingredient di ON d.id = di.id_dish
         LEFT JOIN ingredient i ON di.id_ingredient = i.id
WHERE d.id = 'D001';
