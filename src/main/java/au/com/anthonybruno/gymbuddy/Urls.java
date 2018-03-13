package au.com.anthonybruno.gymbuddy;

import au.com.anthonybruno.gymbuddy.auth.AuthenticationController;
import au.com.anthonybruno.gymbuddy.auth.InMemoryAuthenticationService;
import au.com.anthonybruno.gymbuddy.user.UserController;
import au.com.anthonybruno.gymbuddy.workout.WorkoutController;
import io.javalin.Javalin;

import static io.javalin.ApiBuilder.*;

public class Urls {

    private final Javalin app;

    private final AuthenticationController authenticationController = new AuthenticationController(new InMemoryAuthenticationService());
    private final UserController userController = new UserController();
    private final WorkoutController workoutController = new WorkoutController();

    public Urls(Javalin app) {
        this.app = app;
    }

    void setupEndpoints() {
        app.routes(() -> {
            path("auth",() -> {
                post("/login", authenticationController::login);
                post("/logout", authenticationController::logout);
                post("/logCheck", authenticationController::logCheck);
            });
            path("api/v1", () -> {
                path("/user", () -> {
                    post(userController::addUser);
                    path("/:userId", () -> {
                        get(userController::getUser);
                        put(userController::editUser);
                        post(userController::addUser);
                        delete(userController::deleteUser);
                        path("/workout", () -> {
                            post(workoutController::addWorkout);
                            post(workoutController::getWorkouts);
                        });
                    });
                });
            });
        });
    }

}
