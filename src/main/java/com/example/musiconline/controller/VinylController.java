package com.example.musiconline.controller;

import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.domain.R;
import com.example.musiconline.domain.bo.VinylBo;
import com.example.musiconline.domain.vo.VinylVo;
import com.example.musiconline.log.annotation.Log;
import com.example.musiconline.log.enums.BusinessType;
import com.example.musiconline.service.VinylService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 唱片管理
 */
@RestController
@RequestMapping("/vinyl")
@RequiredArgsConstructor
public class VinylController {

    private final VinylService service;

    /**
     * 获取唱片列表
     */
    @Log(title = "唱片管理列表", businessType = BusinessType.OTHER)
    @PostMapping("/list")
    public TableDataInfo<VinylVo> getVinylList(@RequestBody VinylBo bo) {

        return service.getVinylList(bo);
    }


    /**
     * 新增唱片
     */
    @Log(title = "唱片管理", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public R<Void> saveVinyl(@RequestBody VinylBo bo) {

        service.saveVinyl(bo);
        return R.ok();
    }

    /**
     * 修改唱片
     */
    @Log(title = "唱片管理", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public R<Void> updateVinyl(@RequestBody VinylBo bo) {

        service.updateVinyl(bo);
        return R.ok();
    }

    /**
     * 删除唱片
     */
    @Log(title = "唱片管理", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public R<Void> deleteVinyl(@RequestBody List<Long> ids) {

        service.deleteVinyl(ids);
        return R.ok();
    }
}
