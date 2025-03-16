package com.example.musiconline.controller;

import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.config.satoken.utils.LoginHelper;
import com.example.musiconline.domain.R;
import com.example.musiconline.domain.bo.SysUserBo;
import com.example.musiconline.domain.bo.UpdatePasswordBo;
import com.example.musiconline.domain.vo.SysUserVo;
import com.example.musiconline.log.annotation.Log;
import com.example.musiconline.log.enums.BusinessType;
import com.example.musiconline.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final SysUserService sysUserService;

    /**
     * 获取用户列表
     */
    @Log(title = "获取用户列表", businessType = BusinessType.OTHER)
    @PostMapping("/list")
    public TableDataInfo<SysUserVo> getUserList(@RequestBody SysUserBo bo) {

        return sysUserService.getUserList(bo);
    }

    /**
     * 获取用户信息
     */
    @Log(title = "获取用户信息", businessType = BusinessType.OTHER)
    @PostMapping("/info/{id}")
    public R<SysUserVo> getUserInfoById(@PathVariable String id) {

        return R.ok(sysUserService.getUserInfoById(id));
    }

    /**
     * 用户信息更新
     */
    @Log(title = "用户信息更新", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public R<Void> updateUser(@RequestBody SysUserBo bo) {

        sysUserService.updateUser(bo);
        return R.ok();
    }


    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public R<SysUserVo> getUserInfo() {

        return R.ok(sysUserService.getUserInfoById(LoginHelper.getUserId().toString()));
    }

    /**
     * 修改密码
     */
    @Log(title = "修改密码", businessType = BusinessType.UPDATE)
    @PostMapping("/updatePassword")
    public R<Void> updatePassword(@RequestBody UpdatePasswordBo bo) {

        sysUserService.updatePassword(bo);
        return R.ok();
    }
}
