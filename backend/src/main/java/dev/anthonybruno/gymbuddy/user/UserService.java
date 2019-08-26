package dev.anthonybruno.gymbuddy.user;

import dev.anthonybruno.gymbuddy.user.model.UserDetails;

public interface UserService {

    UserDetails addUser(String email, String password);

    UserDetails editUser(long userId, UserDetails newDetails);

    UserDetails getUser(long userId);

    void deleteUser(long userId);

}
