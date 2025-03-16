package com.example.musiconline.domain.bo;

import lombok.Data;

/**
 * 修改密码的BO
 */
@Data
public class UpdatePasswordBo {

    private String originalPassword;

    private String newPassword;
}
