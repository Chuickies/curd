package com.zcj.dao;

import com.zcj.domain.User;
import com.zcj.utils.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDao {
private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
    public List<User> queryAll() {
        List<User> userList = jdbcTemplate.query("SELECT * FROM tab_user;", new BeanPropertyRowMapper<>(User.class));
        return userList;
    }

    public User findById(String id) {
        User user = jdbcTemplate.queryForObject("select * from tab_user where id=?", new BeanPropertyRowMapper<>(User.class), id);
        return user;
    }

    public int updateUser(User user) {
        Object params[] = {user.getName(),user.getSex(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId()};
        int count = jdbcTemplate.update("UPDATE tab_user set name =?,sex=?,age=?,address=?,qq=?,email=? where id=?", params);
        return count;
    }

    public int add(User user) {
        Object params[]={user.getId(),user.getName(),user.getSex(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail()};
        int count = jdbcTemplate.update("insert into tab_user(id,name,sex,age,address,qq,email)values (?,?,?,?,?,?,?)", params);
        return count;
    }

    public int delete(String id) {
        int count = jdbcTemplate.update("delete from tab_user where id=?", id);
        return count;
    }
}
