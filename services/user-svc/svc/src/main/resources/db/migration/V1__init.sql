create table if not exists role_entity (
                                         id   serial primary key,
                                         role varchar(100) unique not null
  );

create table if not exists user_entity (
                                         id uuid primary key default gen_random_uuid(),
  name varchar(200) not null,
  email varchar(320) unique not null,
  password_hash varchar(256) not null,
  role_id int not null references role_entity(id)
  );
