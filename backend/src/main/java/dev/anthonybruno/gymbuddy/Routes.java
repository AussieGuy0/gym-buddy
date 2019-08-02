package dev.anthonybruno.gymbuddy;

import dev.anthonybruno.gymbuddy.auth.AuthenticationController;
import dev.anthonybruno.gymbuddy.exception.HttpException;
import dev.anthonybruno.gymbuddy.user.UserController;
import dev.anthonybruno.gymbuddy.workout.WorkoutController;

import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;


public class Routes {

    private final Javalin app;

    private final AuthenticationController authenticationController = new AuthenticationController();
    private final UserController userController = new UserController();
    private final WorkoutController workoutController = new WorkoutController();

    public Routes(Javalin app) {
        this.app = app;
    }

    void setupEndpoints() {
        app.routes(() -> {
            path("/auth",() -> {
                post("/login", authenticationController::login);
                post("/logout", authenticationController::logout);
                post("/logcheck", authenticationController::logCheck);
            });
            path("api/v1", () -> {
                path("/users", () -> {
                    post(userController::addUser);
                    path("/:userId", () -> {
                        get(userController::getUser);
                        put(userController::editUser);
                        post(userController::addUser);
                        delete(userController::deleteUser);
                        path("/workouts", () -> {
                            post(workoutController::addWorkout);
                            get(workoutController::getWorkouts);
                        });
                    });
                });
            });
        });
        app.exception(HttpException.class, (exception, ctx) -> {
            ctx.status(exception.getStatusCode());
            ctx.json(exception);
        });
    }

}
