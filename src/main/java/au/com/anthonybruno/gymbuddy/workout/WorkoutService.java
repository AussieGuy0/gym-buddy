package au.com.anthonybruno.gymbuddy.workout;

import au.com.anthonybruno.gymbuddy.auth.UserDetails;

import java.util.List;

public interface WorkoutService {

    List<Workout> getWorkouts(long userId);

    Workout addWorkout(long userId, Workout workout);
}
