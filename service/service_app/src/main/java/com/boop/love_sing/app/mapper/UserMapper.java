package com.boop.love_sing.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boop.love_sing.common.entity.User;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author fubaiping
 * @since 2022-07-21
 */
public interface UserMapper extends BaseMapper<User> {
    User getUser(String username);
}
