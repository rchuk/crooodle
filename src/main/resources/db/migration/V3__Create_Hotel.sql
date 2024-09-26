CREATE TABLE hotel (
                       id BIGSERIAL PRIMARY KEY,  -- Primary key for the hotel
                       name VARCHAR(128) NOT NULL,  -- Hotel name, required and max length of 128 characters
                       address VARCHAR(500) NOT NULL,  -- Hotel address, required and max length of 500 characters
                       ranking DOUBLE PRECISION CHECK (ranking >= 0),  -- Hotel ranking, must be non-negative
                       total_ranks INT CHECK (total_ranks >= 0)  -- Total number of ranks, must be non-negative
);
