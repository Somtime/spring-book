package toby.spring.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import toby.spring.spring.model.User;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class) // Configuration Class 지정 인듯?
@DirtiesContext // 애플리케이션 컨텍스트의 구성이나 상태 변경 을 직접 한다고 선언!
public class UserDaoTest {
    private User user1;
    private User user2;
    private User user3;

    @Autowired
    ApplicationContext context;

    @Autowired
    UserDao dao;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = new SingleConnectionDataSource(
                "jdbc:mysql://172.19.143.49/toby_spring",
                "root",
                "password",
                true
        ); // 오브젝트 직접 생성

        dao.setDataSource(dataSource);

        this.user1 = new User("id1", "name1", "pass1");
        this.user2 = new User("id2", "name2", "pass2");
        this.user3 = new User("id3", "name3", "pass3");

        System.out.println(this.context);
        System.out.println(this);
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userGet1 = dao.get(user1.getId());
        assertThat(userGet1.getName(), is(user1.getName()));
        assertThat(userGet1.getPassword(), is(user1.getPassword()));

        User userGet2 = dao.get(user2.getId());
        assertThat(userGet2.getName(), is(user2.getName()));
        assertThat(userGet2.getPassword(), is(user2.getPassword()));
    }

    // getCount() 테스트
    @Test
    public void count() throws SQLException, ClassNotFoundException {


        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }

    // 예외
    @Test
    public void getUserFailure() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        assertThrows(EmptyResultDataAccessException.class, () -> {
            dao.get("unknown_id");
        });

    }


}
