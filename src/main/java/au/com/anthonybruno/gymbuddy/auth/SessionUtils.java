package au.com.anthonybruno.gymbuddy.auth;

import au.com.anthonybruno.gymbuddy.user.model.UserDetails;
import io.javalin.Context;


public class SessionUtils {

    public static String AUTH_ATTR_KEY = "auth";

    public static void setSession(Context context, UserDetails userDetails) {
        context.sessionAttribute(AUTH_ATTR_KEY, userDetails);
    }

    public static UserDetails getSession(Context context) {
        return context.sessionAttribute(AUTH_ATTR_KEY);
    }
}
