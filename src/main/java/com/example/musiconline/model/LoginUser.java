package com.example.musiconline.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息
 *
 * @author ll
 */
@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 用户ID
     */
    private Long userId;


    /**
     * 用户唯一标识
     */
    private String token;



    /**
     * 用户名
     */
    private String userName;


    /**
     * 密码
     */
    private String password;



    /**
     * 获取登录id
     */
    public Long getLoginId() {
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        return userId;
    }

}
