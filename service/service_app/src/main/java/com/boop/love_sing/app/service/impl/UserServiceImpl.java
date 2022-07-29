package com.boop.love_sing.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boop.love_sing.app.mapper.UserMapper;
import com.boop.love_sing.app.service.UserService;
import com.boop.love_sing.common.entity.User;
import com.boop.love_sing.common.util.SaltUtils;
import com.boop.love_sing.common.vo.UserVo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


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

    @Override
    public User getUser(String username) {
        return baseMapper.getUser(username);
    }

    @Override
    public Boolean register(UserVo uservo) {
        User user = new User();
        BeanUtils.copyProperties(uservo,user);
        String salt = SaltUtils.getSalt();
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        user.setSalt(salt);
        return this.save(user);
    }
}
