create table if not exists "contain" (
    "dish_id" varchar not null references "dish"("id"),
    "ingredient_id" varchar not null references "ingredient"("id"),
    "quantity" decimal(10,2) not null,
    "unit" unit_type not null,
    primary key ("dish_id", "ingredient_id")
    );
