do
$$
begin
    if not exists(select from pg_type where typname = 'unit_type') then
create type "unit_type" as enum ('G', 'L', 'U');
end if;
end
$$;

create table if not exists "ingredient" (
    "id" varchar primary key,
    "name" varchar not null,
    "last_modified" timestamp default current_timestamp,
    "unit_price" DECIMAL(10,2) not null,
    "unit" unit_type not null
    );

