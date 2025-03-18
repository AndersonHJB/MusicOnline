package com.example.musiconline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.config.satoken.utils.LoginHelper;
import com.example.musiconline.domain.bo.VinylBo;
import com.example.musiconline.domain.vo.VinylVo;
import com.example.musiconline.exception.ServiceException;
import com.example.musiconline.model.LoginUser;
import com.example.musiconline.service.VinylService;
import com.example.musiconline.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musiconline.domain.Vinyl;
import com.example.musiconline.mapper.VinylMapper;

import java.util.List;
import java.util.Objects;

/**
 * 黑胶唱片服务实现
 */
@Service
@RequiredArgsConstructor
public class VinylServiceImpl extends ServiceImpl<VinylMapper, Vinyl> implements VinylService {

    private final VinylMapper vinylMapper;

    @Override
    public TableDataInfo<VinylVo> getVinylList(VinylBo bo) {

        LambdaQueryWrapper<Vinyl> queryWrapper = buildQueryWrapper(bo);
        Page<VinylVo> vinylPage =vinylMapper.selectVinyPage(bo.build(), queryWrapper);

        return TableDataInfo.build(vinylPage);
    }

    @Override
    public void deleteVinyl(List<Long> ids) {
        // 获取用户信息
        LoginUser loginUser = LoginHelper.getLoginUser();
        //获取唱片信息
        List<Vinyl> vinyls = vinylMapper.selectBatchIds(ids);
        // 判断是否有权限删除
        vinyls.forEach(vinyl -> {
            if (!Objects.equals(vinyl.getCreateBy(), loginUser.getUserId()) && loginUser.getUserRole() != 1){
                throw new ServiceException("只能删除自己上传的唱片");
            }
        });
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
        // 获取用户信息
        LoginUser loginUser = LoginHelper.getLoginUser();
        // 查询唱片信息
        Vinyl vinyl = vinylMapper.selectById(bo.getId());
        if (vinyl == null) throw new ServiceException("唱片不存在");
        if (!Objects.equals(vinyl.getCreateBy(), loginUser.getUserId()) && loginUser.getUserRole() != 1){
            throw new ServiceException("您没有权限修改该唱片");
        }
        BeanUtils.copyProperties(bo, vinyl);
        // 空值也进行保存
        LambdaUpdateWrapper<Vinyl> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Vinyl::getId, vinyl.getId());
        updateWrapper.set(Vinyl::getAlbumId, vinyl.getAlbumId());
        vinylMapper.update(vinyl,updateWrapper);
    }

    @Override
    public TableDataInfo<VinylVo> getVinylSelect(VinylBo bo) {
        LambdaQueryWrapper<Vinyl> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(bo.getSingleName()), Vinyl::getSingleName, bo.getSingleName())
                .like(StringUtils.isNotEmpty(bo.getVinylTitle()), Vinyl::getVinylTitle, bo.getVinylTitle())
                .like(StringUtils.isNotEmpty(bo.getArtist()), Vinyl::getArtist, bo.getArtist())
                .eq(ObjectUtils.isNotEmpty(bo.getAlbumId()), Vinyl::getAlbumId, bo.getAlbumId());
        Page<VinylVo> vinylPage = vinylMapper.selectVinyPage(bo.build(), queryWrapper);
        return TableDataInfo.build(vinylPage);
    }


    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<Vinyl> buildQueryWrapper(VinylBo bo) {
        // 获取用户信息
        LoginUser loginUser = LoginHelper.getLoginUser();
        LambdaQueryWrapper<Vinyl> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(bo.getSingleName()), Vinyl::getSingleName, bo.getSingleName())
                .like(StringUtils.isNotEmpty(bo.getVinylTitle()), Vinyl::getVinylTitle, bo.getVinylTitle())
                .like(StringUtils.isNotEmpty(bo.getArtist()), Vinyl::getArtist, bo.getArtist())
                .eq(ObjectUtils.isNotEmpty(bo.getAlbumId()), Vinyl::getAlbumId, bo.getAlbumId())
                .eq(ObjectUtils.isNotEmpty(loginUser.getUserRole()) && loginUser.getUserRole() != 1, Vinyl::getCreateBy, loginUser.getUserId())
                .eq(Vinyl::getDelFlag, 0);
        return queryWrapper;
    }
}
