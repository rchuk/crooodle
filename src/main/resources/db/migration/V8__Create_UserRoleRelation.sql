CREATE TABLE user_role_relation (
                                    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,  -- Foreign key to users, deletes relation if user is deleted
                                    role_id INT NOT NULL REFERENCES user_role(id) ON DELETE CASCADE,  -- Foreign key to user_role, deletes relation if role is deleted
                                    PRIMARY KEY (user_id, role_id)  -- Composite primary key using user_id and role_id
);
