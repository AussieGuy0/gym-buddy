package au.com.anthonybruno.gymbuddy.workout;

import au.com.anthonybruno.gymbuddy.exception.UnauthorisedException;
import au.com.anthonybruno.gymbuddy.user.model.UserDetails;
import au.com.anthonybruno.gymbuddy.util.json.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Context;

import java.util.List;

import static au.com.anthonybruno.gymbuddy.auth.SessionUtils.getSession;

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
        checkUserIsAccessingOwnWorkouts(userId, context);
        Workout workout = Json.intoClass(context.body(), Workout.class);
        Workout savedWorkout = workoutService.addWorkout(userId, workout);
        context.json(savedWorkout);
    }

    public void getWorkouts(Context context) {
        int userId = getUserIdFromRequest(context);
        checkUserIsAccessingOwnWorkouts(userId, context);
        List<Workout> workouts = workoutService.getWorkouts(userId);
        context.json(workouts);
    }

    private void checkUserIsAccessingOwnWorkouts(int userId, Context context) {
        UserDetails userDetails = getSession(context);
        if (userId != userDetails.getId()) {
            throw new UnauthorisedException("Can't access someone else's workouts!");
        }
    }

    private int getUserIdFromRequest(Context context) {
        return Integer.parseInt(context.param("userId"));
    }
}
