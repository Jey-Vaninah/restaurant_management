create table if not exists "price " (
    "id" varchar not null,
    "id_ingredient" varchar not null references "ingredient"("id"),
    "addedOn" TIMESTAMP not null,
    "unit_price" decimal(10,2) not null,
    );