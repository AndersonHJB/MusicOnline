package com.example.musiconline.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.domain.bo.VinylBo;
import com.example.musiconline.domain.vo.VinylVo;
import com.example.musiconline.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musiconline.domain.Vinyl;
import com.example.musiconline.mapper.VinylMapper;
import com.example.musiconline.service.impl.VinylService;

import java.util.List;

/**
 *
 * @author lds
 * @date 2025/3/15
 */
@Service
@RequiredArgsConstructor
public class VinylServiceImpl extends ServiceImpl<VinylMapper, Vinyl> implements VinylService{

    private final VinylMapper vinylMapper;

    @Override
    public TableDataInfo<VinylVo> getVinylList(VinylBo bo) {

        LambdaQueryWrapper<Vinyl> queryWrapper = buildQueryWrapper(bo);
        Page<VinylVo> vinylPage =vinylMapper.selectVinyPage(bo.build(), queryWrapper);

        return TableDataInfo.build(vinylPage);
    }

    @Override
    public void deleteVinyl(List<Long> ids) {
        vinylMapper.deleteBatchIds(ids);
    }

    @Override
    public void saveVinyl(VinylBo bo) {
        Vinyl vinyl = new Vinyl();
        BeanUtils.copyProperties(bo, vinyl);
        vinylMapper.insert(vinyl);
    }

    @Override
    public void updateVinyl(VinylBo bo) {
        Vinyl vinyl = new Vinyl();
        BeanUtils.copyProperties(bo, vinyl);
        vinylMapper.updateById(vinyl);
    }


    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<Vinyl> buildQueryWrapper(VinylBo bo) {
        LambdaQueryWrapper<Vinyl> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(bo.getSingleName()), Vinyl::getSingleName, bo.getSingleName())
                .like(StringUtils.isNotEmpty(bo.getVinylTitle()), Vinyl::getVinylTitle, bo.getVinylTitle())
                .like(StringUtils.isNotEmpty(bo.getArtist()), Vinyl::getArtist, bo.getArtist())
                .eq(ObjectUtils.isNotEmpty(bo.getAlbumId()), Vinyl::getAlbumId, bo.getAlbumId())
                .eq(Vinyl::getDelFlag, 0);
        return queryWrapper;
    }
}
