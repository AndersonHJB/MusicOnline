package com.example.musiconline.service.impl;

import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.domain.Album;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musiconline.domain.bo.AlbumBo;
import com.example.musiconline.domain.vo.AlbumSelectVo;
import com.example.musiconline.domain.vo.AlbumVo;

import java.util.List;

/**
 * @author lds
 * @date 2025/3/15
 */
public interface AlbumService extends IService<Album> {

    TableDataInfo<AlbumVo> getAlbumList(AlbumBo bo);

    void saveAlbum(AlbumBo bo);

    void updateAlbum(AlbumBo bo);

    void deleteAlbum(List<Long> ids);

    AlbumVo getAlbumInfoById(Long id);

    List<AlbumSelectVo> getAlbumSelect();
}
