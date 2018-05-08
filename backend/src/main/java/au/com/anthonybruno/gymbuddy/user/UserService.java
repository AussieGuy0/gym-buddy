package au.com.anthonybruno.gymbuddy.user;

import au.com.anthonybruno.gymbuddy.user.model.UserDetails;

public interface UserService {

    UserDetails addUser(String username, String password, String email);

    UserDetails editUser(long userId, UserDetails newDetails);

    UserDetails getUser(long userId);

    void deleteUser(long userId);

}
