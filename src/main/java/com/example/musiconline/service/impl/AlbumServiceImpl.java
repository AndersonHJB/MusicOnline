package com.example.musiconline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.config.satoken.utils.LoginHelper;
import com.example.musiconline.domain.bo.AlbumBo;
import com.example.musiconline.domain.vo.AlbumSelectVo;
import com.example.musiconline.domain.vo.AlbumVo;
import com.example.musiconline.exception.ServiceException;
import com.example.musiconline.model.LoginUser;
import com.example.musiconline.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musiconline.domain.Album;
import com.example.musiconline.mapper.AlbumMapper;

import java.util.List;

/**
 * 专辑服务实现类
 */
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

    private final AlbumMapper albumMapper;

    @Override
    public TableDataInfo<AlbumVo> getAlbumList(AlbumBo bo) {
        // 获取用户登录信息
        LoginUser loginUser = LoginHelper.getLoginUser();
        LambdaQueryWrapper<Album> queryWrapper =  new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(loginUser.getUserRole()) && loginUser.getUserRole() != 1, Album::getCreateBy, loginUser.getUserId());
        IPage<AlbumVo> albumVoIPage = albumMapper.selectVoPage(bo.build(), queryWrapper);

        return TableDataInfo.build(albumVoIPage);
    }

    @Override
    public void saveAlbum(AlbumBo bo) {
        Album album = new Album();
        BeanUtils.copyProperties(bo, album);
        albumMapper.insert(album);
    }

    @Override
    public void updateAlbum(AlbumBo bo) {
        // 获取用户登录信息
        LoginUser loginUser = LoginHelper.getLoginUser();
        // 获取专辑信息
        Album album = albumMapper.selectById(bo.getId());
        // 权限校验
        if (loginUser.getUserRole() != 1 && !album.getCreateBy().equals(loginUser.getUserId())){
            throw new ServiceException("权限不足");
        }
        BeanUtils.copyProperties(bo, album);
        albumMapper.updateById(album);

    }

    @Override
    public void deleteAlbum(List<Long> ids) {
        // 获取用户登录信息
        LoginUser loginUser = LoginHelper.getLoginUser();
        // 获取专辑信息
        List<Album> albums = albumMapper.selectBatchIds(ids);
        // 权限校验
        albums.forEach(album -> {
            if (loginUser.getUserRole() != 1 && !album.getCreateBy().equals(loginUser.getUserId())){
                throw new ServiceException("权限不足");
            }
        });
        albumMapper.deleteBatchIds(ids);
    }

    @Override
    public AlbumVo getAlbumInfoById(Long id) {
        // 获取用户信息
        LoginUser loginUser = LoginHelper.getLoginUser();
        // 查询专辑信息
        AlbumVo albumVo = albumMapper.selectVoById(id);
        //权限校验
        if (loginUser.getUserRole() != 1 && !albumVo.getCreateBy().equals(loginUser.getUserId())){
            throw new ServiceException("权限不足");
        }
        return albumVo;
    }

    @Override
    public List<AlbumSelectVo> getAlbumSelect() {

        // 获取用户信息
        LoginUser loginUser = LoginHelper.getLoginUser();
        // 权限校验
        Long userId = loginUser.getUserId();
        if (loginUser.getUserRole() == 1){
            userId = null;
        }
        return albumMapper.selectAlbumSelect(userId);
    }

    @Override
    public List<AlbumSelectVo> getAllAlbumSelect() {

        return albumMapper.selectAlbumSelect(null);
    }
}
