package com.example.musiconline.controller;

import com.example.musiconline.config.satoken.utils.LoginHelper;
import com.example.musiconline.domain.R;
import com.example.musiconline.domain.bo.SysUserBo;
import com.example.musiconline.service.impl.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lds
 * @date 2025/3/14
 */
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final SysUserService sysUserService;

    /**
     * 新增用户
     */
    @PostMapping("/users/save")
    public R<Void> saveUser(@RequestBody SysUserBo bo) {
        // 校验是否有权限新增用户
        LoginHelper.getLoginUser();
        if (!LoginHelper.isAdmin()) {
            return R.fail("没有权限新增用户");
        }
        sysUserService.saveUser(bo);
        return R.ok();
    }

    /**
     * 删除用户
     */
    @PostMapping("/users/delete")
    public R<Void> deleteUser(@RequestBody List<Long> ids) {

        sysUserService.deleteUser(ids);
        return R.ok();
    }

    /**
     * 重置密码
     */
    @PostMapping("/users/resetPassword/{id}")
    public R<Void> resetPassword(@PathVariable Long id) {

        sysUserService.resetPassword(id);
        return R.ok();
    }
}
