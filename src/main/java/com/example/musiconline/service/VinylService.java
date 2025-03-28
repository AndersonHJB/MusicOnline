package com.example.musiconline.service;

import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.domain.Vinyl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musiconline.domain.bo.VinylBo;
import com.example.musiconline.domain.vo.VinylVo;

import java.util.List;

/**
 * 黑胶唱片日志服务层
 */
public interface VinylService extends IService<Vinyl> {

    TableDataInfo<VinylVo> getVinylList(VinylBo bo);


    void deleteVinyl(List<Long> ids);

    void saveVinyl(VinylBo bo);

    void updateVinyl(VinylBo bo);

    TableDataInfo<VinylVo> getVinylSelect(VinylBo bo);
}
