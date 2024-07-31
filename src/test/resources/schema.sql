CREATE TABLE IF NOT EXISTS "products"(
    id bigint auto_increment primary key,
    name VARCHAR(256),
    price DECIMAL,
    quantity BIGINT,
    description VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS "roles"(
    id smallint auto_increment primary key,
    name varchar(256) unique
  );

CREATE TABLE IF NOT EXISTS "users"(
   id bigint auto_increment primary key,
   email VARCHAR(256) unique,
   password VARCHAR(256),
   role VARCHAR(256) REFERENCES "roles"(name)
    );

CREATE TABLE IF NOT EXISTS "orders"(
   id bigint auto_increment primary key,
   amount DECIMAL,
   date DATE,
   cashier_id BIGINT REFERENCES "users"(id)
);

CREATE TABLE IF NOT EXISTS "order_items"(
   id bigint auto_increment primary key,
   order_id BIGINT references "orders"(id),
   quantity BIGINT,
   product_id BIGINT REFERENCES "products"(id)
)


