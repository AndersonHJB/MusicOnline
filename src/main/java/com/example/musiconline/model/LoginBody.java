package com.example.musiconline.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录对象
 */
@Data
@NoArgsConstructor
public class LoginBody {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;
    

    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 邮箱
     */
    private String userEmail;


}
