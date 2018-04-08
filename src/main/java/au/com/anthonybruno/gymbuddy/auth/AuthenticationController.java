package au.com.anthonybruno.gymbuddy.auth;

import au.com.anthonybruno.gymbuddy.user.model.UserDetails;
import au.com.anthonybruno.gymbuddy.util.json.Json;
import io.javalin.Context;

public class AuthenticationController {

    public static String AUTH_ATTR_KEY = "auth";

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
            throw new IllegalStateException("Need username and password");
        }
        UserDetails userDetails = this.authenticationService.login(credentials.getUsername(), credentials.getPassword())
                .orElseThrow(() -> new IllegalStateException("Incorrect details"));

        setSession(context, userDetails);
        context.status(200);
    }

    public void logout(Context context) {
        setSession(context, null);
        context.status(200);
    }

    public void logCheck(Context context) {
        UserDetails sessionDetails = getSession(context);
        if (sessionDetails == null) {
            context.status(401);
        } else {
            context.json(getSession(context));
        }
    }

    private void setSession(Context context, UserDetails userDetails) {
        context.sessionAttribute(AUTH_ATTR_KEY, userDetails);
    }

    private UserDetails getSession(Context context) {
        return context.sessionAttribute(AUTH_ATTR_KEY);
    }

}
