package toby.spring.spring.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import toby.spring.spring.model.User;
import toby.spring.spring.service.UserService;
import toby.spring.spring.service.UserServiceImpl;
import toby.spring.spring.service.UserServiceTx;
import toby.spring.spring.service.sqlservice.SimpleSqlService;
import toby.spring.spring.service.sqlservice.SqlService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DaoFactory {
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://172.26.1.184/toby_spring");
        dataSource.setUsername("springbook");
        dataSource.setPassword("password");

        return dataSource;
    }

    @Bean
    public UserDaoJdbc userDao() {
        UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
        userDaoJdbc.setDataSource(dataSource());
        userDaoJdbc.setSqlService(sqlService());
        return userDaoJdbc;
    }

    @Bean
    public UserServiceImpl userServiceImpl() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.setUserDao(userDao());
        return userServiceImpl;
    }

    @Bean
    public UserServiceTx userService() {
        UserServiceTx userServiceTx = new UserServiceTx();
        userServiceTx.setUserService(userServiceImpl());
        userServiceTx.setTransactionManager((PlatformTransactionManager) transactionManager());
        return userServiceTx;
    }

    @Bean
    public TransactionManager transactionManager() {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        /*PlatformTransactionManager transactionManager = new JtaTransactionManager(); // JTA 이용시*/

        return transactionManager;
    }

    @Bean
    public SimpleSqlService sqlService() {
        SimpleSqlService sqlService = new SimpleSqlService();
        sqlService.setSqlMap(sqlMap());
        return sqlService;
    }

    @Bean
    public Map<String, String> sqlMap() {
        Map<String, String> sqlMap = new HashMap<>();

        sqlMap.put("userAdd", "INSERT INTO users(id, name, password, level, login, recommend) VALUES(?, ?, ?, ?, ?, ?)");
        sqlMap.put("userGet", "SELECT * FROM users WHERE id = ?");
        sqlMap.put("userGetAll", "SELECT * FROM users");
        sqlMap.put("userDeleteAll", "DELETE FROM users");
        sqlMap.put("userGetCount", "SELECT COUNT(*) FROM users");
        sqlMap.put("userUpdate", "UPDATE users SET name = ?, password = ?, level = ?, login = ?, recommend = ? WHERE id = ?");

        return sqlMap;
    }
}