package com.boop.love_sing.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boop.love_sing.admin.service.UserRoleService;
import com.boop.love_sing.admin.service.UserService;
import com.boop.love_sing.common.entity.User;
import com.boop.love_sing.common.result.Result;
import com.boop.love_sing.common.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author fubaiping
 * @since 2022-07-29
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {

    UserService userService;
    UserRoleService userRoleService;

    @Autowired
    private void setUserService(UserService userService,UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/getUsers/{page}/{size}")
    public Result<IPage<User>> getUsers(@PathVariable Integer page,
                                        @PathVariable Integer size,
                                        @RequestBody UserVo userVo) {
        Page<User> pageParam = new Page<>(page, size);
        Set<String> userId= new HashSet<>();
        userRoleService.list().forEach(userRole -> {
            if (userRole.getRoleId().equals("3")||userRole.getRoleId().equals("4")) {
                userId.add(userRole.getUserId());
            }
        });
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userId);
        if (userVo.getUsername() != null) {
            queryWrapper.like("username", userVo.getUsername());
        }
        if (userVo.getNickname() != null) {
            queryWrapper.like("nickname", userVo.getNickname());
        }
        if (userVo.getGender() != null) {
            queryWrapper.eq("gender", userVo.getGender());
        }
        IPage<User> usersPage = userService.page(pageParam, queryWrapper);
        return Result.ok(usersPage);
    }

}

