package com.example.musiconline.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.example.musiconline.config.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户信息表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user")
public class SysUser extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户账号
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户邮箱
     */
    @TableField(value = "user_email")
    private String userEmail;

    /**
     * 手机号码
     */
    @TableField(value = "user_phone")
    private String userPhone;

    /**
     * 密码
     */
    @TableField(value = "user_password")
    private String userPassword;

    /**
     * 用户角色 （0代表普通用户 1代表管理员）
     */
    @TableField(value = "user_role")
    private Integer userRole;

    /**
     * 活跃状态（0代表禁用 1代表活跃）
     */
    @TableField(value = "user_status")
    private Integer userStatus;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    @TableField(value = "del_flag")
    private String delFlag;



}