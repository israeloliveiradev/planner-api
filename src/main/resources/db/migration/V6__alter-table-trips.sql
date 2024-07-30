ALTER TABLE trips
ADD COLUMN username_id UUID DEFAULT uuid_generate_v4() NOT NULL;

ALTER TABLE trips
ADD CONSTRAINT fk_username
FOREIGN KEY (username_id) REFERENCES usernames(id) ON DELETE CASCADE;