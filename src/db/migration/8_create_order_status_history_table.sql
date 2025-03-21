do
$$
    begin
        if not exists (select from pg_type where typname  = 'order_status') THEN
            create type "order_status" as enum ('CREATE', 'CONFIRMED', 'IN_PREPARATION', 'COMPLETED', 'SERVED');
        end if;
    end
$$;

create table if not exists order_status_history (
    "id" varchar primary key,
    "id_order" varchar not null references "order" ("id"),
    "status" order_status not null,
    "updated_at" timestamp not null default now(),
    "created_at" timestamp default current_timestamp not null
);
