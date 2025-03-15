package com.example.musiconline.domain.vo;

import com.example.musiconline.domain.Vinyl;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lds
 * @date 2025/3/15
 */
@Data
@AutoMapper(target = Vinyl.class)
public class VinylVo {

    /**
     * 黑胶唱片id
     */
    private Long id;

    /**
     * 单曲名称
     */
    private String singleName;

    /**
     * 黑胶唱片标题
     */
    private String vinylTitle;

    /**
     * 唱片封面
     */
    private String vinylCover;

    /**
     * 艺术家
     */
    private String artist;

    /**
     * 专辑name
     */
    private String albumName;

    /**
     * 发行日期
     */
    private Date issueDate;

    /**
     * 价格
     */
    private BigDecimal price;

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
