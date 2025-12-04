CREATE TABLE reservations (
  id UUID PRIMARY KEY,
  room_id UUID NOT NULL,
  profile_id UUID NOT NULL,
  check_in_date DATE NOT NULL,
  check_out_date DATE NOT NULL
);