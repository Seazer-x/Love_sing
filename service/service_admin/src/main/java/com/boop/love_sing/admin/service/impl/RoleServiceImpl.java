package com.boop.love_sing.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boop.love_sing.admin.mapper.RoleMapper;
import com.boop.love_sing.admin.service.RoleService;
import com.boop.love_sing.common.entity.Role;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author fubaiping
 * @since 2022-07-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
