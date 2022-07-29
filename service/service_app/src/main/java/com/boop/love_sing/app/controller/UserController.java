package com.boop.love_sing.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boop.love_sing.app.service.UserService;
import com.boop.love_sing.common.entity.User;
import com.boop.love_sing.common.enums.ResultCode;
import com.boop.love_sing.common.result.Result;
import com.boop.love_sing.common.util.JedisUtil;
import com.boop.love_sing.common.util.JwtUtil;
import com.boop.love_sing.common.vo.UserVo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author fubaiping
 * @since 2022-07-21
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public Result<Object> create(@Validated @RequestBody UserVo uservo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", uservo.getUsername());
        User one = userService.getOne(queryWrapper);
        if (one != null) {
            return Result.fail(null).message("用户名已存在");
        }
        Boolean success = userService.register(uservo);
        if (!success) {
            return Result.fail(null).message("注册失败！");
        }
        return Result.ok(null).message("注册成功！");
    }

    @PostMapping("/login")
    public Result<Object> login(@RequestBody UserVo userVo, HttpServletResponse httpServletResponse) {
        if (userVo.getUsername()==null||userVo.getPassword()==null){
            return Result.fail(ResultCode.USER_LOGIN_ERROR);
        }
        User user = userService.getUser(userVo.getUsername());
        if (user == null) {
            return Result.fail(null).message("用户不存在");
        }
        Md5Hash md5Hash = new Md5Hash(userVo.getPassword(), user.getSalt(), 1024);
        if (!md5Hash.toHex().equals(user.getPassword())) {
            return Result.fail(null).message("密码错误");
        }
        if (JedisUtil.exists("shiro:cache:" + userVo.getUsername())) {
            JedisUtil.delKey("shiro:cache:" + userVo.getUsername());
        }
        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject("shiro:refresh_token:" + userVo.getUsername(), currentTimeMillis, 1800);
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(userVo.getUsername(), currentTimeMillis);
        httpServletResponse.setHeader("Authorization", token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        return Result.ok(token);
    }

    @GetMapping("/selectUser/{username}")
    public Result<Object> selectRole(@PathVariable String username) {
        User user = userService.getUser(username);
        return Result.ok(user);
    }

    @GetMapping("/getUserById/{id}")
    public Result<User> getUserById(@PathVariable String id) {
        User user = userService.getById(id);
        return Result.ok(user);
    }

    @PutMapping("/updateUser")
    public Result<Boolean> updateUser(@Validated @RequestBody UserVo userVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userVo.getUsername());
        User user = userService.getOne(queryWrapper);
        BeanUtils.copyProperties(userVo, user);
        boolean update = userService.updateById(user);
        return Result.ok(update);
    }
}

