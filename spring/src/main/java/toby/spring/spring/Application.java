package toby.spring.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import toby.spring.spring.dao.UserDao;
import toby.spring.spring.model.User;

import java.sql.SQLException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		UserDao dao = new UserDao();
		User user = new User();

		user.setId("geonhee");
		user.setName("유건희");
		user.setPassword("1234");

		dao.add(user);

		System.out.println(user.getId() + " 유저 등록 성공");
		/*SpringApplication.run(Application.class, args);*/
	}

}
