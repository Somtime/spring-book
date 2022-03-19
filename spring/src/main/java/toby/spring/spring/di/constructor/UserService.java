package toby.spring.spring.di.constructor;

import java.sql.SQLException;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) throws SQLException {
        userRepository.save(user);
    }

    public User findById(String id) throws SQLException {
        return userRepository.findById(id);
    }

    public int count() throws SQLException {
        return userRepository.count();
    }

    public void deleteAll() throws SQLException {
        userRepository.deleteAll();
    }
}
