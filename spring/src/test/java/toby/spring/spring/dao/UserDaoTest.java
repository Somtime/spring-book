package toby.spring.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import toby.spring.spring.model.Level;
import toby.spring.spring.model.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class) // Configuration Class 지정 인듯?
/*@ContextConfiguration(locations="applicationContext.xml") // Configuration Xml*/
/*@DirtiesContext // 애플리케이션 컨텍스트의 구성이나 상태 변경 을 직접 한다고 선언!*/
public class UserDaoTest {
    private User user1;
    private User user2;
    private User user3;
    private User user4;

    ApplicationContext context;

    @Autowired UserDao dao;
    @Autowired DataSource dataSource;

    @BeforeEach
    public void setUp() {
        this.context = new AnnotationConfigApplicationContext(DaoFactory.class);
        /*this.context = new GenericXmlApplicationContext("applicationContext.xml");*/
        this.dao = context.getBean("userDao", UserDao.class);
        this.dao.deleteAll();

        this.user1 = new User("id1", "name1", "pass1", Level.BASIC, 1, 0);
        this.user2 = new User("id2", "name2", "pass2", Level.SILVER, 55, 10);
        this.user3 = new User("id3", "name3", "pass3", Level.GOLD, 100, 40);
        this.user4 = new User("id1", "name1", "pass1");
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userGet1 = dao.get(user1.getId());
        checkSameUser(userGet1, user1);

        User userGet2 = dao.get(user2.getId());
        checkSameUser(userGet2, user2);
    }

    // getCount() 테스트
    @Test
    public void count() throws SQLException, ClassNotFoundException {
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
        assertThat(dao.getCount(), is(0));

        assertThrows(EmptyResultDataAccessException.class, () -> {
            dao.get("unknown_id");
        });

    }

    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user1, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user3, users3.get(2));
    }

    @Test
    public void duplicateKey() throws DuplicateKeyException {
        assertThrows(DuplicateKeyException.class, () -> {
            dao.add(user1);
            dao.add(user1);
        });
    }

    @Test
    public void sqlExceptionTranslate() {
        try {
            dao.add(user1);
            dao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlEx = (SQLException) e.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            assertThat(set.translate(null, null, sqlEx), instanceOf(DuplicateKeyException.class));
        }
    }

    @Test
    public void update() {
        dao.add(user1);
        dao.add(user2);

        user1.setName("유건희");
        user1.setPassword("password");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);

        dao.update(user1);

        User user1update = dao.get(user1.getId());
        checkSameUser(user1, user1update);

        User user2update = dao.get(user2.getId());
        checkSameUser(user2, user2update);
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
        assertThat(user1.getLevel(), is(user2.getLevel()));
        assertThat(user1.getLogin(), is(user2.getLogin()));
        assertThat(user1.getRecommend(), is(user2.getRecommend()));
    }
}
