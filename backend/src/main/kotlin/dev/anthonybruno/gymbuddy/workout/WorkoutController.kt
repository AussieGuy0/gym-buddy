package dev.anthonybruno.gymbuddy.workout;

import dev.anthonybruno.gymbuddy.exception.HttpException;
import dev.anthonybruno.gymbuddy.exception.UnauthorisedException;
import dev.anthonybruno.gymbuddy.user.model.UserDetails;
import dev.anthonybruno.gymbuddy.util.json.Json;
import dev.anthonybruno.gymbuddy.auth.SessionUtils;
import io.javalin.http.Context;

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
        UserDetails userDetails = SessionUtils.getSession(context);
        if (userId != userDetails.getId()) {
            throw new UnauthorisedException("Can't access someone else's workouts!");
        }
    }

    private int getUserIdFromRequest(Context context) {
        String userId = context.pathParam("userId");
        if (userId.isEmpty()) {
            throw new HttpException(400, "Need userId in path");
        }
        try {
            return Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new HttpException(400, "userId provided is not a number!");
        }
    }
}
