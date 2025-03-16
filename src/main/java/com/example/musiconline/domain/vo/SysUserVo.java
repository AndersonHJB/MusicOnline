package com.example.musiconline.domain.vo;

import com.example.musiconline.domain.SysUser;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Date;

/**
 * 用户对象
 */
@Data
@AutoMapper(target = SysUser.class)
public class SysUserVo {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 手机号码
     */
    private String userPhone;


    /**
     * 用户角色 （0代表普通用户 1代表管理员）
     */
    private Integer userRole;

    /**
     * 活跃状态（0代表禁用 1代表活跃）
     */
    private Integer userStatus;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;
}
