package com.example.musiconline.controller;

import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.domain.R;
import com.example.musiconline.domain.bo.AlbumBo;
import com.example.musiconline.domain.vo.AlbumSelectVo;
import com.example.musiconline.domain.vo.AlbumVo;
import com.example.musiconline.service.impl.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lds
 * @date 2025/3/14
 */
@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    /**
     * 获取专辑列表
     */
    @PostMapping("/list")
    public TableDataInfo<AlbumVo> getAlbumList(@RequestBody AlbumBo bo) {

        return albumService.getAlbumList(bo);
    }

    /**
     * 根据id获取专辑信息
     */
    @GetMapping("/info/{id}")
    public R<AlbumVo> getAlbumInfoById(@PathVariable Long id) {
        AlbumVo albumVo = albumService.getAlbumInfoById(id);
        return R.ok(albumVo);
    }


    /**
     * 新增专辑
     */
    @PostMapping("/save")
    public R<Void> saveAlbum(@RequestBody AlbumBo bo) {
        albumService.saveAlbum(bo);
        return R.ok();
    }

    /**
     * 修改专辑
     */
    @PostMapping("/update")
    public R<Void> updateAlbum(@RequestBody AlbumBo bo) {
        albumService.updateAlbum(bo);
        return R.ok();
    }

    /**
     * 删除专辑
     */
    @PostMapping("/delete")
    public R<Void> deleteAlbum(@RequestBody List<Long> ids) {
        albumService.deleteAlbum(ids);
        return R.ok();
    }

    /**
     * 获取专辑下拉框数据
     */
    @GetMapping("/select")
    public R<List<AlbumSelectVo>> getAlbumSelect() {
        List<AlbumSelectVo> list = albumService.getAlbumSelect();
        return R.ok(list);
    }

}
