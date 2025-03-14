package com.example.musiconline.controller;


import com.example.musiconline.domain.R;
import com.example.musiconline.domain.vo.LoginVo;
import com.example.musiconline.model.LoginBody;
import com.example.musiconline.service.impl.SysUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

/**
 * token 控制
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class TokenController {

    private final SysUserService sysUserService;


    /**
     * 登录方法
     */
    @PostMapping("login")
    public R<LoginVo> login(@RequestBody LoginBody loginBody, HttpServletRequest request) {

        // 登录
        LoginVo vo = sysUserService.login(loginBody);

        // 保存用户名到session
        request.getSession().setAttribute("loginUser", loginBody.getUserName());
        return R.ok(vo);
    }



    /**
     * 登出方法
     */
    @PostMapping("logout")
    public R<Void> logout(HttpServletRequest request) {
        sysUserService.logout();
        // 清除session
        request.getSession().removeAttribute("loginUser");
        return R.ok();
    }

    /**
     * 用户注册
     */
    @PostMapping("register")
    public R<Void> register(@RequestBody LoginBody registerBody) {
        // 用户注册
        sysUserService.register(registerBody);
        return R.ok();
    }


}
