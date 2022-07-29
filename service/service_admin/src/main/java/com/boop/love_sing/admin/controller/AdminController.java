package com.boop.love_sing.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boop.love_sing.admin.service.RoleService;
import com.boop.love_sing.admin.service.UserService;
import com.boop.love_sing.common.entity.User;
import com.boop.love_sing.common.result.Result;
import com.boop.love_sing.common.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author fubaiping
 * @since 2022-07-21
 */
@RestController
@RequestMapping("/admin/user")
public class AdminController {

    UserService userService;
    RoleService roleService;

    @Autowired
    private void setUserService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

    @PutMapping("/updateAdmin")
    public Result<Boolean> updateAdmin(@Validated @RequestBody UserVo userVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userVo.getUsername());
        User user = userService.getOne(queryWrapper);
        BeanUtils.copyProperties(userVo, user);
        roleService.update();
        boolean update = userService.updateById(user);
        return Result.ok(update);
    }
}

