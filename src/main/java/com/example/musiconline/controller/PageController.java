package com.example.musiconline.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lds
 * @date 2025/3/10
 */
@Controller
public class PageController {

    @GetMapping({"/","/page/index"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "admin/index";
    }

    @GetMapping("/page/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/page/blogs/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        return "admin/edit";
    }

    @GetMapping("/page/user")
    public String configurations(HttpServletRequest request) {
        request.setAttribute("path", "user");
        return "admin/user";
    }
}
