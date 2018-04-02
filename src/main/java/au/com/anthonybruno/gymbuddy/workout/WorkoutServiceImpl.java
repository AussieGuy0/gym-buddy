package au.com.anthonybruno.gymbuddy.workout;

import java.util.List;

public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository = new WorkoutRepository();

    @Override
    public List<Workout> getWorkouts(int userId) {
        return workoutRepository.getWorkouts(userId);
    }

    @Override
    public Workout addWorkout(int userId, Workout workout) {
        return workoutRepository.addWorkout(userId, workout);
    }
}
