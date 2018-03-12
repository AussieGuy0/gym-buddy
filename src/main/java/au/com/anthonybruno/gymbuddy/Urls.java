package au.com.anthonybruno.gymbuddy;

import au.com.anthonybruno.gymbuddy.auth.AuthenticationController;
import au.com.anthonybruno.gymbuddy.auth.InMemoryAuthenticationService;
import io.javalin.Javalin;

import static io.javalin.ApiBuilder.path;
import static io.javalin.ApiBuilder.post;

public class Urls {

    private final Javalin app;

    private final AuthenticationController authenticationController = new AuthenticationController(new InMemoryAuthenticationService());

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
            path("api/v1/", () -> {

            });
        });
    }

}
