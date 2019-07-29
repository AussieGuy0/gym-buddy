package dev.anthonybruno.gymbuddy.user;

import dev.anthonybruno.gymbuddy.user.model.UserDetails;

public class UserServiceImpl implements UserService {

    UserRepository userRepository = new UserRepository();

    @Override
    public UserDetails addUser(String username, String password, String email) {
        userRepository.addUser(username, password, email);
        return new UserDetails(0, username, email);
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
