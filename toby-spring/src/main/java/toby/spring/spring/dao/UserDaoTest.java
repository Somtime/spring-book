package toby.spring.spring.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import toby.spring.spring.model.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        /*UserDao dao = new DaoFactory().userDao();*/
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();

        user.setId("ascas1342");
        user.setName("유건희");
        user.setPassword("1234");

        dao.add(user);

        System.out.println(user.getId() + " user add complete");
    }
}
