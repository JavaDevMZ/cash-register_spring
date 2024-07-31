CREATE TABLE IF NOT EXISTS "products"(
    id bigserial primary key,
    name VARCHAR(256),
    price DECIMAL,
    quantity BIGINT,
    description VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS "roles"(
    id smallserial primary key,
    name varchar(256) unique
  );

CREATE TABLE IF NOT EXISTS "users"(
   id bigserial primary key,
   email VARCHAR(256) unique,
   password VARCHAR(256),
   role VARCHAR(256) REFERENCES "roles"(name)
    );

CREATE TABLE IF NOT EXISTS "orders"(
   id bigserial primary key,
   amount DECIMAL,
   date DATE,
   cashier_id BIGINT REFERENCES "users"(id)
);

CREATE TABLE IF NOT EXISTS "order_items"(
   id bigserial primary key,
   order_id BIGINT references "orders"(id),
   quantity BIGINT,
   product_id BIGINT REFERENCES "products"(id)
)


