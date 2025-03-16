package com.example.musiconline.controller;

import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.config.satoken.utils.LoginHelper;
import com.example.musiconline.domain.R;
import com.example.musiconline.domain.bo.SysOperLogBo;
import com.example.musiconline.domain.bo.SysUserBo;
import com.example.musiconline.domain.vo.SysOperLogVo;
import com.example.musiconline.log.annotation.Log;
import com.example.musiconline.log.enums.BusinessType;
import com.example.musiconline.service.OperLogService;
import com.example.musiconline.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统管理
 */
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final SysUserService sysUserService;

    private final OperLogService operLogService;

    /**
     * 新增用户
     */
    @Log(title = "新增用户", businessType = BusinessType.INSERT)
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
    @Log(title = "删除用户", businessType = BusinessType.DELETE)
    @PostMapping("/users/delete")
    public R<Void> deleteUser(@RequestBody List<Long> ids) {

        sysUserService.deleteUser(ids);
        return R.ok();
    }

    /**
     * 重置密码
     */
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/users/resetPassword/{id}")
    public R<Void> resetPassword(@PathVariable Long id) {

        sysUserService.resetPassword(id);
        return R.ok();
    }


    /**
     * 操作日志
     */
    @PostMapping("/log/list")
    public TableDataInfo<SysOperLogVo> getLogList(@RequestBody SysOperLogBo bo) {

        return operLogService.getLogList(bo);
    }

    /**
     * 清空日志
     */
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @PostMapping("/log/clean")
    public R<Void> cleanLog() {
        operLogService.cleanLog();
        return R.ok();
    }

}
