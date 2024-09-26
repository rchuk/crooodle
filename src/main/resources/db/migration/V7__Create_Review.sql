CREATE TABLE review (
                        id BIGSERIAL PRIMARY KEY,  -- Primary key for the review
                        hotel_id BIGINT NOT NULL REFERENCES hotel(id) ON DELETE CASCADE,  -- Foreign key to hotel, deletes review if hotel is deleted
                        user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,  -- Foreign key to users, deletes review if user is deleted
                        content TEXT NOT NULL  -- Review content, required
);
