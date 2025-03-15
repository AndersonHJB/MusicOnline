package com.example.musiconline.domain.bo;

import com.example.musiconline.config.mybatis.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author lds
 * @date 2025/3/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumBo extends PageQuery {

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


}
