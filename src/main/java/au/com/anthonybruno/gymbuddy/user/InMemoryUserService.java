package au.com.anthonybruno.gymbuddy.user;

import au.com.anthonybruno.gymbuddy.auth.UserDetails;

public class InMemoryUserService implements UserService {

    @Override
    public UserDetails addUser(String username, String password, String email) {
        return null;
    }

    @Override
    public UserDetails editUser(long userId, UserDetails newDetails) {
        return null;
    }

    @Override
    public UserDetails getUser(long userId) {
        return null;
    }

    @Override
    public void deleteUser(long userId) {

    }
}
