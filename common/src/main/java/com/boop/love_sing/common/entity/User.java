package com.boop.love_sing.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
}
