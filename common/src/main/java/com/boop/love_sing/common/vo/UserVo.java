package com.boop.love_sing.common.vo;

import com.boop.love_sing.common.enums.Gender;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserVo {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4,max = 12,message = "用户名长度应在4到12个字符")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6,max = 18,message = "密码长度应在6到18个字符")
    private String password;

    private Gender gender;

    private String nickname;
}
