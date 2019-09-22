package dev.anthonybruno.gymbuddy.user;

import dev.anthonybruno.gymbuddy.user.model.UserDetails;

public class UserServiceImpl implements UserService {

    UserRepository userRepository = new UserRepository();

    @Override
    public UserDetails addUser(String email, String password) {
        userRepository.addUser(email, password);
        return new UserDetails(0, email);
    }

    @Override
    public UserDetails editUser(long userId, UserDetails newDetails) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserDetails getUser(long userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(long userId) {
        throw new UnsupportedOperationException();
    }
}
