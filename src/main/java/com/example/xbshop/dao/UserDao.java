package com.example.xbshop.dao;

import com.example.xbshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * @author liao
 * @date 2020/12/6 9:22
 * @Description
 */
public interface UserDao extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);

    @Query("update User u set u.loginTime=?2 where u.id=?1")
    @Modifying
    void updateLoginTime(Long userId, Date date);

    @Query("update User u set u.password=?2 where u.email=?1")
    @Modifying
    void updatePassword(String email, String password);
}
