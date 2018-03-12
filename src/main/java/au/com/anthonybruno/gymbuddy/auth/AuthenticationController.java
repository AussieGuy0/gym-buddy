package au.com.anthonybruno.gymbuddy.auth;

import io.javalin.BasicAuthCredentials;
import io.javalin.Context;

public class AuthenticationController {

    public static String AUTH_ATTR_KEY = "auth";

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void login(Context context) {
        BasicAuthCredentials credentials = context.basicAuthCredentials();
        if (credentials == null) {
            throw new IllegalStateException("Need username and password in auth header");
        }
        UserDetails userDetails = this.authenticationService.login(credentials.getUsername(), credentials.getPassword())
                .orElseThrow(() -> new IllegalStateException("Incorrect details"));

        context.sessionAttribute(AUTH_ATTR_KEY, userDetails);
        context.status(200);
    }

    public void logout(Context context) {
        context.sessionAttribute(AUTH_ATTR_KEY, null);
        context.status(200);
    }

    public void logCheck(Context context) {
        context.json(context.sessionAttribute(AUTH_ATTR_KEY));
    }

}
