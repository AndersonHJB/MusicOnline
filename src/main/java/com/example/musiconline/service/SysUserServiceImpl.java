package com.example.musiconline.service;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.config.satoken.utils.LoginHelper;
import com.example.musiconline.domain.bo.SysUserBo;
import com.example.musiconline.domain.bo.UpdatePasswordBo;
import com.example.musiconline.domain.vo.LoginVo;
import com.example.musiconline.domain.vo.SysUserVo;
import com.example.musiconline.exception.ServiceException;
import com.example.musiconline.model.LoginBody;
import com.example.musiconline.model.LoginUser;
import com.example.musiconline.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musiconline.mapper.SysUserMapper;
import com.example.musiconline.domain.SysUser;
import com.example.musiconline.service.impl.SysUserService;

import java.util.List;
import java.util.Objects;

/**
 * 系统用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    private final SysUserMapper sysUserMapper;

    // 默认密码后缀
    @Value("${user.password.suffix}")
    private String passwordSuffix;

    @Override
    public void register(SysUserBo bo) {

        if (bo.getUserName() == null) {
            throw new RuntimeException("用户名不能为空");
        }
        // 判断用户名是否唯一
        if (checkUserNameUnique(bo)) {
            throw new RuntimeException("用户名已存在");
        }
        // 保存用户
        SysUser sysUser = new SysUser();
        sysUser.setUserName(bo.getUserName());
        sysUser.setUserPassword(bo.getUserName() + "_" + passwordSuffix);
        sysUser.setUserEmail(bo.getUserEmail());
        sysUser.setUserPhone(bo.getUserPhone());
        sysUserMapper.insert(sysUser);
    }

    @Override
    public LoginVo login(LoginBody loginBody) {


        String username = loginBody.getUserName();
        String userPassword = loginBody.getUserPassword();

        SysUser userInfo = getUserInfo(username);
        // 校验用户活跃状态
        if (Objects.equals(userInfo.getUserStatus(), 0)) {
            throw new ServiceException("用户已禁用");
        }
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(userInfo, loginUser);

        boolean equals = userPassword.equals(userInfo.getUserPassword());
        if (!equals){
            throw new RuntimeException("密码错误");
        }

        SaLoginModel model = new SaLoginModel();
        model.setTimeout(5 *60 * 1000);
        model.setActiveTimeout(5 *60 * 1000);
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

    @Override
    public TableDataInfo<SysUser> getUserList(SysUserBo bo) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(bo.getUserName()), SysUser::getUserName, bo.getUserName())
                .like(StringUtils.isNotEmpty(bo.getUserEmail()), SysUser::getUserEmail, bo.getUserEmail())
                .eq(ObjectUtils.isNotEmpty(bo.getUserRole()), SysUser::getUserRole, bo.getUserRole())
                .eq(ObjectUtils.isNotEmpty(bo.getUserStatus()), SysUser::getUserStatus, bo.getUserStatus());

        Page<SysUser> sysUserPage = sysUserMapper.selectPage(bo.build(), queryWrapper);

        return TableDataInfo.build(sysUserPage);
    }

    @Override
    public SysUserVo getUserInfoById(String id) {
        if (StringUtils.isEmpty(id)){
            throw new ServiceException("用户id不能为空");
        }
        return sysUserMapper.selectVoById(id);
    }

    @Override
    public void saveUser(SysUserBo bo) {
        if (bo.getUserName() == null) {
            throw new RuntimeException("用户名不能为空");
        }
        // 校验用户名是否唯一
        if (checkUserNameUnique(bo)) {
            throw new ServiceException("用户名已存在");
        }

        // 保存用户
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(bo, sysUser);
        // 设置默认密码
        sysUser.setUserPassword(sysUser.getUserName() + "_" + passwordSuffix);
        sysUserMapper.insert(sysUser);

    }

    @Override
    public void updateUser(SysUserBo bo) {
        // 用户id不为空
        if (Objects.isNull(bo.getUserId())){
            throw new ServiceException("用户id不能为空");
        }
        // 校验用户名是否唯一
        if (checkUserNameUnique(bo)) {
            throw new ServiceException("用户名已存在");
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(bo, sysUser);
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public void deleteUser(List<Long> ids) {

        // 校验是否有权限删除用户
        LoginHelper.getLoginUser();
        if (!LoginHelper.isAdmin()) {
            throw new ServiceException("没有权限删除用户");
        }
        // 无法删除自己，以及其他管理员
        List<SysUser> sysUsers = sysUserMapper.selectBatchIds(ids);
        sysUsers.forEach(sysUser -> {
            if (sysUser.getUserId().equals(LoginHelper.getUserId())) {
                throw new ServiceException("无法删除自己");
            }
            if (sysUser.getUserRole() == 1) {
                throw new ServiceException("无法删除其他管理员");
            }
        });
        sysUserMapper.deleteBatchIds(ids);
    }

    @Override
    public void resetPassword(Long id) {
        // 校验是否有权限重置密码
        LoginHelper.getLoginUser();
        if (!LoginHelper.isAdmin()) {
            throw new ServiceException("没有权限重置密码");
        }
        // 查询用户信息
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            throw new ServiceException("用户不存在");
        }
        sysUserMapper.update(null, new LambdaUpdateWrapper<SysUser>()
                .set(SysUser::getUserPassword, sysUser.getUserName() + "_" + passwordSuffix)
                .eq(SysUser::getUserId, id));
    }

    @Override
    public void updatePassword(UpdatePasswordBo bo) {
        // 获取用户信息
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUser sysUser = sysUserMapper.selectById(loginUser.getUserId());
        if (sysUser == null) throw new ServiceException("用户不存在");
        if (!sysUser.getUserPassword().equals(bo.getOriginalPassword())){
            throw new ServiceException("旧密码错误");
        }
        sysUserMapper.update(null, new LambdaUpdateWrapper<SysUser>()
                .set(SysUser::getUserPassword, bo.getNewPassword())
                .eq(SysUser::getUserId, sysUser.getUserId()));
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


    private boolean checkUserNameUnique(SysUserBo user) {
        return baseMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, user.getUserName())
                .ne(Objects.nonNull(user.getUserId()), SysUser::getUserId, user.getUserId()));
    }
}
