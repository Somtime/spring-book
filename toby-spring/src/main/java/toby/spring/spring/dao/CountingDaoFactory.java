package toby.spring.spring.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {
    /*@Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }*/

    @Bean
    private ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    private ConnectionMaker realConnectionMaker() {
        return new DConnectionMaker();
    }

}
