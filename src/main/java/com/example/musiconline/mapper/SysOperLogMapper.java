package com.example.musiconline.mapper;

import com.example.musiconline.config.mybatis.mapper.BaseMapperPlus;
import com.example.musiconline.domain.SysOperLog;
import com.example.musiconline.domain.vo.SysOperLogVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志 数据层
 */
@Mapper
public interface SysOperLogMapper extends BaseMapperPlus<SysOperLog, SysOperLogVo> {
}
