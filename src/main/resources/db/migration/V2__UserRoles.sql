CREATE TABLE user_role(
                          id SERIAL PRIMARY KEY,  -- Primary key
                          name VARCHAR(255) NOT NULL  -- Role name
);

-- Insert predefined roles
INSERT INTO user_role (name)
VALUES
    ('ROLE_CLIENT'),
    ('ROLE_HOTEL_STAFF'),
    ('ROLE_HOTEL_ADMIN'),
    ('ROLE_HOTEL_OWNER'),
    ('ROLE_SYSTEM_ADMIN'),
    ('ROLE_SYSTEM_OWNER');
