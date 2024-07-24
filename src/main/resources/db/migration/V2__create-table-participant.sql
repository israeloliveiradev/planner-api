CREATE TABLE participants (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    trip_id CHAR(36) NOT NULL,
    FOREIGN KEY (trip_id) REFERENCES trips(id)
);