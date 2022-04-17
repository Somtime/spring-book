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

import javax.sql.DataSource;

@Configuration
public class DaoFactory {
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/toby_spring");
        dataSource.setUsername("springbook");
        dataSource.setPassword("password");

        return dataSource;
    }

    @Bean
    public UserDaoJdbc userDao() {
        UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
        userDaoJdbc.setDataSource(dataSource());
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
}