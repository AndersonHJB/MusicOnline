package com.example.musiconline.domain.bo;

import com.example.musiconline.config.mybatis.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperLogBo extends PageQuery {

    private String keyword;
}
