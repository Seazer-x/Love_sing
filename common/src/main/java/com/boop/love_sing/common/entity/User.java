package com.boop.love_sing.common.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.boop.love_sing.common.base.BaseEntity;
import com.boop.love_sing.common.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author fubaiping
 * @since 2022-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 权限
     */
    @TableField(value="roles",exist = false)
    private List<Role> roles;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 加密后的密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别
     */
    @TableField("gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * 是否锁定,1-是,O-否
     */
    @TableField("locked")
    private Integer locked;

    /**
     * 最后登录IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 最后登录IP
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

}
