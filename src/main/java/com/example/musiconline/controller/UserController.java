package com.example.musiconline.controller;

import com.example.musiconline.service.impl.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lds
 * @date 2025/3/14
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final SysUserService sysUserService;

    @PostMapping("/list")
    public String getUserList() {
        return "test";
    }
}
