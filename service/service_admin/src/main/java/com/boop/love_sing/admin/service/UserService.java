package com.boop.love_sing.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boop.love_sing.common.entity.User;
import com.boop.love_sing.common.vo.UserVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author fubaiping
 * @since 2022-07-21
 */
public interface UserService extends IService<User> {

    Boolean register(UserVo userVo);

    String getUserId(String username);


}
