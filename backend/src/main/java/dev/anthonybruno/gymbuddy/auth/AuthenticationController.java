package dev.anthonybruno.gymbuddy.auth;

import dev.anthonybruno.gymbuddy.exception.BadRequestException;
import dev.anthonybruno.gymbuddy.user.model.UserDetails;
import dev.anthonybruno.gymbuddy.util.json.Json;
import io.javalin.http.Context;

import static dev.anthonybruno.gymbuddy.auth.SessionUtils.getSession;
import static dev.anthonybruno.gymbuddy.auth.SessionUtils.setSession;

public class AuthenticationController {


    private final AuthenticationService authenticationService;

    public AuthenticationController() {
        this(new DbAuthenticationService());
    }

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void login(Context context) {
        UserCredentials credentials = Json.intoClass(context.body(), UserCredentials.class);
        if (credentials == null) {
            throw new IllegalStateException("Need email and password");
        }
        UserDetails userDetails = this.authenticationService.login(credentials.getEmail(), credentials.getPassword())
                .orElseThrow(() -> new BadRequestException("Incorrect details"));

        setSession(context, userDetails);
        context.status(200);
        context.json(userDetails);
    }

    public void logout(Context context) {
        setSession(context, UserDetails.NOOP);
        context.status(200);
    }

    public void logCheck(Context context) {
        UserDetails sessionDetails = getSession(context);
        if (sessionDetails == null || sessionDetails == UserDetails.NOOP) {
            context.status(401);
        } else {
            context.json(getSession(context));
        }
    }


}
