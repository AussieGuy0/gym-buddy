
CREATE TABLE users (
  id LONG AUTO_INCREMENT,
  username VARCHAR(24) NOT NULL,
  password BINARY(60) NOT NULL,
  email VARCHAR(150) NOT NULL
);

CREATE TABLE workouts (
  id LONG AUTO_INCREMENT,
  user_id LONG NOT NULL,
  title VARCHAR(100) NOT NULL,
  description VARCHAR(1024),
  date_start TIMESTAMP NOT NULL,
  date_end TIMESTAMP NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(user_id) REFERENCES users(id)
);