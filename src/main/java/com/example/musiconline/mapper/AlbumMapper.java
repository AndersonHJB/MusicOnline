package com.example.musiconline.mapper;

import com.example.musiconline.config.mybatis.mapper.BaseMapperPlus;
import com.example.musiconline.domain.Album;
import com.example.musiconline.domain.vo.AlbumSelectVo;
import com.example.musiconline.domain.vo.AlbumVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author lds
 * @date 2025/3/15
 */
@Mapper
public interface AlbumMapper extends BaseMapperPlus<Album, AlbumVo> {
    List<AlbumSelectVo> selectAlbumSelect();
}