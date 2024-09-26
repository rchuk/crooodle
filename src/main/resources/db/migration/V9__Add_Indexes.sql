-- Adding unique index on users' email field
CREATE UNIQUE INDEX idx_user_email ON users (email);

-- Adding index on room's hotel_id field for performance
CREATE INDEX idx_room_hotel_id ON room (hotel_id);
