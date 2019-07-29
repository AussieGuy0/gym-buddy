package dev.anthonybruno.gymbuddy.user;

import dev.anthonybruno.gymbuddy.exception.BadRequestException;
import dev.anthonybruno.gymbuddy.user.model.UserDetails;
import dev.anthonybruno.gymbuddy.user.model.UserRego;
import dev.anthonybruno.gymbuddy.util.json.Json;
import io.javalin.http.Context;

public class UserController {

    private final UserService userService;

    public UserController() {
        this(new UserServiceImpl());
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void addUser(Context context) {
        UserRego rego = Json.intoClass(context.body(), UserRego.class);
        String password = rego.getPassword();
        if (password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long");
        }

        String email = rego.getEmail();
        if (!email.contains("@")) {
            throw new BadRequestException("Email (" + email + ") not a email format");
        }
        userService.addUser(rego.getUsername(), password, email);

    }

    public void editUser(Context context) {
        throw new UnsupportedOperationException();
    }

    public void deleteUser(Context context) {
        long userId = getUserIdFromRequest(context);
        userService.deleteUser(userId);
    }

    public void getUser(Context context) {
        long userId = getUserIdFromRequest(context);
        UserDetails userDetails = userService.getUser(userId);
        context.json(userDetails);
    }

    private long getUserIdFromRequest(Context context) {
        return Long.parseLong(context.pathParam("userId"));
    }
}