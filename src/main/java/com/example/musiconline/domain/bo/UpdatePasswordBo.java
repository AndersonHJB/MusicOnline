package com.example.musiconline.domain.bo;

import lombok.Data;

/**
 * @author lds
 * @date 2025/3/15
 */
@Data
public class UpdatePasswordBo {

    private String originalPassword;

    private String newPassword;
}
