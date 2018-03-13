package au.com.anthonybruno.gymbuddy.user;

import au.com.anthonybruno.gymbuddy.auth.UserDetails;
import au.com.anthonybruno.gymbuddy.json.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Context;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void addUser(Context context) {
        Json json = new Json(context.body());
        ObjectNode objectNode = json.asObject();
        String username = objectNode.get("username").asText();
        String password = objectNode.get("password").asText();
        String email = objectNode.get("email").asText();
        userService.addUser(username, password, email);

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
        return Long.parseLong(context.param("userId"));
    }
}