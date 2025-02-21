create table if not exists "Dish_Ingredient " (
    "id_dish" varchar not null references "dish"("id"),
    "id_ingredient" varchar not null references "ingredient"("id"),
    "required_quantity" decimal(10,2) not null,
    "unit" unit not null,
    primary key ("id_dish", "id_ingredient")
    );
