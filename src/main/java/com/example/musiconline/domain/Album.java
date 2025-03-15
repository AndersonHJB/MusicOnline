package com.example.musiconline.domain;

import com.baomidou.mybatisplus.annotation.*;

import com.example.musiconline.config.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author lds
 * @date 2025/3/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "album")
public class Album extends BaseEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 专辑名称
     */
    @TableField(value = "album_name")
    private String albumName;

    /**
     * 专辑封面
     */
    @TableField(value = "album_cover")
    private String albumCover;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    @TableField(value = "del_flag")
    private String delFlag;

}