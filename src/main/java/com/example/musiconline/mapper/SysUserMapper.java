package com.example.musiconline.mapper;

import com.example.musiconline.config.mybatis.mapper.BaseMapperPlus;
import com.example.musiconline.domain.SysUser;
import com.example.musiconline.domain.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表 数据层
 */
@Mapper
public interface SysUserMapper extends BaseMapperPlus<SysUser, SysUserVo> {
}