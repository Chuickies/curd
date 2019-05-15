package com.zcj.service;

import com.sun.org.apache.regexp.internal.RE;
import com.zcj.dao.UserDao;
import com.zcj.domain.User;

import java.util.List;

public class UserService {
    private UserDao dao = new UserDao();

    public List<User> queryAll() {
        List<User> userList = dao.queryAll();
        return userList;
    }

    public User findByid(String id) {
        User user = dao.findById(id);
        return  user;
    }

    public boolean updateUser(User user) {
        int count = dao.updateUser(user);
        return count==1;
    }

    public boolean add(User user) {
        int count =dao.add(user);
        return count==1;

    }

    public boolean delete(String id) {
        int count=dao.delete(id);
        return count==1;
    }
}
