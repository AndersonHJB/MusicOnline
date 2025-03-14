package com.example.musiconline.service;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.musiconline.config.satoken.utils.LoginHelper;
import com.example.musiconline.domain.vo.LoginVo;
import com.example.musiconline.model.LoginBody;
import com.example.musiconline.model.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musiconline.mapper.SysUserMapper;
import com.example.musiconline.domain.SysUser;
import com.example.musiconline.service.impl.SysUserService;

/**
 * 系统用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    private final SysUserMapper sysUserMapper;

    @Override
    public void register(LoginBody registerBody) {

        if (registerBody.getUserName() == null || registerBody.getPassword() == null) {
            throw new RuntimeException("用户名或密码不能为空");
        }
        // 判断用户名是否唯一
        if (!checkUserNameUnique(registerBody)) {
            throw new RuntimeException("用户名已存在");
        }
        // 保存用户
        SysUser sysUser = new SysUser();
        sysUser.setUserName(registerBody.getUserName());
        sysUser.setPassword(registerBody.getPassword());
        sysUser.setEmail(registerBody.getEmail());
        sysUser.setPhoneNumber(registerBody.getPhoneNumber());
        sysUserMapper.insert(sysUser);
    }

    @Override
    public LoginVo login(LoginBody loginBody) {


        String username = loginBody.getUserName();
        String password = loginBody.getPassword();

        SysUser userInfo = getUserInfo(username);
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(userInfo, loginUser);

        boolean equals = password.equals(userInfo.getPassword());
        if (!equals){
            throw new RuntimeException("密码错误");
        }

        SaLoginModel model = new SaLoginModel();
        model.setTimeout(30 * 60);
        model.setActiveTimeout(30 * 60);
        model.setExtra(LoginHelper.CLIENT_KEY, 1);
        // 生成token
        LoginHelper.login(loginUser, model);

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        return loginVo;
    }

    @Override
    public void logout() {

        try {
            StpUtil.logout();
        } catch (NotLoginException ignored) {
        }
    }

    private SysUser getUserInfo(String username) {

        // 通过用户名查询用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, username);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if (sysUser == null){
            throw new RuntimeException("用户不存在");
        }
        return sysUser;
    }


    private boolean checkUserNameUnique(LoginBody user) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, user.getUserName()));
        return !exist;
    }
}
