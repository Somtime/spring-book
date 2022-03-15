package toby.spring.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import toby.spring.spring.model.User;

import java.sql.SQLException;

@SpringBootTest
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    void register() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        /*UserDao dao = new DaoFactory().userDao();*/
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();

        user.setId("test1");
        user.setName("유건희");
        user.setPassword("1234");

        dao.add(user);

        System.out.println(user.getId() + " user add complete");
    }
}
