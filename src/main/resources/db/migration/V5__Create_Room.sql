CREATE TABLE room (
                      id BIGSERIAL PRIMARY KEY,  -- Primary key for the room
                      number VARCHAR(100) NOT NULL,  -- Room number, required and max length of 100 characters
                      price_per_night INT CHECK (price_per_night > 0),  -- Room price per night, must be positive
                      hotel_id BIGINT NOT NULL REFERENCES hotel(id) ON DELETE CASCADE,  -- Foreign key to hotel, deletes room if hotel is deleted
                      room_type_id BIGINT NOT NULL REFERENCES room_type(id) ON DELETE CASCADE  -- Foreign key to room type, deletes room if room type is deleted
);
