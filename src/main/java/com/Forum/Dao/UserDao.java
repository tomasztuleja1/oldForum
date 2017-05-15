package com.Forum.Dao;

import com.Forum.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Tomal on 2017-05-13.
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addUser(String username, String pass) {
        final String sql = "INSERT INTO user (user_name,user_password) VALUES (?,?)";

        String name = username;
        String password = pass;

        jdbcTemplate.update(sql, new Object[]{name, password});


    }

    public Collection<User> getAllUsers() {
        String sql = "SELECT * FROM user";

        List<User> users = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper(User.class));

        return users;
    }

    public void disableUserByName(String user_name) {
        final String sql = "UPDATE user SET user_status=0 WHERE user_name = ?";
        jdbcTemplate.update(sql, user_name);

    }
}
