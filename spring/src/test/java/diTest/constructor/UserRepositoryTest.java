package diTest.constructor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import toby.spring.spring.di.constructor.User;
import toby.spring.spring.di.constructor.UserRepository;
import toby.spring.spring.di.constructor.UserService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.sql.DataSource;
import java.sql.SQLException;

public class UserRepositoryTest {
    DataSource dataSource;
    UserService userService;

    @BeforeEach
    public void jdbc() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://172.30.157.57/toby_spring");
        dataSource.setUsername("springbook");
        dataSource.setPassword("password");

        this.dataSource = dataSource;

        UserRepository userRepository = new UserRepository(dataSource);
        this.userService = new UserService(userRepository);
    }

    @AfterEach
    public void clear() throws SQLException {
        UserRepository userRepository = new UserRepository(dataSource);
        userRepository.deleteAll();
    }

    @Test
    public void register() throws SQLException {

        User user1 = new User("id1", "name1", "password1");
        User user2 = new User("id2", "name2", "password2");
        User user3 = new User("id3", "name3", "password3");

        userService.save(user1);
        assertThat(userService.count(), is(1));

        userService.save(user2);
        assertThat(userService.count(), is(2));

        userService.save(user3);
        assertThat(userService.count(), is(3));
    }
}
