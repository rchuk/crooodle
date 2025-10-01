CREATE TABLE role_entity(
  id SERIAL PRIMARY KEY,
  role varchar(100) UNIQUE NOT NULL
);

CREATE TABLE user_entity(
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  name varchar(200) NOT NULL,
  email varchar(320) UNIQUE NOT NULL,
  password_hash varchar(256) NOT NULL,
  role_id int NOT NULL REFERENCES role_entity(id)
);
