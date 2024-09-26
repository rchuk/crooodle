CREATE TABLE room_type (
                           id BIGSERIAL PRIMARY KEY,  -- Primary key for the room type
                           type VARCHAR(50) NOT NULL  -- Room type (e.g., Standard, Deluxe), required
);
