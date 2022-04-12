package toby.spring.spring.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import toby.spring.spring.service.UserService;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://172.24.188.66/toby_spring");
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
    public UserService userService() {
        UserService userService = new UserService();
        userService.setUserDao(userDao());
        return userService;
    }

    @Bean
    public TransactionManager transactionManager() {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        /*PlatformTransactionManager transactionManager = new JtaTransactionManager(); // JTA 이용시*/

        return transactionManager;
    }
}