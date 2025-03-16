package com.example.musiconline.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.domain.bo.AlbumBo;
import com.example.musiconline.domain.vo.AlbumSelectVo;
import com.example.musiconline.domain.vo.AlbumVo;
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
        IPage<AlbumVo> albumVoIPage = albumMapper.selectVoPage(bo.build(), null);

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
        Album album = new Album();
        BeanUtils.copyProperties(bo, album);
        albumMapper.updateById(album);

    }

    @Override
    public void deleteAlbum(List<Long> ids) {
        albumMapper.deleteBatchIds(ids);
    }

    @Override
    public AlbumVo getAlbumInfoById(Long id) {
        return albumMapper.selectVoById(id);
    }

    @Override
    public List<AlbumSelectVo> getAlbumSelect() {

        return albumMapper.selectAlbumSelect();
    }
}
