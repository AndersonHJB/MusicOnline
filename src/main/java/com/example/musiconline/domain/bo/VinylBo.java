package com.example.musiconline.domain.bo;

import com.example.musiconline.config.mybatis.page.PageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lds
 * @date 2025/3/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VinylBo extends PageQuery {

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
     * 专辑id
     */
    private Long albumId;

    /**
     * 发行日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date issueDate;

    /**
     * 价格
     */
    private BigDecimal price;
}
