INSERT INTO "ingredient_stock_movement" ("id","id_ingredient", "movement_datetime","quantity", "unit", "movement_type") VALUES
    ('S001', 'I001', '2025-02-01 08:00', 10000, 'G','IN'),
    ('S002', 'I002', '2025-02-01 08:00', 20, 'L', 'IN'),
    ('S003', 'I003', '2025-02-01 08:00', 100, 'U', 'IN'),
    ('S004', 'I004', '2025-02-01 08:00', 50, 'U', 'IN');

INSERT INTO "ingredient_stock_movement" ("id","id_ingredient", "movement_datetime","quantity", "unit", "movement_type") VALUES
    ('S005', 'I003', '2025-02-02 10:00', 10, 'U','OUT'),
    ('S006', 'I003', '2025-02-03 15:00', 10, 'U','OUT'),
    ('S007', 'I004', '2025-02-05 16:00', 20, 'U','OUT');
