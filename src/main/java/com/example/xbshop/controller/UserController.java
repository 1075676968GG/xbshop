package com.example.xbshop.controller;

import com.example.xbshop.entity.Result;
import com.example.xbshop.entity.User;
import com.example.xbshop.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author liao
 * @date 2020/12/6 9:22
 * @Description
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpSession session;

    @GetMapping("/checkEmail/{email}")
    public Result checkEmail(@PathVariable String email){
        User user=userService.findByEmail(email);
        if(user!=null){
            return new Result(true,"该邮箱已注册");
        }
        return new Result(true,"该邮箱未注册");
    }

    @GetMapping("/checkUsername/{username}")
    public Result checkUsername(@PathVariable String username){
        User user=userService.findByUsername(username);
        if(user!=null){
            return new Result(true,"用户名已注册");
        }
        return new Result(false,"用户名未注册");
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        userService.save(user);
        return new Result(true,"注册成功");
    }

    @PostMapping("/sendEmail/{email}")
    public Result sendEmail(@PathVariable String email){
        System.out.println("邮箱"+email);
        String code= RandomStringUtils.randomNumeric(4);
        redisTemplate.opsForValue().set("user:updatePassword:code:"+email,code);
        System.out.println(code);
        return new Result(true,"发送成功",code);
    }

    @PostMapping("/updatePassword/{code}")
    public Result updatePassword(@PathVariable String code, @RequestBody User user) {
        Object codeInRedis = redisTemplate.opsForValue().get("user:updatePassword:code:") + user.getEmail();
        if (code.equals(codeInRedis)) {
            userService.updatePassword(user.getEmail(), user.getPassword());
            return new Result(true, "修改密码成功");

        }
        return new Result(false, "验证码错误");
    }

    @PostMapping("/login/{code}")
    public Result login(@PathVariable String code,@RequestBody User user){
        String username=user.getUsername();
        String password=user.getPassword();

        Object sessionInCode = session.getAttribute("safeCode");
        if(!code.equalsIgnoreCase(String.valueOf(sessionInCode))){
            return new Result(false,"验证码错误");
        }
        User dbUser=userService.findByUsername(username);
        if(dbUser==null||!dbUser.getPassword().equals(password)){
            return new Result(false,"用户名或密码错误");
        }
        redisTemplate.opsForValue().set("user:loginUser:" + dbUser.getId(), dbUser, 30, TimeUnit.MINUTES);
        userService.updateLoginTime(dbUser.getId());
        Map returnMap=new HashMap();
        returnMap.put("userId",dbUser.getId());
        dbUser.setPassword(null);
        returnMap.put("loginUser",dbUser);
        return new Result(true, "登录成功", returnMap);
    }


}
