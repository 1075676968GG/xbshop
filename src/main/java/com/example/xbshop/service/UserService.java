package com.example.xbshop.service;

import com.example.xbshop.dao.UserDao;
import com.example.xbshop.entity.User;
import com.example.xbshop.mapper.UserMapper;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * @author liao
 * @date 2020/12/6 9:22
 * @Description
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional
    public void save(User user) {
        user.setIsSecret("0");
        user.setLook(0L);
        user.setRegisterTime(new Date());
        if (StringUtils.isNullOrEmpty(user.getPic())) {
            user.setPic("https://www.baidu.com/favicon.ico");
        }
        userDao.save(user);
    }

    public User findById(Long id) {
        return userDao.findById(id).get();
    }

    @Transactional
    public void updateLoginTime(Long userId) {
        userDao.updateLoginTime(userId,new Date());
    }

    @Transactional
    public void updatePassword(String email, String password) {
        userDao.updatePassword(email,password);
    }
}
