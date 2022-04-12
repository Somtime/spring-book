package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import toby.spring.spring.dao.DaoFactory;
import toby.spring.spring.dao.UserDao;
import toby.spring.spring.model.Level;
import toby.spring.spring.model.User;
import toby.spring.spring.service.UserService;

import static org.assertj.core.api.Assertions.fail;
import static toby.spring.spring.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static toby.spring.spring.service.UserService.MIN_RECOMMEND_FOR_GOLD;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
/*@ContextConfiguration(locations = "/toby/spring/spring/dao/applicationContext.xml")*/
public class UserServiceTest {
    @Autowired UserService userService;
    @Autowired UserDao userDao;
    @Autowired
    PlatformTransactionManager transactionManager;
    List<User> users;

    @BeforeEach
    public void serUp() {
        userDao.deleteAll();

        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
                new User("joytouce", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1),
                new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
                new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void upgradeLevels() throws Exception {
        for (User user : users) userDao.add(user);

        userService.setTransactionManager(transactionManager);
        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    @Test
    public void add() {
        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
    }

    @Test
    public void upgradeAllOrNothing() throws Exception {
        UserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.userDao);
        /*testUserService.setDataSource(this.dataSource);*/
        testUserService.setTransactionManager(transactionManager);

        for (User user : users) userDao.add(user);

        try {
            testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (TestUserService.TestUserServiceException e) {
        }

        checkLevelUpgraded(users.get(1), false);
    }

    static class TestUserService extends UserService {
        private String id;

        public TestUserService(String id) {
            this.id = id;
        }

        @Override
        protected boolean canUpgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            return super.canUpgradeLevel(user);
        }

        private class TestUserServiceException extends RuntimeException {
        }
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());

        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }
}
