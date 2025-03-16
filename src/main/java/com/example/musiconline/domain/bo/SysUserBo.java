package com.example.musiconline.domain.bo;

import com.example.musiconline.config.mybatis.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lds
 * @date 2025/3/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserBo extends PageQuery {

    private Long userId;

    private String userName;

    private String userEmail;

    private String userPhone;

    private Integer userRole;

    private Integer userStatus;

    private String userPassword;

}
