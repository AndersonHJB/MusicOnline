package com.example.musiconline.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.domain.SysUser;
import com.example.musiconline.domain.bo.SysUserBo;
import com.example.musiconline.domain.bo.UpdatePasswordBo;
import com.example.musiconline.domain.vo.LoginVo;
import com.example.musiconline.domain.vo.SysUserVo;
import com.example.musiconline.model.LoginBody;

import java.util.List;

/**
 * 系统用户服务
 */
public interface SysUserService extends IService<SysUser> {


    void register(SysUserBo registerBody);

    LoginVo login(LoginBody loginBody);

    void logout();

    TableDataInfo<SysUser> getUserList(SysUserBo bo);

    SysUserVo getUserInfoById(String id);

    void saveUser(SysUserBo user);

    void updateUser(SysUserBo bo);

    void deleteUser(List<Long> ids);

    void resetPassword(Long id);

    void updatePassword(UpdatePasswordBo bo);
}
