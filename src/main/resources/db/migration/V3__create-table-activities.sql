CREATE TABLE activities (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    occurs_at TIMESTAMP NOT NULL,
    trip_id UUID DEFAULT uuid_generate_v4() NOT NULL,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
)