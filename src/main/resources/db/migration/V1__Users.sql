CREATE TABLE users(
                      id BIGSERIAL PRIMARY KEY,  -- Primary key
                      name VARCHAR(128) NOT NULL,  -- User name
                      email VARCHAR(320) NOT NULL,  -- User email
                      password_hash VARCHAR(344) NOT NULL  -- Hashed password
);
