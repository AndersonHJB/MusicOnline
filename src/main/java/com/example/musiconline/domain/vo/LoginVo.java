package com.example.musiconline.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 登录验证信息
 */
@Data
public class LoginVo {

    /**
     * 授权令牌
     */
    @JsonProperty("access_token")
    private String accessToken;

}
