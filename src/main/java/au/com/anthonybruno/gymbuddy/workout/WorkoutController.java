package au.com.anthonybruno.gymbuddy.workout;

import au.com.anthonybruno.gymbuddy.util.json.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Context;

import java.util.List;

public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController() {
        this(new WorkoutServiceImpl());
    }

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }


    public void addWorkout(Context context) {
        int userId = getUserIdFromRequest(context);
        Workout workout = Json.intoClass(context.body(), Workout.class);
        workoutService.addWorkout(userId, workout);
    }

    public void getWorkouts(Context context) {
        int userId = getUserIdFromRequest(context);
        List<Workout> workouts = workoutService.getWorkouts(userId);
        context.json(workouts);
    }

    private int getUserIdFromRequest(Context context) {
        return Integer.parseInt(context.param("userId"));
    }
}
