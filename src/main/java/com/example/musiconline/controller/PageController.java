package com.example.musiconline.controller;

import com.example.musiconline.config.satoken.utils.LoginHelper;
import com.example.musiconline.domain.Vinyl;
import com.example.musiconline.service.impl.AlbumService;
import com.example.musiconline.service.impl.VinylService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lds
 * @date 2025/3/10
 */
@Controller
@RequiredArgsConstructor
public class PageController {

    private final VinylService vinylService;

    private final AlbumService albumService;

    @GetMapping({"/","/page/index"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        request.setAttribute("vinylCount", 100);
        request.setAttribute("albumCount", 100);
        return "admin/index";
    }

    @GetMapping("/page/login")
    public String login() {
        return "admin/login";
    }

    /**
     * 注册页面
     */
    @GetMapping("/page/register")
    public String register() {
        return "admin/register";
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

    /**
     * 专辑管理页面
     */
    @GetMapping("/page/album")
    public String albums(HttpServletRequest request) {
        request.setAttribute("path", "album");
        return "admin/album";
    }

    /**
     * 唱片管理页面
     */
    @GetMapping("/page/vinyl")
    public String vinyls(HttpServletRequest request) {
        request.setAttribute("path", "vinyl");
        return "admin/vinyl";
    }

    /**
     * 唱片发布编辑页面
     */
    @GetMapping({"/page/vinyl/edit","/page/vinyl/edit/{id}"})
    public String vinylEdit(HttpServletRequest request, @PathVariable(required = false) Long id) {
        if (id != null){
            //获取唱片数据用于回显
            Vinyl vinyl = vinylService.getById(id);
            request.setAttribute("vinyl", vinyl);
        }
        request.setAttribute("albums", albumService.getAlbumSelect());
        request.setAttribute("path", "vinyl-edit");
        return "admin/vinyl-edit";
    }

    /**
     * 唱片详情页面
     */
    @GetMapping("/page/vinyl/detail/{id}")
    public String vinylDetail(HttpServletRequest request, @PathVariable Long id) {
        Vinyl vinyl = vinylService.getById(id);
        request.setAttribute("vinyl", vinyl);
        request.setAttribute("albums", albumService.getAlbumSelect());
        return "admin/vinyl-detail";
    }

    /**
     * 个人信息页面
     */
    @GetMapping("/page/user/info")
    public String userInfo(HttpServletRequest request) {
        request.setAttribute("path", "info");
        return "admin/info";
    }
}
