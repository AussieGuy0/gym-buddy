package au.com.anthonybruno.gymbuddy.auth;

import io.javalin.BasicAuthCredentials;
import io.javalin.Context;

public class AuthenticationController {

    public static String AUTH_ATTR_KEY = "auth";

    private final AuthenticationService authenticationService;

    public AuthenticationController() {
        this(new InMemoryAuthenticationService());
    }

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
