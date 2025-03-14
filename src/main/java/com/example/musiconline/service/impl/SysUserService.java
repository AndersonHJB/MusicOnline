package com.example.musiconline.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musiconline.domain.SysUser;
import com.example.musiconline.domain.vo.LoginVo;
import com.example.musiconline.model.LoginBody;

/**
 * 系统用户服务
 */
public interface SysUserService extends IService<SysUser> {


    void register(LoginBody registerBody);

    LoginVo login(LoginBody loginBody);

    void logout();
}
