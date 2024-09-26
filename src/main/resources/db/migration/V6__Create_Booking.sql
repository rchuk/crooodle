CREATE TABLE booking (
                         id BIGSERIAL PRIMARY KEY,  -- Primary key for the booking
                         user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,  -- Foreign key to users, deletes booking if user is deleted
                         room_id BIGINT NOT NULL REFERENCES room(id) ON DELETE CASCADE,  -- Foreign key to room, deletes booking if room is deleted
                         start_date DATE NOT NULL,  -- Start date of the booking, required
                         end_date DATE NOT NULL,  -- End date of the booking, required
                         total_price INT CHECK (total_price > 0),  -- Total price, must be positive
                         status VARCHAR(50) NOT NULL  -- Booking status, required
);
