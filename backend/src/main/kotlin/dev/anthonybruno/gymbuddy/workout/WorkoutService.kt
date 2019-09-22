package dev.anthonybruno.gymbuddy.workout;

import java.util.List;

public interface WorkoutService {

    List<Workout> getWorkouts(int userId);

    Workout addWorkout(int userId, Workout workout);
}
