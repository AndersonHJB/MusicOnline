package com.example.musiconline.controller;

import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.domain.R;
import com.example.musiconline.domain.bo.AlbumBo;
import com.example.musiconline.domain.vo.AlbumSelectVo;
import com.example.musiconline.domain.vo.AlbumVo;
import com.example.musiconline.log.annotation.Log;
import com.example.musiconline.log.enums.BusinessType;
import com.example.musiconline.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 专辑管理
 */
@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    /**
     * 获取专辑列表
     */
    @Log(title = "获取专辑列表", businessType = BusinessType.OTHER)
    @PostMapping("/list")
    public TableDataInfo<AlbumVo> getAlbumList(@RequestBody AlbumBo bo) {

        return albumService.getAlbumList(bo);
    }

    /**
     * 根据id获取专辑信息
     */
    @Log(title = "根据id获取专辑信息", businessType = BusinessType.OTHER)
    @GetMapping("/info/{id}")
    public R<AlbumVo> getAlbumInfoById(@PathVariable Long id) {
        AlbumVo albumVo = albumService.getAlbumInfoById(id);
        return R.ok(albumVo);
    }


    /**
     * 新增专辑
     */
    @Log(title = "新增专辑", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public R<Void> saveAlbum(@RequestBody AlbumBo bo) {
        albumService.saveAlbum(bo);
        return R.ok();
    }

    /**
     * 修改专辑
     */
    @Log(title = "修改专辑", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public R<Void> updateAlbum(@RequestBody AlbumBo bo) {
        albumService.updateAlbum(bo);
        return R.ok();
    }

    /**
     * 删除专辑
     */
    @Log(title = "删除专辑", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public R<Void> deleteAlbum(@RequestBody List<Long> ids) {
        albumService.deleteAlbum(ids);
        return R.ok();
    }

    /**
     * 获取专辑下拉框数据
     */
    @Log(title = "获取专辑下拉框数据", businessType = BusinessType.OTHER)
    @GetMapping("/select")
    public R<List<AlbumSelectVo>> getAlbumSelect() {
        List<AlbumSelectVo> list = albumService.getAlbumSelect();
        return R.ok(list);
    }

}
