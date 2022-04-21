package toby.spring.spring.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import toby.spring.spring.model.Level;
import toby.spring.spring.model.User;
import toby.spring.spring.service.sqlservice.SqlService;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private SqlService sqlService;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setSqlService(SqlService sqlService) {
        this.sqlService = sqlService;
    }

    public void add(User user) {
        this.jdbcTemplate.update(
                this.sqlService.getSql("userAdd"),
                user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }


    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                this.sqlService.getSql("userGet"),
                new Object[]{id},
                userMapper
        );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
                this.sqlService.getSql("userGetAll"),
                userMapper
        );
    }

    public void deleteAll() {
        this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGetCount"), Integer.class);
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                this.sqlService.getSql("userUpdate"),
                user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
    }

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            return user;
        }
    };


}
