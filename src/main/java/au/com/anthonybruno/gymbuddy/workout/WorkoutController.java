package au.com.anthonybruno.gymbuddy.workout;

import au.com.anthonybruno.gymbuddy.json.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Context;

import java.util.List;

public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }


    public void addWorkout(Context context) {
        long userId = getUserIdFromRequest(context);
        ObjectNode objectNode = new Json(context.body()).asObject();
        Workout workout = new Workout(null, null, null, null); //TODO: Fill in details
        workoutService.addWorkout(userId, workout);
    }

    public void getWorkouts(Context context) {
        long userId = getUserIdFromRequest(context);
        List<Workout> workouts = workoutService.getWorkouts(userId);
        context.json(workouts);
    }

    private long getUserIdFromRequest(Context context) {
        return Long.parseLong(context.param("userId"));
    }
}
