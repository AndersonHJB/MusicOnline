package com.example.musiconline.domain.vo;

import com.example.musiconline.domain.Album;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Date;

/**
 * @author lds
 * @date 2025/3/15
 */
@Data
@AutoMapper(target = Album.class)
public class AlbumVo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 专辑名称
     */
    private String albumName;

    /**
     * 专辑封面
     */
    private String albumCover;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;
}
