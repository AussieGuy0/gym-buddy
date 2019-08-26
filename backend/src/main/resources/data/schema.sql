
CREATE TABLE users (
  id SERIAL UNIQUE PRIMARY KEY ,
  password VARCHAR(60) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE workouts (
  id SERIAL UNIQUE PRIMARY KEY,
  user_id INTEGER NOT NULL,
  title VARCHAR(100) NOT NULL,
  description TEXT,
  date TIMESTAMP NOT NULL,
  FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE exercises (
   id SERIAL UNIQUE PRIMARY KEY,
   name VARCHAR(100) UNIQUE NOT NULL,
   description TEXT NULL,
   main_muscle TEXT NULL
);

CREATE TABLE workout_exercises (
   id SERIAL UNIQUE PRIMARY KEY,
   workout_id INTEGER,
   exercise_id exercises,
   weight INTEGER NULL,
   FOREIGN KEY(workout_id) references workouts(id),
   FOREIGN KEY (exercise_id) references exercises(id)
)
