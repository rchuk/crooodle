CREATE TABLE hotel (
  id UUID NOT NULL,
  owner_id UUID NOT NULL,
  name VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE room (
  id UUID NOT NULL,
  name VARCHAR(255) NOT NULL,
  price INTEGER NOT NULL,
  hotel_id UUID,
  PRIMARY KEY (id),
  CONSTRAINT fk_room_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id),
  CONSTRAINT check_price_positive CHECK (price > 0)
);
