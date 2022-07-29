package com.boop.love_sing.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boop.love_sing.admin.service.UserRoleService;
import com.boop.love_sing.admin.service.UserService;
import com.boop.love_sing.common.entity.User;
import com.boop.love_sing.common.enums.ResultCode;
import com.boop.love_sing.common.result.Result;
import com.boop.love_sing.common.util.JedisUtil;
import com.boop.love_sing.common.util.JwtUtil;
import com.boop.love_sing.common.vo.UserVo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author fubaiping
 * @since 2022-07-21
 */
@RestController
@RequestMapping("/admin/system")
public class AdminController {

    UserService userService;
    UserRoleService userRoleService;

    @Autowired
    private void setUserService(UserService userService,UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @RequiresRoles("superadmin")
    @PutMapping("/createAdmin")
    public Result<Boolean> createAdmin(@Validated @RequestBody UserVo userVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userVo.getUsername());
        User one = userService.getOne(queryWrapper);
        if (one != null) {
            return Result.fail(false).message("管理员用户名已存在");
        }
        Boolean success = userService.register(userVo);
        if (!success) {
            return Result.fail(false).message("添加失败!");
        }
        return Result.ok(true).message("添加成功!");
    }

    @RequiresRoles("superadmin")
    @PutMapping("/deleteAdmin/{id}")
    public Result<Boolean> deleteAdmin(@PathVariable("id") String id) {
        boolean success = userService.removeById(id);
        if (!success) {
            return Result.fail(false).message("删除失败!");
        }
        return Result.ok(true).message("删除成功!");
    }

    @RequiresRoles("admin")
    @PutMapping("/updateAdmin")
    public Result<Boolean> updateAdmin(@Validated @RequestBody UserVo userVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userVo.getUsername());
        User user = userService.getOne(queryWrapper);
        BeanUtils.copyProperties(userVo, user);
        boolean update = userService.updateById(user);
        return Result.ok(update);
    }

    @GetMapping("/getAdmins/{page}/{size}")
    public Result<IPage<User>> getAdmins( @PathVariable Integer page,
                                  @PathVariable Integer size) {
        Page<User> pageParam = new Page<>(page, size);
        List<String> adminId= new ArrayList<>();
        userRoleService.list().forEach(userRole -> {
            if (userRole.getRoleId().equals("1")) {
                adminId.add(userRole.getUserId());
            }
        });
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", adminId);
        IPage<User> adminsPage = userService.page(pageParam, queryWrapper);
        return Result.ok(adminsPage);
    }

}

