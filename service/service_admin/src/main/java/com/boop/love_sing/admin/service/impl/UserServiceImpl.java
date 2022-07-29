package com.boop.love_sing.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boop.love_sing.admin.mapper.UserMapper;
import com.boop.love_sing.admin.service.UserRoleService;
import com.boop.love_sing.admin.service.UserService;
import com.boop.love_sing.common.entity.User;
import com.boop.love_sing.common.entity.UserRole;
import com.boop.love_sing.common.util.SaltUtils;
import com.boop.love_sing.common.vo.UserVo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author fubaiping
 * @since 2022-07-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    UserRoleService userRoleService;

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public Boolean register(UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo,user);
        String salt = SaltUtils.getSalt();
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        user.setSalt(salt);
        this.save(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(this.getUserId(user.getUsername()));
        userRole.setRoleId("2");
        return userRoleService.save(userRole);
    }

    @Override
    public String getUserId(String username) {
        return this.getOne(new QueryWrapper<User>().eq("username", username)).getId();
    }

}
