package com.example.musiconline.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

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
@TableName(value = "vinyl")
public class Vinyl extends BaseEntity {
    /**
     * 黑胶唱片id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 单曲名称
     */
    @TableField(value = "single_name")
    private String singleName;

    /**
     * 黑胶唱片标题
     */
    @TableField(value = "vinyl_title")
    private String vinylTitle;

    /**
     * 唱片封面
     */
    @TableField(value = "vinyl_cover")
    private String vinylCover;

    /**
     * 艺术家
     */
    @TableField(value = "artist")
    private String artist;

    /**
     * 专辑id
     */
    @TableField(value = "album_id")
    private Long albumId;

    /**
     * 发行日期
     */
    @TableField(value = "issue_date")
    private Date issueDate;

    /**
     * 价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    @TableField(value = "del_flag")
    private String delFlag;

}