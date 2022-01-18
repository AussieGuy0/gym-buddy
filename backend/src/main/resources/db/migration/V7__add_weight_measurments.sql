
CREATE TABLE weight_measurements (
  id SERIAL UNIQUE PRIMARY KEY,
  user_id INTEGER NOT NULL,
  weight numeric(4, 1) NULL, -- kg. Supports decimal weights (85.5 kg)
  date TIMESTAMP WITH TIME ZONE NOT NULL,
  FOREIGN KEY(user_id) REFERENCES users(id)
);


